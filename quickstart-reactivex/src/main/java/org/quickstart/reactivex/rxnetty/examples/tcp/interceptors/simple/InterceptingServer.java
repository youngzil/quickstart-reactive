/*
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package org.quickstart.reactivex.rxnetty.examples.tcp.interceptors.simple;

import java.nio.charset.Charset;

import org.quickstart.reactivex.rxnetty.examples.ExamplesEnvironment;
import org.quickstart.reactivex.rxnetty.examples.tcp.interceptors.transformation.TransformingInterceptorsServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.reactivex.netty.protocol.tcp.server.ConnectionHandler;
import io.reactivex.netty.protocol.tcp.server.TcpServer;
import io.reactivex.netty.protocol.tcp.server.TcpServerInterceptorChain;
import io.reactivex.netty.protocol.tcp.server.TcpServerInterceptorChain.Interceptor;
import rx.Observable;

/**
 * A TCP echo server that follows a simple text based, new line delimited message protocol. The server sends a "Hello" as the first message and then the echo of what it receives from the client.
 * <p>
 *
 * This example demonstrates the usage of simple server side interceptors which does not do any data transformations. For interceptors requiring data transformation see
 * {@link TransformingInterceptorsServer}
 *
 * This example just aims to demonstrate how to write the simplest TCP server, it is however, not of much use in general primarily because it reads unstructured data i.e. there are no boundaries that
 * define what constitutes "a message". In order to define such boundaries, one would typically add a {@link ChannelHandler} that converts the read raw {@code ByteBuffer} to a structured message.
 */
public final class InterceptingServer {

  public static void main(final String[] args) {

    ExamplesEnvironment env = ExamplesEnvironment.newEnvironment(InterceptingServer.class);

    TcpServer<ByteBuf, ByteBuf> server;

    /*Starts a new TCP server on an ephemeral port.*/
    server = TcpServer.newServer(0).start(TcpServerInterceptorChain.startRaw(sendHello()).end(echoHandler()));

    /*Wait for shutdown if not called from the client (passed an arg)*/
    if (env.shouldWaitForShutdown(args)) {
      server.awaitShutdown();
    }

    /*If not waiting for shutdown, assign the ephemeral port used to a field so that it can be read and used by
    the caller, if any.*/
    env.registerServerAddress(server.getServerAddress());
  }

  /**
   * Sends a hello on accepting a new connection.
   */
  private static Interceptor<ByteBuf, ByteBuf> sendHello() {
    return in -> newConnection -> newConnection.writeString(Observable.just("Hello\n")).concatWith(in.handle(newConnection));
  }

  /**
   * New {@link ConnectionHandler} that echoes all data received.
   *
   * @return Connection handler.
   */
  private static ConnectionHandler<ByteBuf, ByteBuf> echoHandler() {
    return conn -> conn.writeStringAndFlushOnEach(conn.getInput().map(msg -> "echo => " + msg.toString(Charset.defaultCharset()) + "\n"));
  }
}
