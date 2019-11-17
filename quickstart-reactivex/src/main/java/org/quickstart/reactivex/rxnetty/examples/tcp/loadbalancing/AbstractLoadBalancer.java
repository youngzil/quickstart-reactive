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

import java.net.SocketException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.netty.channel.Connection;
import io.reactivex.netty.client.ConnectionProvider;
import io.reactivex.netty.client.HostConnector;
import io.reactivex.netty.client.events.ClientEventListener;
import io.reactivex.netty.client.loadbalancer.HostHolder;
import io.reactivex.netty.client.loadbalancer.LoadBalancingStrategy;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.functions.Func1;

public abstract class AbstractLoadBalancer<W, R> implements LoadBalancingStrategy<W, R> {

  private final Func1<Integer, Integer> nextIndexFinder;

  protected AbstractLoadBalancer(Func1<Integer, Integer> nextIndexFinder) {
    this.nextIndexFinder = nextIndexFinder;
  }

  protected AbstractLoadBalancer() {
    this(new Func1<Integer, Integer>() {
      private final AtomicInteger nextIndex = new AtomicInteger();

      @Override
      public Integer call(Integer maxValue) {
        return nextIndex.incrementAndGet() % maxValue;
      }
    });
  }

  @Override
  public ConnectionProvider<W, R> newStrategy(final List<HostHolder<W, R>> hosts) {

    final int size = hosts.size();

    return () -> Observable.create((OnSubscribe<Connection<R, W>>) subscriber -> {

      ConnectionProvider<W, R> hostToUse;

      HostHolder<W, R> host1 = hosts.get(nextIndexFinder.call(size));
      HostHolder<W, R> host2 = hosts.get(nextIndexFinder.call(size));

      long weight1 = getWeight(host1.getEventListener());
      long weight2 = getWeight(host2.getEventListener());

      if (weight1 >= weight2) {
        hostToUse = host1.getConnector().getConnectionProvider();
      } else {
        hostToUse = host2.getConnector().getConnectionProvider();
      }

      hostToUse.newConnectionRequest().unsafeSubscribe(subscriber);

    }).retry((count, th) -> count < 3 && th instanceof SocketException);
  }

  @Override
  public final HostHolder<W, R> toHolder(HostConnector<W, R> connector) {
    return new HostHolder<>(connector, newListener());
  }

  protected abstract ClientEventListener newListener();

  protected abstract long getWeight(ClientEventListener eventListener);
}
