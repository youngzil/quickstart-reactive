/*
 * Copyright 2015 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */
package org.quickstart.reactivex.rxnetty.examples.http.ws.messaging;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import org.quickstart.reactivex.rxnetty.examples.ExamplesEnvironment;
import org.slf4j.Logger;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.ws.client.WebSocketResponse;
import rx.Observable;

/**
 * An example to demonstrate how to use WebSocket to send messages to a messaging server. This example sends a stream of 10 {@link MessageFrame} to the specified server and expects 10 acknowledgments
 * in response.
 *
 * There are three ways of running this example:
 *
 * <h2>Default</h2>
 *
 * The default way is to just run this class with no arguments, which will start a server ({@link MessagingServer}) on an ephemeral port and then send an HTTP request to that server and print the
 * response.
 *
 * <h2>After starting {@link MessagingServer}</h2>
 *
 * If you want to see how {@link MessagingServer} work, you can run {@link MessagingServer} by yourself and then pass the port on which the server started to this class as a program argument:
 *
 * <PRE>
 * java io.reactivex.netty.examples.http.ws.messaging.MessagingClient [server port]
 * </PRE>
 *
 * <h2>Existing HTTP server</h2>
 *
 * You can also use this client to send a GET request "/ws" to an existing HTTP server (different than {@link MessagingServer}) by passing the port and host of the existing server similar to the case
 * above:
 *
 * <PRE>
 * java io.reactivex.netty.examples.http.ws.messaging.MessagingClient [server port]
 * </PRE>
 * 
 * If the server host is omitted from the above, it defaults to "127.0.0.1"
 *
 * In all the above usages, this client will print the response received from the server.
 *
 * <h2>Retry</h2>
 *
 * If the client does not receive acknowledgments for any message ID for 10 seconds, it re-sends the message. See {@link PendingMessageTracker} for the relevant code. This means that this example
 * follows the atleast once delivery semantics.
 *
 * <h2>Message buffering</h2>
 *
 * Since, message production is asynchronous, if this client can not connect to the server, it will buffer the messages in memory and wait for the server to come up.
 */
public final class MessagingClient {

  public static void main(String[] args) {

    ExamplesEnvironment env = ExamplesEnvironment.newEnvironment(MessagingClient.class);
    /*
     * Retrieves the server address, using the following algorithm:
     * <ul>
         <li>If any arguments are passed, then use the first argument as the server port.</li>
         <li>If available, use the second argument as the server host, else default to localhost</li>
         <li>Otherwise, start the passed server class and use that address.</li>
     </ul>
     */
    SocketAddress socketAddress = env.getServerAddress(MessagingServer.class, args);
    Logger logger = env.getLogger();

    /*Sends 10 messages each at an interval of 10 seconds to show asynchronous message creation*/
    MessageProducer producer = new MessageProducer(10, 1, TimeUnit.SECONDS);

    /*Create a new client for the server address*/
    HttpClient.newClient(socketAddress).enableWireLogging("msging-client", LogLevel.DEBUG).createGet("/ws").requestWebSocketUpgrade()
        .doOnNext(resp -> logger.info(resp.toString())).flatMap(WebSocketResponse::getWebSocketConnection)
        .flatMap(conn -> conn.writeAndFlushOnEach(producer.getMessageStream()).cast(WebSocketFrame.class).mergeWith(conn.getInput()))
        .retryWhen(errStream -> errStream.flatMap(err -> {
          if (err instanceof IOException) {
            return Observable.timer(1, TimeUnit.SECONDS);
          }
          return Observable.error(err);
        })).filter(AcceptOnlyBinaryFramesFilter.INSTANCE).cast(BinaryWebSocketFrame.class).map(producer::acceptAcknowledgment).take(10).toBlocking()
        .forEach(msgId -> logger.info("Received acknowledgment for message id => " + msgId.toString()));
  }
}
