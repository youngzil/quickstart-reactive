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

package org.quickstart.reactivex.rxnetty.examples.tcp.loadbalancing;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

import org.quickstart.reactivex.rxnetty.examples.ExamplesEnvironment;
import org.slf4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.client.ConnectionProvider;
import io.reactivex.netty.client.Host;
import io.reactivex.netty.client.loadbalancer.LoadBalancerFactory;
import io.reactivex.netty.protocol.tcp.client.TcpClient;
import io.reactivex.netty.protocol.tcp.server.TcpServer;
import rx.Observable;

/**
 * This example demonstrates how to integrate any arbitrary load balancing logic with a {@link TcpClient}. Load balancing algorithms are not provided by {@code RxNetty}, what is provided is a low
 * level construct of {@link ConnectionProvider} that abstracts providing connections for a {@link TcpClient}. Higher level constructs like Load Balancing, connection pooling, etc. can be built using
 * these building blocks.
 *
 * The code here uses a naive {@link TcpLoadBalancer} that removes a host on any connection failure and otherwise round robins on the set of available hosts.
 *
 * This example, starts a couple emebedded TCP server and uses a list of these server addresses and an unavailable server address to demonstrate failure detetction (not using the unavailable server)
 * and round-robin load balancing (alternating between the two available hosts for the requests)
 *
 * @see ConnectionProvider Low level abstraction to create varied load balancing schemes.
 * @see TcpLoadBalancer An example of load balancer used by this example.
 */
public final class TcpLoadBalancingClient {

  public static void main(String[] args) {

    ExamplesEnvironment env = ExamplesEnvironment.newEnvironment(TcpLoadBalancingClient.class);
    Logger logger = env.getLogger();

    /*Start two embedded servers and use there addresses as two hosts, add a unavailable server to demonstrate
     * failure detection.*/
    final Observable<Host> hosts = Observable.just(startNewServer(), startNewServer(), new InetSocketAddress(0)).map(Host::new);

    TcpClient.<ByteBuf, ByteBuf>newClient(LoadBalancerFactory.create(new TcpLoadBalancer<>()), hosts).enableWireLogging("lb-client", LogLevel.DEBUG)
        .createConnectionRequest().doOnNext(conn -> logger.info("Using host: " + conn.unsafeNettyChannel().remoteAddress()))
        .flatMap(connection -> connection.writeString(Observable.just("Hello World!")).cast(ByteBuf.class).concatWith(connection.getInput())).take(1)
        .map(bb -> bb.toString(Charset.defaultCharset())).repeat(5).toBlocking().forEach(logger::info);
  }

  private static SocketAddress startNewServer() {
    /*Start a new server on an ephemeral port that echoes all messages, as is.*/
    return TcpServer.newServer().start(conn -> conn.writeAndFlushOnEach(conn.getInput())).getServerAddress();
  }
}
