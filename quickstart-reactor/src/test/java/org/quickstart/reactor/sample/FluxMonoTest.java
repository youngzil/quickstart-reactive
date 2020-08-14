package org.quickstart.reactor.sample;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

/**
 * <p>
 * 描述: [功能描述]
 * </p >
 *
 * @author yangzl
 * @version v1.0
 * @date 2020/8/11 09:54
 */
public class FluxMonoTest {

  @Test
  public void testFluxMono() {

    // Reactor中的发布者（Publisher）由Flux和Mono两个类定义，它们都提供了丰富的操作符（operator）。
    // 一个Flux对象代表一个包含0..N个元素的响应式序列，
    // 而一个Mono对象代表一个包含零/一个（0..1）元素的结果。

    // 声明定义
    Flux.just(1, 2, 3, 4, 5, 6);
    Mono.just(1);

    // 通过如下方式声明（分别基于数组、集合和Stream生成）：
    Integer[] array = new Integer[] {1, 2, 3, 4, 5, 6};
    Flux.fromArray(array);

    List<Integer> list = Arrays.asList(array);
    Flux.fromIterable(list);

    Stream<Integer> stream = list.stream();
    Flux.fromStream(stream);

    // 只有完成信号的空数据流
    Flux.just();
    Flux.empty();
    Mono.empty();
    Mono.justOrEmpty(Optional.empty());

    // 只有错误信号的数据流
    Flux.error(new Exception("some error"));
    Mono.error(new Exception("some error"));

    // 订阅前什么都不会发生
    // subscribe方法中的lambda表达式作用在了每一个数据元素上。
    // 这里需要注意的一点是，Flux.just(1, 2, 3, 4, 5, 6)仅仅声明了这个数据流，此时数据元素并未发出，只有subscribe()方法调用的时候才会触发数据流。所以，订阅前什么都不会发生。
    Flux.just(1, 2, 3, 4, 5, 6).subscribe(System.out::print);
    System.out.println();
    Mono.just(1).subscribe(System.out::println);

    // Flux和Mono还提供了多个subscribe方法的变体：

    // 订阅并触发数据流
    // subscribe();
    // 订阅并指定对正常数据元素如何处理
    // subscribe(Consumer<? super T> consumer);
    // 订阅并定义对正常数据元素和错误信号的处理
    // subscribe(Consumer<? super T> consumer,Consumer<? super Throwable> errorConsumer);
    // 订阅并定义对正常数据元素、错误信号和完成信号的处理
    // subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer, Runnable completeConsumer);
    // 订阅并定义对正常数据元素、错误信号和完成信号的处理，以及订阅发生时的处理逻辑
    // subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer, Runnable completeConsumer, Consumer<? super Subscription> subscriptionConsumer);

    Flux.just(1, 2, 3, 4, 5, 6).subscribe(//
        System.out::println, //
        System.err::println, //
        () -> System.out.println("Completed!"));

    Mono.error(new Exception("some error")).subscribe(//
        System.out::println, //
        System.err::println, //
        () -> System.out.println("Completed!"));

  }

  private Flux<Integer> generateFluxFrom1To6() {
    return Flux.just(1, 2, 3, 4, 5, 6);
  }

  private Mono<Integer> generateMonoWithError() {
    return Mono.error(new Exception("some error"));
  }

  @Test
  public void testViaStepVerifier() {
    StepVerifier.create(generateFluxFrom1To6())//
        .expectNext(1, 2, 3, 4, 5, 6)//
        .expectComplete()//
        .verify();

    StepVerifier.create(generateMonoWithError())//
        .expectErrorMessage("some error")//
        .verify();
  }

  @Test
  public void testMap() {

    StepVerifier.create(Flux.range(1, 6) // 1
        .map(i -> i * i)) // 2
        .expectNext(1, 4, 9, 16, 25, 36) // 3
        .expectComplete(); // 4 verifyComplete()相当于expectComplete().verify()。

  }

  @Test
  public void testFlatMap() {

    StepVerifier.create(//
        Flux.just("flux", "mono")//
            .flatMap(s -> Flux.fromArray(s.split("\\s*")) // 1
                .delayElements(Duration.ofMillis(100))) // 2
            .doOnNext(System.out::print)) // 3
        .expectNextCount(8) // 4
        .verifyComplete();

  }

  @Test
  public void testFilter() {

    StepVerifier.create(Flux.range(1, 6).filter(i -> i % 2 == 1) // 1
        .map(i -> i * i)).expectNext(1, 9, 25) // 2
        .verifyComplete();

  }

  private Flux<String> getZipDescFlux() {
    String desc =
        "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
    return Flux.fromArray(desc.split("\\s+")); // 1
  }

