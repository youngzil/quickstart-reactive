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
package org.quickstart.reactivex.rxnetty.examples.http.loadbalancing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;

import java.util.Queue;

import org.junit.Test;
import org.quickstart.reactivex.rxnetty.examples.ExamplesTestUtil;

public class LoadBalancingTest {

  @Test(timeout = 60000)
  public void testLoadBalancing() throws Exception {
    Queue<String> output = ExamplesTestUtil.runClientInMockedEnvironment(HttpLoadBalancingClient.class);

    assertThat("Unexpected number of messages echoed", output, hasSize(5));

    assertThat("Unexpected status.", output.poll(), anyOf(containsString("HTTP/1.1 200 OK"), containsString("HTTP/1.1 503 Service Unavailable")));
    assertThat("Unexpected status.", output.poll(), anyOf(containsString("HTTP/1.1 200 OK"), containsString("HTTP/1.1 503 Service Unavailable")));
    assertThat("Unexpected status.", output.poll(), anyOf(containsString("HTTP/1.1 200 OK"), containsString("HTTP/1.1 503 Service Unavailable")));
    assertThat("Unexpected status.", output.poll(), anyOf(containsString("HTTP/1.1 200 OK"), containsString("HTTP/1.1 503 Service Unavailable")));
    assertThat("Unexpected status.", output.poll(), anyOf(containsString("HTTP/1.1 200 OK"), containsString("HTTP/1.1 503 Service Unavailable")));

  }
}
