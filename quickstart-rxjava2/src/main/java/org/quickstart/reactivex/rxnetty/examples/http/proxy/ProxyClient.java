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

package org.quickstart.reactivex.rxnetty.examples.http.proxy;

import java.net.SocketAddress;
import java.nio.charset.Charset;

import org.quickstart.reactivex.rxnetty.examples.ExamplesEnvironment;
import org.quickstart.reactivex.rxnetty.examples.http.helloworld.HelloWorldClient;
import org.slf4j.Logger;

import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.client.HttpClient;

/**
 * A client to test {@link ProxyServer}. This client is provided here only for completeness of the example, otherwise, it is exactly the same as {@link HelloWorldClient}.
 */
public final class ProxyClient {

  public static void main(String[] args) {

    ExamplesEnvironment env = ExamplesEnvironment.newEnvironment(ProxyClient.class);
    Logger logger = env.getLogger();

    /*
     * Retrieves the server address, using the following algorithm:
     * <ul>
         <li>If any arguments are passed, then use the first argument as the server port.</li>
         <li>If available, use the second argument as the server host, else default to localhost</li>
         <li>Otherwise, start the passed server class and use that address.</li>
     </ul>
     */
    SocketAddress serverAddress = env.getServerAddress(ProxyServer.class, args);

    HttpClient.newClient(serverAddress).enableWireLogging("proxy-client", LogLevel.DEBUG).createGet("/hello")
        .doOnNext(resp -> logger.info(resp.toString())).flatMap(resp -> resp.getContent().map(bb -> bb.toString(Charset.defaultCharset())))
        .toBlocking().forEach(logger::info);
  }
}
