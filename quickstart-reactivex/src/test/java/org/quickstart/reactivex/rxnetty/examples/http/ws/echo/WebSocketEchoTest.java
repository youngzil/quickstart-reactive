/*
 * Copyright 2015 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.quickstart.reactivex.rxnetty.examples.http.ws.echo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;

import java.lang.reflect.InvocationTargetException;
import java.util.Queue;

import org.junit.Test;
import org.quickstart.reactivex.rxnetty.examples.ExamplesTestUtil;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;

public class WebSocketEchoTest {

  @Test(timeout = 60000)
  public void testWebSocketHello() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

    Queue<String> output = ExamplesTestUtil.runClientInMockedEnvironment(WebSocketEchoClient.class);
    String[] content = new String[10];
    for (int i = 0; i < 10; i++) {
      content[i] = "Interval " + (i + 1);
    }

    assertThat("Unexpected number of messages echoed", output, hasSize(content.length + 1));

    final String headerString = output.poll();
    assertThat("Unexpected HTTP initial line of response.", headerString, containsString("HTTP/1.1 101 Switching Protocols"));
    assertThat("WebSocket accept header not found in response.", headerString, containsString(HttpHeaderNames.SEC_WEBSOCKET_ACCEPT + ":"));
    assertThat("Unexpected connection header.", headerString, containsString(HttpHeaderNames.CONNECTION + ": " + HttpHeaderValues.UPGRADE));

    assertThat("Unexpected content", output, contains(content));
  }
}
