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

package org.quickstart.reactivex.rxnetty.examples.http.interceptors.transformation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

import java.util.Queue;

import org.junit.Test;
import org.quickstart.reactivex.rxnetty.examples.ExamplesTestUtil;

import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.reactivex.netty.protocol.http.internal.HttpMessageFormatter;

public class TransformingInterceptorTest {

  @Test(timeout = 60000)
  public void testTransformingInterceptor() throws Exception {

    Queue<String> output = ExamplesTestUtil.runClientInMockedEnvironment(InterceptingClient.class);

    HttpResponse expectedHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    expectedHeader.headers().add(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
    String expectedHeaderString = HttpMessageFormatter.formatResponse(expectedHeader.protocolVersion(), expectedHeader.status(),
        expectedHeader.headers().iteratorCharSequence());

    assertThat("Unexpected number of messages echoed", output, hasSize(3));

    assertThat("Unexpected response.", output, contains(expectedHeaderString, "1", "2"));
  }
}
