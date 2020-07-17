package org.quickstart.reactivex.rxjava3;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author yangzl
 * @description TODO
 * @createTime 2020/7/17 16:50
 */
public class Example {

  @Test
  public void test() {
    Flowable<Integer> flow = Flowable.range(1, 5).map(v -> v * v).filter(v -> v % 3 == 0);
    flow.subscribe(System.out::println);

  }

  @Test
  public void test2() {
    Observable.create(emitter -> {
      while (!emitter.isDisposed()) {
        long time = System.currentTimeMillis();
        emitter.onNext(time);
        if (time % 2 != 0) {
          emitter.onError(new IllegalStateException("Odd millisecond!"));
          break;
        }
      }
    }).subscribe(System.out::println, Throwable::printStackTrace);
  }

  @Test
  public void test3() throws InterruptedException {
    Flowable.fromCallable(() -> {
      Thread.sleep(1000); // imitate expensive computation
      return "Done";
    })//
        .subscribeOn(Schedulers.io())//
        .observeOn(Schedulers.single())//
        .subscribe(System.out::println, Throwable::printStackTrace);

    Thread.sleep(2000); // <--- wait for the flow to finish
  }

  @Test
  public void test4() throws InterruptedException {

    Flowable<String> source = Flowable.fromCallable(() -> {
      Thread.sleep(1000); // imitate expensive computation
      return "Done";
    });

    Flowable<String> runBackground = source.subscribeOn(Schedulers.io());

    Flowable<String> showForeground = runBackground.observeOn(Schedulers.single());

    showForeground.subscribe(System.out::println, Throwable::printStackTrace);

    Thread.sleep(2000);

  }

  @Test
  public void test5() {

    Flowable.range(1, 10)//
        .observeOn(Schedulers.computation())//
        .map(v -> v * v)//
        .blockingSubscribe(System.out::println);

  }

  @Test
  public void test6() {

    Flowable.range(1, 10)//
        .flatMap(v -> Flowable.just(v)//
            .subscribeOn(Schedulers.computation())//
            .map(w -> w * w))//
        .blockingSubscribe(System.out::println);

  }

  @Test
  public void test7() {

    Flowable.range(1, 10)//
        .parallel()// 或者ParallelFlowable
        .runOn(Schedulers.computation())//
        .map(v -> v * v)//
        .sequential()//
        .blockingSubscribe(System.out::println);

  }

  @Test
  public void test8() {
    AtomicInteger count = new AtomicInteger();

    Observable.range(1, 10)//
        .doOnNext(ignored -> count.incrementAndGet())//
        .ignoreElements()//
        .andThen(Single.just(count.get()))//
        .subscribe(System.out::println);
  }

  @Test
  public void test9() {
    AtomicInteger count = new AtomicInteger();

    Observable.range(1, 10)//
        .doOnNext(ignored -> count.incrementAndGet())//
        .ignoreElements()//
        .andThen(Single.defer(() -> Single.just(count.get())))//
        .subscribe(System.out::println);
  }

  @Test
  public void test10() {

    AtomicInteger count = new AtomicInteger();

    Observable.range(1, 10)//
        .doOnNext(ignored -> count.incrementAndGet())//
        .ignoreElements()//
        .andThen(Single.fromCallable(() -> count.get()))//
        .subscribe(System.out::println);

  }

}
