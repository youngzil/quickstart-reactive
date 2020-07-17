/**
 * 项目名称：quickstart-reactivex 文件名：RxJavaTest.java 版本信息： 日期：2018年5月19日 Copyright yangzl Corporation 2018 版权所有 *
 */
package org.quickstart.reactivex.rxjava1;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * RxJavaTest
 *
 * https://blog.csdn.net/bingduanlbd/article/details/51836132
 *
 * @author：youngzil@163.com
 * @2018年5月19日 下午9:13:11
 * @since 1.0
 */
public class RxJavaTest {

  // Observable.from(T[]array)将数组转化为Observable对象，并将数组中的元素作为流对象元素发布。除了从数组外，还可以从Iterable、Future等对象转化为Observable。Observable对象是ReactiveX的核心，代表着被观察的对象，或者说是事件流的抽象。更多信息参考doc文档：http://reactivex.io/RxJava/javadoc/
  // from方法返回的是Observable对象，调用该对象的subscribe方法，可以订阅该对象，在该对象发布事件（item）时得到相应的调用。其中Action1表示一个参数的回掉，也就是call(）方法带有一个参数。除了Action1，还有Action2，Action3,…ActionN.

  public static void main(String[] args) {
    hello("Brandon", "Braney", "world");

    // 事件源和订阅者
    // 从数组创建一个Observable
    Integer[] arr = {1, 2, 3};
    Observable<Integer> observable = Observable.from(arr);

    // 订阅前面创建在Observable
    observable.subscribe(new Subscriber<Integer>() {
      // Subscriber是抽象类, 继承了Observer接口, 但是未实现其中的方法
      @Override
      public void onNext(Integer item) {
        // 处理接收到的事件
        System.out.println(item);
      }

      @Override
      public void onCompleted() {}

      @Override
      public void onError(Throwable e) {}
    });

    // 只订阅Next事件
    Integer[] arr2 = {1, 2, 3};
    Observable<Integer> observable2 = Observable.from(arr2);

    observable2.subscribe(new Action1<Integer>() {
      @Override
      public void call(Integer item) {
        System.out.println(item);
      }
    });

    // 示例: 将数字一一映射成字符串
    // map操作符可以将Observable里元素转换成另一种元素. 例如, 我们可以将刚刚创建数字一一映射成字符串.
    Integer[] arr3 = {1, 2, 3};
    Observable<Integer> observable3 = Observable.from(arr3);

    Observable<String> observable31 = observable3.map(new Func1<Integer, String>() {
      @Override
      public String call(Integer item) {
        return "number is: " + item; // 将每个元素映射成一句话
      }
    });

    observable31.subscribe(new Action1<String>() {
      @Override
      public void call(String item) {
        System.out.println(item); // will print: the number is x
      }
    });

    // 除了 subscribe(Observer) 和 subscribe(Subscriber) ，subscribe() 还支持不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber 。形式如下
    Action1<String> onNextAction = new Action1<String>() {
      // onNext()
      @Override
      public void call(String s) {
        System.out.println("onNext()=" + s);
      }
    };
    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
      // onError()
      @Override
      public void call(Throwable throwable) {
        // Error handling
        System.out.println("onError()=" + throwable);
      }
    };
    Action0 onCompletedAction = new Action0() {
      // onCompleted()
      @Override
      public void call() {
        System.out.println("onCompleted()===");
      }
    };

    Observable<String> observable4 = Observable.just("s", "ss", "sss");
    // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
    observable4.subscribe(onNextAction);
    // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
    observable4.subscribe(onNextAction, onErrorAction);
    // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
    observable4.subscribe(onNextAction, onErrorAction, onCompletedAction);

  }

  public static void hello(String... names) {
    Observable.from(names).subscribe(new Action1<String>() {

      @Override
      public void call(String s) {
        System.out.println("Hello " + s + "!");
      }

    });
  }

}
