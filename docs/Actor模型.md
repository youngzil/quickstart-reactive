Actors
一个Actor指的是一个最基本的计算单元。它能接收一个消息并且基于其执行计算。

这个理念很像面向对象语言，一个对象接收一条消息（方法调用），然后根据接收的消息做事（调用了哪个方法）。

Actors一大重要特征在于actors之间相互隔离，它们并不互相共享内存。这点区别于上述的对象。也就是说，一个actor能维持一个私有的状态，并且这个状态不可能被另一个actor所改变。


聚沙成塔
One ant is no ant, one actor is no actor.
光有一个actor是不够的，多个actors才能组成系统。在actor模型里每个actor都有地址，所以它们才能够相互发送消息。


Actors有邮箱
只得指明的一点是，尽管许多actors同时运行，但是一个actor只能顺序地处理消息。也就是说其它actors发送了三条消息给一个actor，这个actor只能一次处理一条。所以如果你要并行处理3条消息，你需要把这条消息发给3个actors。

消息异步地传送到actor，所以当actor正在处理消息时，新来的消息应该存储到别的地方。Mailbox就是这些消息存储的地方。






Arun 首先从整体上对 Actor 进行了介绍，他在示例中将 Actor 比作了一群人：
你可以将 Actor 当作是一群人，他们互相之间不会面对面地交流，而只是通过邮件的方式进行沟通。

传递消息是 Actor 模型的基础

一个 Actor 的生命周期并不复杂，首先通过构造函数进行创建，随后调用它的 preStart 方法，
而 Actor 准备开始接收消息时，再调用它的 receive 方法。
最后，调用 postStop 方法将 Actor 置为终结状态。
Arun 还提到，Actor 的生命周期与一个 Java servlet 非常相似，仅存在着一些细微的差异。

Actor 之间的关系是层次结构型的，每个 Actor 都从属于另外一个 Actor。
Arun 将这种结构与文件系统进行了比较，在文件系统中存在着一些顶层文件夹，而当你依照文件夹的层次结构进入内部时，文件夹的总数也在不断上升。
通常来说，父 Actor 创建子 Actor 以处理一些子任务，或者是让子 Actor 在隔离的情况下去处理一些特别容易出错的任务，这样可以保证系统在故障发生时能够恢复状态。
Actor 的容错性的关键部分来自于父 Actor 管理子 Actor 生命周期的能力。

Arun 还描述了一些其它方面的内容，包括日志记录、测试与配置。



介绍Actor模型
http://www.infoq.com/cn/news/2014/11/intro-actor-model
https://github.com/arunma/AkkaMessagingRequestResponse



参考
https://www.jianshu.com/p/449850aa8e82
https://www.jianshu.com/p/db04cab86ab9



---------------------------------------------------------------------------------------------------------------------

Actor 模型(Actor model)首先是由Carl Hewitt在1973定义， 由Erlang OTP(Open Telecom Platform)推广，其消息传递更加符合面向对象的原始意图。Actors属于并发组件模型，通过组件方式定义并发编程范式的高级阶段，避免使用者直接接触多线程并发或线程池等基础概念。

流行语言并发是基于多线程之间的共享内存，使用同步方法防止写争夺，Actors使用消息模型，每个Actors在同一时间处理最多一个消息，可以发送消息给其他Actors，保证了单独写原则。从而巧妙避免了多线程写争夺。


Actor 模型特点
- 隔离计算实体
- Share Nothing
- 没有任何地方同步
- 异步消息传递
- 不可变的消息 消息模型类似 MailBox / Queue



[Actor 编程模型浅谈](https://jiangew.me/actor-model/)



---------------------------------------------------------------------------------------------------------------------