  @Test
  public void testZip() throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1); // 2
    Flux.zip(//
        getZipDescFlux(), //
        Flux.interval(Duration.ofMillis(200))) // 3
        .subscribe(t -> System.out.println(t.getT1() + "," + t.getT2()), null, countDownLatch::countDown); // 4
    countDownLatch.await(10, TimeUnit.SECONDS); // 5
  }

  private String getStringSync() {
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "Hello, Reactor!";
  }

  @Test
  public void testSyncToAsync() throws InterruptedException {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    Mono.fromCallable(() -> getStringSync()) // 1
        .subscribeOn(Schedulers.elastic()) // 2
        .subscribe(System.out::println, null, countDownLatch::countDown);
    countDownLatch.await(10, TimeUnit.SECONDS);
  }

  @Test
  public void testErrorHandling() {
    Flux.range(1, 6)//
        .map(i -> 10 / (i - 3)) // 1
        .map(i -> i * i)//
        .subscribe(System.out::println, System.err::println);
  }

  @Test
  public void testErrorHandlingDefaultValue() {
    Flux.range(1, 6)//
        .map(i -> 10 / (i - 3))//
        .onErrorReturn(0) // 1
        .map(i -> i * i)//
        .subscribe(System.out::println, System.err::println);
  }

  @Test
  public void testErrorHandlingResume() {

    Flux.range(1, 6)//
        .map(i -> 10 / (i - 6))//
        .onErrorResume(e -> Mono.just(new Random().nextInt(6))) // 提供新的数据流
        .map(i -> i * i)//
        .subscribe(System.out::println, System.err::println);

  }

  @Test
  public void testErrorHandlingRetry() throws InterruptedException {

    Flux.range(1, 6)//
        .map(i -> 10 / (3 - i))//
        .retry(1)//
        .subscribe(System.out::println, System.err::println);

    Thread.sleep(100); // 确保序列执行完

  }

  @Test
  public void testBackpressure() {
    Flux.range(1, 6) // 1
        .doOnRequest(n -> System.out.println("Request " + n + " values...")) // 2
        .subscribe(new BaseSubscriber<Integer>() { // 3

          @Override
          protected void hookOnSubscribe(Subscription subscription) { // 4
            System.out.println("Subscribed and make a request...");
            request(1); // 5
          }

          @Override
          protected void hookOnNext(Integer value) { // 6
            try {
              TimeUnit.SECONDS.sleep(1); // 7
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            System.out.println("Get value [" + value + "]"); // 8
            request(1); // 9
          }

        });
  }

  @Test
  public void testScheduling() {
    Flux.range(0, 10)//
        .log()//
        .publishOn(Schedulers.newParallel("myParallel"))//
        .log()//
        .subscribeOn(Schedulers.newElastic("myElastic"))//
        .log()//
        .blockLast();
  }

  @Test
  public void testDelayElements() {
    Flux.range(0, 10)//
        .delayElements(Duration.ofMillis(10))//
        .log()//
        .blockLast();
  }

  @Test
  public void testParallelFlux() throws InterruptedException {
    Flux.range(1, 10)//
        .publishOn(Schedulers.parallel())//
        .log().subscribe();
    TimeUnit.MILLISECONDS.sleep(10);
  }

  @Test
  public void testParallelFlux2() throws InterruptedException {
    Flux.range(1, 10)//
        .parallel(2)//
        .runOn(Schedulers.parallel())//
        // .publishOn(Schedulers.parallel())
        .log()//
        .subscribe();

    TimeUnit.MILLISECONDS.sleep(10);
  }

  @Test
  public void testTransform() {
    Function<Flux<String>, Flux<String>> filterAndMap = f -> f.filter(color -> !color.equals("orange"))//
        .map(String::toUpperCase);

    Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))//
        .doOnNext(System.out::println)//
        .transform(filterAndMap)//
        .subscribe(d -> System.out.println("Subscriber to Transformed MapAndFilter: " + d));
  }

  @Test
  public void testCompose() {
    AtomicInteger ai = new AtomicInteger();
    Function<Flux<String>, Flux<String>> filterAndMap = f -> {
      if (ai.incrementAndGet() == 1) {
        return f.filter(color -> !color.equals("orange"))//
            .map(String::toUpperCase);
      }
      return f.filter(color -> !color.equals("purple"))//
          .map(String::toUpperCase);
    };

    Flux<String> composedFlux = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))//
        .doOnNext(System.out::println)//
        .compose(filterAndMap);//这个函数式是针对每一个订阅者起作用的

    composedFlux.subscribe(d -> System.out.println("Subscriber 1 to Composed MapAndFilter :" + d));
    composedFlux.subscribe(d -> System.out.println("Subscriber 2 to Composed MapAndFilter: " + d));
  }

}
