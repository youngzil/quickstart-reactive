- [响应式编程介绍](#响应式编程介绍)：Reactive Programming(RP)
    - [什么是反应式编程](#什么是反应式编程)
    - [为什么我们需要Java中的“反应性”](#为什么我们需要Java中的“反应性”)
    - [如何使用Java进行反应](#如何使用Java进行反应)
    - [在现实生活中使用RP有什么好处](#在现实生活中使用RP有什么好处)
    - [反应式系统和反应式编程](#反应式系统和反应式编程)
- [响应式编程学习](#响应式编程学习)
    - [性能之争：响应式编程真的有效吗？](#性能之争-响应式编程真的有效吗)
    - [响应式编程概念学习](#响应式编程概念学习)
    - [命令式编程和声明式编程](#命令式编程和声明式编程)
    - [回调地狱](#回调地狱.md)
    - [Actor模型](#Actor模型.md)
    - [Actor模型和CSP模型](#Actor模型和CSP模型.md)
    - [构建反应式微服务架构](#building_reactive_microservices_in_java.pdf)
- [响应式编程框架](#响应式编程框架)
    - [响应式编程规范](#响应式编程规范)
    - [响应式宣言](#响应式宣言)
    - [RxJava](#RxJava)：（Netflix开发，Java平台） 
        - [RxJava介绍](#RxJava介绍)
        - [RXJava1学习](RXJava/RXJava1.x学习.md)
        - [RxJava2学习](RXJava/RxJava学习.md)
        - [RXJava3学习](RXJava/RXJava3学习.md)
    - [Akka](#Akka)：（Lightbend（以前称为Typesafe），Scala语言）
        - [Akka介绍](#Akka介绍)
    - [Reactor框架](#Reactor框架)：（Pivatol开发，WebFlux 则是以 Reactor 为基础实现了 Web 领域的反应式编程框架）
        - [Reactor介绍](#Reactor介绍)
    - [Play2框架](Play2框架.md)

---------------------------------------------------------------------------------------------------------------------

## 响应式编程介绍

Reactive Programming(RP),响应式编程

Observer Design Pattern 观察者模式

命令式编程（指令式编程）、声明式编程、响应式编程  
命令式编程（Imperative）：详细的命令机器怎么（How）去处理一件事情以达到你想要的结果（What）；  
声明式编程（Declarative）：只告诉你想要的结果（What），机器自己摸索过程（How）。  


### 响应式编程有2个典型的例子：  
1、Excel，当单元格变化了，相互之间的单元格也会立即变化。  
2、Autolayout，当父View变化了，根据相互之间的关系Constraint，子View的frame也会随之变化。  


### 响应式的核心特点之一：  
1、变化传递（propagation of change）  
2、基于数据流（data stream），这些数据/事件在响应式编程里会以数据流的形式发出。  
3、声明式（declarative）”的编程范式，命令式是面向过程的，声明式是面向结构的。声明式比较适合基于流的处理方式  

总结起来，响应式编程（reactive programming）是一种基于数据流（data stream）和变化传递（propagation of change）的声明式（declarative）的编程范式。  


## 响应式编程好处
根据个人经验来看，响应式编程至少有如下好处：  
1、在业务层面实现代码逻辑分离，方便后期维护和拓展：不会存在共享状态  
2、减少线程阻塞，极大提高程序响应速度，充分发掘CPU的能力：网络IO、磁盘IO的时候，线程可以去做其他的事情，不会阻塞  
3、帮助开发者提高代码的抽象能力和充分理解业务逻辑  
4、Rx丰富的操作符会帮助我们极大的简化代码逻辑  

1、无锁化，不需要处理共享数据
2、减少线程阻塞



### 什么是反应式编程
响应式编程是一种编程范例，用于处理异步数据流（事件的顺序）和特定的更改传播，这意味着它以一定顺序对执行环境（上下文）进行了修改。 
反应式编程 (reactive programming) 是一种基于数据流 (data stream) 和 变化传递 (propagation of change) 的 声明式 (declarative) 的编程范式。  

在计算机领域，响应式编程是一个专注于数据流和变化传递的异步编程范式。
这意味着可以使用编程语言很容易地表示静态（例如数组）或动态（例如事件发射器）数据流，
并且在关联的执行模型中，存在着可推断的依赖关系，这个关系的存在有利于自动传播与数据流有关的更改。

编程是为了解决问题，而解决问题可以有多种视角和思路，其中具有普适性的模式被归结为范式。
面向对象、面向过程都是编程范式。
响应式编程是一种从数据流和变化出发的解决问题的模式。
响应式编程，本质上是对数据流或某种变化所作出的反应，但是这个变化什么时候发生是未知的，所以是基于异步、回调的方式在处理问题。



### 为什么我们需要Java中的“反应性”
当涉及到大量的数据或者多userness，我们经常需要异步处理，以使我们的快速反应系统。在Java（旧的面向对象编程的代表）中，异步性会变得很麻烦，并使代码难以理解和维护。因此，响应式编程对于这种“纯”面向对象的环境特别有益，因为它简化了异步流的处理。 



### 如何使用Java进行反应
在其最新版本（从Java 8开始）中，Java本身已经进行了一些尝试来引入内置的反应性，但是迄今为止，这些尝试在开发人员中并不十分流行。但是，对于Java中的响应式编程，有一些实时且定期更新的第三方实现，这些实现有助于节省时间，因此受到Java开发人员的特别爱戴和珍惜。

RxJava是第一个特定于Java平台的Reactive Extension API。它与Java 6一起使用，并提供了为Java和Android Java编写基于事件的异步程序的机会，这非常方便。

Spring Reactor是Spring开发人员的另一个Java框架。它与RxJava非常相似，但是具有更简单的抽象。由于可以利用Java 8的优势，该框架已成功赢得欢迎。



### 在现实生活中使用RP有什么好处
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


### 反应式系统和反应式编程
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

## 响应式编程框架



## 响应式编程规范

Reactive Streams 项目（规范）：反应式编程相关的规范以及接口。 

响应式流规范（Reactive Stream Specification）：接口规范，实现的框架有：RxJava、reactor
因此基于响应式流的实现属于第三代，它的实现包括 RxJava 2.x，Project Reactor 和 Akka-Streams等。
https://github.com/reactive-streams/reactive-streams-jvm
http://www.reactive-streams.org/

响应式流规范定义了四个接口，如下：
1.Publisher是能够发出元素的发布者。
2.Subscriber是接收元素并做出响应的订阅者。
3.Subscription是Publisher和Subscriber的“中间人”。
4.Processor集Publisher和Subscriber于一身。

这四个接口在JEP 266跟随Java 9版本被引入了Java SDK。


操作符熔合
https://github.com/reactor/reactive-streams-commons
https://blog.csdn.net/get_set/article/details/79799922


参考  
https://cloud.tencent.com/developer/news/327778  



### 响应式宣言

响应式宣言（反应式宣言）

https://www.reactivemanifesto.org/ribbons  
https://www.reactivemanifesto.org/  
https://www.reactivemanifesto.org/zh-CN  

参考  
https://www.infoq.cn/article/2016/01/reactive-basics  
https://blog.csdn.net/get_set/article/details/79506602  


不过也应该看到，也正是由于响应式宣言中对现代系统的Responsive、Resilient、Elastic和Message Driven的要求，使得对响应式编程技术的呼声越来越高，显然响应式编程技术是构建响应式系统的合适工具之一。尤其是随着面向响应式宣言的响应式流规范（Reactive Streams Specification）这一顶层设计的提出，类似Reactor、RxJava、Vert.x、Spring WebFlux等的响应式编程技术在响应式系统中必将发挥越来越大的作用。


在这个宣言里面，对于响应式的系统特征定义了四个特性：
    及时响应(Responsive)：系统能及时的响应请求。
    有韧性(Resilient)：系统在出现异常时仍然可以响应，即支持容错。
    有弹性(Elastic)：在不同的负载下，系统可弹性伸缩来保证运行。
    消息驱动(Message Driven)：不同组件之间使用异步消息传递来进行交互，并确保松耦合及相互隔离。




---------------------------------------------------------------------------------------------------------------------
## RxJava

### RxJava介绍
RxJava（Netflix开发，Java平台）    

RxJava是第一个特定于Java平台的Reactive Extension API。它与Java 6一起使用，并提供了为Java和Android Java编写基于事件的异步程序的机会，这非常方便。


https://github.com/ReactiveX  

ReactiveX或Rx是用于反应式编程的最流行的API。它基于可观察模式，迭代器模式和函数式编程的思想。

RX为您提供了超能力。您拥有组合，合并，过滤，转换和创建数据流的功能库。

Rx具有用于不同语言的库，RxJava、RxNetty、RxJS、RxPY、RxCpp、RxGo、RxKotlin、RxScala、RxGroovy、RxAndroid、RxPHP、RxRuby


RxJava弹珠交互图  
https://rxmarbles.com/  
https://github.com/staltz/rxmarbles  

        


参考  
https://www.infoq.cn/article/rxjava-by-example/  
https://www.infoq.com/articles/Refactoring-Reactive-JDBC/  


---------------------------------------------------------------------------------------------------------------------
## Akka


### Akka介绍

Akka（Lightbend（以前称为Typesafe），Scala语言）

Akka（Akka用Scala语言编写，同时提供了Scala和Java的开发接口。）  

Lightbend（以前称为Typesafe）是由Scala编程语言的创建者Martin Odersky，Akka中间件的创建者JonasBonér和2011年的Paul Phillips共同创立的公司。



---------------------------------------------------------------------------------------------------------------------
## Reactor框架


### Reactor介绍

Reactor（Pivatol开发，WebFlux 则是以 Reactor 为基础实现了 Web 领域的反应式编程框架）  

Spring Reactor是Spring开发人员的另一个Java框架。它与RxJava非常相似，但是具有更简单的抽象。由于可以利用Java 8的优势，该框架已成功赢得欢迎。

reactor框架  
http://projectreactor.io  
http://www.reactive-streams.org/  
https://github.com/reactor/reactor-core  


学习Reactor Core 3.x提供的lite Rx API 。  
https://github.com/reactor/lite-rx-api-hands-on



参考  
https://blog.csdn.net/get_set/category_7484996.html  
https://blog.csdn.net/get_set/category_9272724.html  




---------------------------------------------------------------------------------------------------------------------
## 响应式编程学习


### 性能之争-响应式编程真的有效吗

性能之争：响应式编程真的有效吗？
https://www.infoq.cn/article/xYCWYK9*TfmpFNO6RkWt


Project Reactor 是 Spring 的 Reactive Web Framework 的基础，目前支持测试，调试等等。
https://projectreactor.io/docs/core/release/reference/


JDK的Project loom 结合了线程模型
https://cr.openjdk.java.net/~rpressler/loom/Loom-Proposal.html

响应式编程为 Java 的企业版应用提供了更高的性能，并降低了内存消耗，主要是通过减少进程的上下文切换来实现的。因为类似的上下文切换对 CPU 和内存的消耗是极大，所以要尽可能的减少这样的切换操作。
不过，响应式编程带来的这种性能上的提高，代价是降低了软件的维护性，这样的代价交换是否值得呢？

并行计算的时间应明显高于 Java 将线程实现为操作系统线程的时间，但是目前请求的执行时间很短，操作系统进程上下文的切换时间很长，完全不成比例。
响应式编程的好处就是执行的代码和执行的线程是分开的。因此在操作系统的层面上，上下文切换的代价比较低。

响应式编程也有一些比较严重的问题，写入的代码和执行的代码分离开来，导致阅读和编写代码的难度增加，对于这种异步的代码，编写单元测试和调试代码都很困难。



响应式编程为 Java 的企业版应用提供了更高的性能，并降低了内存消耗，主要是通过减少进程的上下文切换来实现的。因为类似的上下文切换对 CPU 和内存的消耗是极大，所以要尽可能的减少这样的切换操作。
不过，响应式编程带来的这种性能上的提高，代价是降低了软件的维护性，这样的代价交换是否值得呢？

但是目前，Java 的线程在实现上有一个严重的缺陷：Java 线程的实现被操作系统认定为系统进程，这使得线程的切换等价于操作系统的上下文切换，这个代价非常高。
如果一个应用程序每分钟内只处理几千个请求，或许没什么问题。可是目前 Web 程序的需求与日俱增，不断增长的用户量和请求应用致使企业应用现在每分钟处理的数据量远远高于 15 年前。由操作系统线程来处理请求的这种模型渐渐地进入了瓶颈，尤其是当你在程序执行的过程中，阻塞住当前程序来访问数据库或者访问其他的微服务，这种情境下特别明显。

并行计算的时间应明显高于 Java 将线程实现为操作系统线程的时间，但是目前请求的执行时间很短，操作系统进程上下文的切换时间很长，完全不成比例。



而这正是响应式编程的用武之地，它与目前的 Java 线程模型完全相反。目前的线程模型是保证所有的事情都在当前线程执行，但是在响应式编程中，异步是一个准则。一个程序执行过程被认定为一系列异步执行的事件，每个事件都被 Publisher 创建，你不需要关心 Publisher 在哪个线程中创建。在响应式程序里面，程序代码包含了监听和执行异步事件的功能，而且会在必要时提供新的事件。

这个方式在某些场景下很有效果，比如说在程序里面访问外部数据库。传统的企业级 Java 程序里面，系统会发送一段 SQL 语句到数据库上面，阻塞住程序，直到数据库返回查询结果。但是在响应式编程里面，程序会跳过等待结果的过程，正常向下执行代码。当你发送一个 SQL 请求到数据库的时候，会用一个 Pushlisher 来替代阻塞的过程，调用者可以注册这个 Pushlisher，这样的话，在之后数据库返回结果的时候，就会通知 Publisher，然后 Publisher 会通知调用者。

响应式编程的好处就是执行的代码和执行的线程是分开的。因此在操作系统的层面上，上下文切换的代价比较低。

但是正如文章开篇里面提到的，响应式编程也有一些比较严重的问题，写入的代码和执行的代码分离开来，导致阅读和编写代码的难度增加，对于这种异步的代码，编写单元测试和调试代码都很困难。





---------------------------------------------------------------------------------------------------------------------
### 响应式编程概念学习

响应式编程是一种通过异步和数据流来构建事务关系的编程模型。这里每个词都很重要，“事务的关系”是响应式编程的核心理念，“数据流”和“异步”是实现这个核心理念的关键。
异步和数据流都是为了正确的构建事务的关系而存在的。只不过，异步是为了区分出无关的事务，而数据流（事件流）是为了联系起有关的事务。
Reactive编程 是面向数据流的、异步化的编程范式


为啥不用Java Stream来进行数据流的操作？ 原因在于，若将其用于响应式编程中，是有局限性的。比如如下两个需要面对的问题：
1、Web 应用具有I/O密集的特点，I/O阻塞会带来比较大的性能损失或资源浪费，我们需要一种异步非阻塞的响应式的库，而Java Stream是一种同步API。
2、假设我们要搭建从数据层到前端的一个变化传递管道，可能会遇到数据层每秒上千次的数据更新，而显然不需要向前端传递每一次更新，这时候就需要一种流量控制能力，就像我们家里的水龙头，可以控制开关流速，而Java Stream不具备完善的对数据流的流量控制的能力。

具备“异步非阻塞”特性和“流量控制”能力的数据流，我们称之为响应式流（Reactive Stream）。




响应式编程技术通常用于在单个节点或服务中对数据流进行异步非阻塞的处理。当有多个结点时，就需要认真考量数据一致性（data consistency）、跨结点沟通（cross-node communication）、协调（coordination）、版本控制（versioning）、编排（orchestration）、错误管理（failure management）、关注与责任（concerns and responsibilities）分离等等的内容——这些都是响应式系统架构要考虑的内容。

类似的，Spring WebFlux是一种响应式编程框架，用于开发响应式应用，而Spring Cloud不仅是更是一套适应于当今云原生环境下微服务架构基础，更加接近响应式宣言的目标和响应式系统的设计原则。


响应式编程，即 Reactive Programming。它是一种基于事件模式的模型。在上面的异步编程模式中，我们描述了两种获得上一个任务执行结果的方式，
一个就是主动轮询，我们把它称为 Proactive 方式；
另一个就是被动接收反馈，我们称为 Reactive 方式。


在观察者模式中，可以分为两种模式，push和pull:
1.push“推模式“，就是被监听者将消息推送出去，进而触发监听者的相应事件。如上面的事例代码就是采用这种方式，响应式编程一般采用这种模式。
2.pull"拉模式”，就是监听者主动从被监听者处获取数据。




事件驱动架构(Event Driven Architecture, EDA)
核心概念：消息代理、发布者/订阅者、讯息、频道

为什么是“事件驱动”而不是“消息驱动”？
您会发现两者可以互换使用，尽管它们并不完全相同。您甚至会发现“基于消息”和“基于事件”。实际上，它们都可能指的是同一件事。
从理论上讲，“消息驱动”是最通用的术语，意味着您可以使用事件和命令，而事件驱动意味着它纯粹是关于事件的。
但是，并非总是如此，正如Martin Fowler在他的演讲中解释的那样：“事件驱动架构的许多含义”




参考
https://zhuanlan.zhihu.com/p/34445114
https://www.gryen.com/articles/show/25.html
https://zhuanlan.zhihu.com/p/27678951
https://hijiangtao.github.io/2020/01/13/RxJS-Introduction-and-Actions/
https://www.jianshu.com/p/c95e29854cb1
https://segmentfault.com/a/1190000020193566
https://alleniverson.gitbooks.io/rxjava-docs-for-android-developer/content/RxJava%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B/13.Android%20%E4%B8%93%E7%94%A8%E5%93%8D%E5%BA%94%E5%BC%8F%E7%BC%96%E7%A8%8B%E6%A1%86%E6%9E%B6%20%E2%80%94%20Agera.html
https://www.cnblogs.com/littleatp/p/11386487.html
https://www.jianshu.com/p/1765f658200a
http://ifeve.com/reactive%EF%BC%88%E5%93%8D%E5%BA%94%E5%BC%8F%EF%BC%89%E7%BC%96%E7%A8%8B-reactor/
https://zh.wikipedia.org/wiki/%E5%93%8D%E5%BA%94%E5%BC%8F%E7%BC%96%E7%A8%8B
https://www.jianshu.com/p/4894c708e467



---------------------------------------------------------------------------------------------------------------------
## 命令式编程和声明式编程

RP、FP、FRP区别，反应式编程、函数式编程、函数反应式编程



面向对象编程 Object Oriented Programming
响应式编程 Reactive Programming
函数式编程 Functional Programming
函数响应式编程 Functional Reactive Programming



近年来逐渐火爆的functional programming以其提倡的:
    函数是编程语言的一等公民(function as first-class citizen)
    不可变量(immutable variable)
    无副作用的函数(no side-effect/reference transparency)
    可组合的函数(composable functions)

总结一下函数式编程具有以下几个特点：
1、函数是"第一等公民"
2、闭包和高阶函数
3、不改变状态(由此延伸出”引用透明”的概念)
4、递归
5、只用"表达式"，不用"语句"，没有副作用



函数式编程：

1、一个纯函数在执行的过程中，只跟入参有关，在函数体中并不会引用外部全局变量，或者说是一个类方法里面的其他成员变量。
2、另外，纯函数除了返回值之外，也不会去改变外部的变量值。

满足上面这两点的纯函数，就可以说它是引用透明的。也有说法叫这种特性为幂等性


四.递归
函数式编程是用递归做为控制流程的机制。


五.只用"表达式"，不用"语句"，没有副作用
"表达式"（expression）是一个单纯的运算过程，总是有返回值；"语句"（statement）是执行某种操作，没有返回值。函数式编程要求，只使用表达式，不使用语句。也就是说，每一步都是单纯的运算，而且都有返回值。
原因是函数式编程的开发动机，一开始就是为了处理运算（computation），不考虑系统的读写（I/O）。"语句"属于读写操作，所以就被排斥在外。
函数式编程强调没有"副作用"，意味着函数要保持独立，所有功能就是返回一个新的值，没有其他行为，尤其是不得修改外部变量的值。
在函数式编程里面是基本上没有状态量，只有表达式，也没有赋值语句。利用了递归解决了问题。




面试题: 函数式语言主张不变量的原因是什么？

1、函数保持独立，所有功能就是返回一个新的值，没有其他行为，尤其是不修改外部变量的值。由于这一主张，我们不需要考虑线程"死锁"问题，线程之间一定是安全的，因为它不修改变量，所以根本不存在"锁"线程的问题。
2、进一步，函数式语言更加趋向于数学公式的推导，在数学公式里面其实是完全不存在变量这一概念的，此时如果又不存在变量了，那整个程序的执行顺序其实就不必要了，这样可以使我们更加容易的进行并发编程，更加有效率的利用多核cpu的计算处理能力。




链式调用：


面试题: 组成链式调用的必要条件就是在方法里面返回对象自己

这个说法是错误，举个例子：RAC每次做信号变换的时候，都产生了一个新的信号，所以返回自己就并不是必要条件。
其实如果返回自己的同类或者和自己类似的类型，里面也包含可以继续链式调用的方法，也是可以组成链式调用的。



参考
https://halfrost.com/functional_reactive_programming_concept/
https://insights.thoughtworks.cn/lets-talk-about-reactive/


---------------------------------------------------------------------------------------------------------------------







