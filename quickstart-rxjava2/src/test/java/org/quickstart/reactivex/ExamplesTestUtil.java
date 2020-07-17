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
package org.quickstart.reactivex.rxnetty.examples;

import java.util.Queue;

public final class ExamplesTestUtil {

  private ExamplesTestUtil() {}

  public static Queue<String> runClientInMockedEnvironment(Class<?> exampleClass) {
    ExamplesMockLogger mockLogger = new ExamplesMockLogger(exampleClass);
    Queue<String> output = mockLogger.getLogMessages();
    ExamplesEnvironment.overrideEnvironment(exampleClass, new ExamplesEnvironment(exampleClass, mockLogger.getMock()));
    ExamplesEnvironment.invokeExample(exampleClass, new String[0]);
    return output;
  }
}
