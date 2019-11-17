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

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import rx.functions.Func1;

public class AcceptOnlyBinaryFramesFilter implements Func1<WebSocketFrame, Boolean> {

  public static final AcceptOnlyBinaryFramesFilter INSTANCE = new AcceptOnlyBinaryFramesFilter();

  private AcceptOnlyBinaryFramesFilter() {}

  @Override
  public Boolean call(WebSocketFrame f) {
    if (f instanceof BinaryWebSocketFrame) {
      return true;
    } else {
      f.release();
      return false;
    }
  }
}
