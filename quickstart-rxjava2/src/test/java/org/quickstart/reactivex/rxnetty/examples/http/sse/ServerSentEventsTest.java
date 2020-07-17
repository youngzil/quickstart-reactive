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

package org.quickstart.reactivex.rxnetty.examples.http.sse;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.TRANSFER_ENCODING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.Queue;

import org.junit.Test;
import org.quickstart.reactivex.rxnetty.examples.ExamplesTestUtil;

import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.reactivex.netty.protocol.http.internal.HttpMessageFormatter;

public class ServerSentEventsTest {

  @Test(timeout = 60000)
  public void testSse() throws Exception {
    Queue<String> output = ExamplesTestUtil.runClientInMockedEnvironment(HelloSseClient.class);

    HttpResponse expectedHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    expectedHeader.headers().add(TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
    expectedHeader.headers().add(CONTENT_TYPE, "text/event-stream");
    String expectedHeaderString = HttpMessageFormatter.formatResponse(expectedHeader.protocolVersion(), expectedHeader.status(),
        expectedHeader.headers().iteratorCharSequence());

    String[] expectedContent = new String[10];
    for (int i = 0; i < 10; i++) {
      expectedContent[i] = "data: Interval => " + i + '\n';
    }

    assertThat("Unexpected number of messages echoed", output, hasSize(expectedContent.length + 1));

    assertThat("Unexpected header", output.poll()/*Remove the header.*/, is(expectedHeaderString));

    assertThat("Unexpected content", output, contains(expectedContent));
  }
}
