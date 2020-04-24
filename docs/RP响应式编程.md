
1、Reactive Programming(RP),响应式编程
2、响应式编程框架
3、响应式宣言
4、
5、
6、
7、
8、
9、


学习网站
https://www.infoq.cn/article/rxjava-by-example/

---------------------------------------------------------------------------------------------------------------------
Reactive Programming(RP),响应式编程

Observer Design Pattern 观察者模式


1、什么是反应式编程？
响应式编程是一种编程范例，用于处理异步数据流（事件的顺序）和特定的更改传播，这意味着它以一定顺序对执行环境（上下文）进行了修改。 

2、为什么我们需要Java中的“反应性”？
当涉及到大量的数据或者多userness，我们经常需要异步处理，以使我们的快速反应系统。在Java（旧的面向对象编程的代表）中，异步性会变得很麻烦，并使代码难以理解和维护。因此，响应式编程对于这种“纯”面向对象的环境特别有益，因为它简化了异步流的处理。 

3、如何使用Java进行反应？
在其最新版本（从Java 8开始）中，Java本身已经进行了一些尝试来引入内置的反应性，但是迄今为止，这些尝试在开发人员中并不十分流行。但是，对于Java中的响应式编程，有一些实时且定期更新的第三方实现，这些实现有助于节省时间，因此受到Java开发人员的特别爱戴和珍惜。

RxJava是第一个特定于Java平台的Reactive Extension API。它与Java 6一起使用，并提供了为Java和Android Java编写基于事件的异步程序的机会，这非常方便。

Spring Reactor是Spring开发人员的另一个Java框架。它与RxJava非常相似，但是具有更简单的抽象。由于可以利用Java 8的优势，该框架已成功赢得欢迎。

4、在现实生活中，使用RP有什么好处？
性能提高 –由于可以快速，稳定地处理大量数据。
改进的UX –由于可以保持应用程序对用户的响应速度更快。
简化的修改和更新 -由于更具可读性和更容易预测代码。

5、何时使用RP？  
  去反应性提供了一个很好的解决方案，当涉及到特定类型的高负载或多用户应用

6、什么时候不使用RP？
  简而言之，不要尝试将RP应用于不需要的地方，例如，在没有“实时”数据，高负载或大量用户同时更改数据的地方。




响应式编程是一种异步编程范例，涉及数据流和更改的传播。-维基百科
ReactiveX或Rx是用于反应式编程的最流行的API。它基于可观察模式，迭代器模式和函数式编程的思想。Rx具有用于不同语言的库，但是我们将使用RxJS。


在反应式编程定义中有一个重要的词：
1、asynchronous：当数据在流中异步发出时，您会收到通知，这意味着独立于主程序流。通过围绕数据流构造程序
2、threads：观察流并在发生有趣的事情时收到通知是很高兴的，但是您绝不能忘记谁在调用您，或更确切地说，是在哪个线程上执行您的函数。强烈建议您避免在程序中使用太多线程。依赖多个线程的异步程序成为一个棘手的同步难题，通常以死锁搜寻结束。
3、never block：因为您不拥有调用您的线程，所以必须确保永远不要阻止它。



Reactive Programming != Reactive System
反应式编程 != 反应式系统

反应系统具有以下四个特性：
1、Responsive响应式：响应式系统需要在合理的时间内处理请求（我让您定义合理的）。
2、Resilient弹性：响应式系统必须在出现故障（崩溃，超时，500错误……）时保持响应能力，因此必须针对故障进行设计并进行适当处理。
3、Elastic弹性：反应系统必须在各种负载下保持响应能力。因此，它必须向上和向下扩展，并能够以最少的资源处理负载。
4、Message driven消息驱动：反应系统中的组件使用异步消息传递进行交互。




Stream 就是一个按时间排序的 Events 序列,它可以放射三种不同的 Events：(某种类型的)Value、Error 或者一个" Completed" Signal。考虑一下"Completed"发生的时机，例如，当包含这个按钮的窗口或者视图被关闭时。

通过分别为 Value、Error、"Completed"定义事件处理函数，我们将会异步地捕获这些 Events。有时可以忽略 Error 与"Completed"，你只需要定义 Value 的事件处理函数就行。监听一个 Stream 也被称作是订阅 ，而我们所定义的函数就是观察者，Stream则是被观察者，其实就是 。




参考
https://www.scnsoft.com/blog/java-reactive-programming
https://gist.github.com/staltz/868e7e9bc2a7b8c1f754
https://egghead.io/courses/introduction-to-reactive-programming
https://dzone.com/articles/5-things-to-know-about-reactive-programming
https://medium.com/@kevalpatel2106/what-is-reactive-programming-da37c1611382
https://www.freecodecamp.org/news/an-introduction-to-functional-reactive-programming-in-redux-b0c14d097836/
https://spring.io/blog/2016/06/07/notes-on-reactive-programming-part-i-the-reactive-landscape
https://wiki.jikexueyuan.com/project/android-weekly/issue-145/introduction-to-RP.html


---------------------------------------------------------------------------------------------------------------------
响应式编程框架

RxJava是第一个特定于Java平台的Reactive Extension API。它与Java 6一起使用，并提供了为Java和Android Java编写基于事件的异步程序的机会，这非常方便。

Spring Reactor是Spring开发人员的另一个Java框架。它与RxJava非常相似，但是具有更简单的抽象。由于可以利用Java 8的优势，该框架已成功赢得欢迎。

https://github.com/ReactiveX
ReactiveX或Rx是用于反应式编程的最流行的API。它基于可观察模式，迭代器模式和函数式编程的思想。
RX为您提供了超能力。您拥有组合，合并，过滤，转换和创建数据流的功能库。
Rx具有用于不同语言的库，RxJava、RxNetty、RxJS、RxPY、RxCpp、RxGo、RxKotlin、RxScala、RxGroovy、RxAndroid、RxPHP、RxRuby


RxJava弹珠交互图
https://rxmarbles.com/
https://github.com/staltz/rxmarbles




reactor框架
http://projectreactor.io
http://www.reactive-streams.org/
https://github.com/reactor/reactor-core



Akka框架



---------------------------------------------------------------------------------------------------------------------

响应式宣言
https://www.reactivemanifesto.org/ribbons
https://www.reactivemanifesto.org/
https://www.reactivemanifesto.org/zh-CN


参考
https://www.infoq.cn/article/2016/01/reactive-basics
https://blog.csdn.net/get_set/article/details/79506602










---------------------------------------------------------------------------------------------------------------------




