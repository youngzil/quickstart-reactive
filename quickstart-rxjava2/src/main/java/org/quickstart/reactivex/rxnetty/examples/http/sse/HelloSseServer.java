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

package org.quickstart.reactivex.rxnetty.examples.http.sse;

import static io.reactivex.netty.protocol.http.sse.ServerSentEvent.withData;

import java.util.concurrent.TimeUnit;

import org.quickstart.reactivex.rxnetty.examples.ExamplesEnvironment;

import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

/**
 * An <a href="http://www.w3.org/TR/eventsource/">Server sent event</a> "Hello World" example server.
 *
 * This server send an infinite Server Sent Event stream for any request that it recieves. The infinite stream publishes an event every 10 milliseconds, with the event data as "Interval => [count
 * starting with 0]"
 */
public final class HelloSseServer {

  public static void main(final String[] args) {

    ExamplesEnvironment env = ExamplesEnvironment.newEnvironment(HelloSseServer.class);
    HttpServer<ByteBuf, ByteBuf> server;

    server = HttpServer.newServer().enableWireLogging("sse-server", LogLevel.DEBUG).start((req, resp) -> resp.transformToServerSentEvents()
        .writeAndFlushOnEach(Observable.interval(10, TimeUnit.MILLISECONDS).onBackpressureBuffer(10).map(aLong -> withData("Interval => " + aLong))));

    /*Wait for shutdown if not called from the client (passed an arg)*/
    if (env.shouldWaitForShutdown(args)) {
      server.awaitShutdown();
    }

    /*If not waiting for shutdown, assign the ephemeral port used to a field so that it can be read and used by
    the caller, if any.*/
    env.registerServerAddress(server.getServerAddress());
  }
}
