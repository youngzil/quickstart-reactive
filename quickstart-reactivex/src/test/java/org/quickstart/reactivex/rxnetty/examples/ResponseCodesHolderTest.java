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

package org.quickstart.reactivex.rxnetty.examples;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.netflix.spectator.api.DefaultRegistry;
import com.netflix.spectator.api.Registry;

import io.reactivex.netty.spectator.http.internal.ResponseCodesHolder;

public class ResponseCodesHolderTest {

  @Test(timeout = 60000)
  public void testGetResponse1xx() throws Exception {
    String monitorId = newMonitorId();
    Registry registry = new DefaultRegistry();
    ResponseCodesHolder holder = new ResponseCodesHolder(registry, monitorId);
    holder.update(100);

    checkCodes(holder, 1, 0, 0, 0, 0);

    holder.update(102);

    checkCodes(holder, 2, 0, 0, 0, 0);
  }

  @Test(timeout = 60000)
  public void testGetResponse2xx() throws Exception {
    String monitorId = newMonitorId();
    Registry registry = new DefaultRegistry();
    ResponseCodesHolder holder = new ResponseCodesHolder(registry, monitorId);
    holder.update(200);

    checkCodes(holder, 0, 1, 0, 0, 0);

    holder.update(233);

    checkCodes(holder, 0, 2, 0, 0, 0);
  }

  @Test(timeout = 60000)
  public void testGetResponse3xx() throws Exception {
    String monitorId = newMonitorId();
    Registry registry = new DefaultRegistry();
    ResponseCodesHolder holder = new ResponseCodesHolder(registry, monitorId);
    holder.update(300);

    checkCodes(holder, 0, 0, 1, 0, 0);

    holder.update(365);

    checkCodes(holder, 0, 0, 2, 0, 0);
  }

  @Test(timeout = 60000)
  public void testGetResponse4xx() throws Exception {
    String monitorId = newMonitorId();
    Registry registry = new DefaultRegistry();
    ResponseCodesHolder holder = new ResponseCodesHolder(registry, monitorId);
    holder.update(400);

    checkCodes(holder, 0, 0, 0, 1, 0);

    holder.update(452);

    checkCodes(holder, 0, 0, 0, 2, 0);
  }

  @Test(timeout = 60000)
  public void testGetResponse5xx() throws Exception {
    String monitorId = newMonitorId();
    Registry registry = new DefaultRegistry();
    ResponseCodesHolder holder = new ResponseCodesHolder(registry, monitorId);
    holder.update(500);

    checkCodes(holder, 0, 0, 0, 0, 1);

    holder.update(599);

    checkCodes(holder, 0, 0, 0, 0, 2);
  }

  private static void checkCodes(ResponseCodesHolder holder, long xx1, long xx2, long xx3, long xx4, long xx5) {
    assertThat("Invalid 1xx count.", holder.getResponse1xx(), is(xx1));
    assertThat("Invalid 2xx count.", holder.getResponse2xx(), is(xx2));
    assertThat("Invalid 3xx count.", holder.getResponse3xx(), is(xx3));
    assertThat("Invalid 4xx count.", holder.getResponse4xx(), is(xx4));
    assertThat("Invalid 5xx count.", holder.getResponse5xx(), is(xx5));
  }

  private static String newMonitorId() {
    return "monitorId - " + Math.random();
  }
}
