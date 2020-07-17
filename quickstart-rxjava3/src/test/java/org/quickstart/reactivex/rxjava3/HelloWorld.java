package org.quickstart.reactivex.rxjava3;

import io.reactivex.rxjava3.core.Flowable;

/**
 * @author yangzl
 * @description TODO
 * @createTime 2020/7/17 15:22
 */
public class HelloWorld {

  public static void main(String[] args) {
    Flowable.just("Hello world").subscribe(System.out::println);
  }

}
