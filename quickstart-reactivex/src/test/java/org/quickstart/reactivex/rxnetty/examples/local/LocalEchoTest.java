package org.quickstart.reactivex.rxnetty.examples.local;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

import java.util.Queue;

import org.junit.Ignore;
import org.junit.Test;
import org.quickstart.reactivex.rxnetty.examples.ExamplesTestUtil;

@Ignore("travis doesn't like me")
public class LocalEchoTest {

  @Test(timeout = 60000)
  public void testEcho() throws Exception {
    Queue<String> output = ExamplesTestUtil.runClientInMockedEnvironment(LocalEcho.class);

    assertThat("Unexpected number of messages echoed", output, hasSize(1));
    assertThat("Unexpected number of messages echoed", output, contains("echo => Hello World!"));
  }
}
