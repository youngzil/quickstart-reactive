Actor模型和CSP模型


对比 Actor 和 CSP

Akka（Scala）实现了Actor模型
Go语言的CSP模型（Golang CSP并发模型）


首先这两者都是并发模型的解决方案，我们看看Actor和Channel这两个方案的不同：
CSP 中的 Process 和 Actor 很相似. Channel 概念也和 Actor 的 Mailbox 很相似.
Actor之间直接通讯，而CSP是通过Channel通讯，在耦合度上两者是有区别的，后者更加松耦合。



Actor模型
　　在Actor模型中，主角是Actor，类似一种worker，Actor彼此之间直接发送消息，不需要经过什么中介，消息是异步发送和处理的：

Channel模型
　　Channel模型中，worker之间不直接彼此联系，而是通过不同channel进行消息发布和侦听。消息的发送者和接收者之间通过Channel松耦合，发送者不知道自己消息被哪个接收者消费了，接收者也不知道是哪个发送者发送的消息。



理论上, 每个 Actor 有且只有一个 Mailbox, 所以只向 Actor 的 Mail Address (标识符) 发消息. Mailbox 是概念上的而不是实体, 不过具体实现也能把信箱实体独立出来方便使用...
理论上, Channel 和 Process 之间没有从属关系. Process 可以订阅任意个 Channel, Process 没必要拥有标识符, 只有 Channel 需要, 因为只向 Channel 发消息. 不过具体实现也能把 Process 作为一个实体暴露出来...



CSP 是 Communicating Sequential Process 的简称，中文可以叫做通信顺序进程，是一种并发编程模型，是一个很强大的并发数据模型，是上个世纪七十年代提出的，用于描述两个独立的并发实体通过共享的通讯 channel(管道)进行通信的并发模型。相对于Actor模型，CSP中channel是第一类对象，它不关注发送消息的实体，而关注与发送消息时使用的channel。

Golang实现了 CSP 并发模型做为并发基础，底层使用goroutine做为并发实体，goroutine非常轻量级可以创建几十万个实体。实体间通过 channel 继续匿名消息传递使之解耦，在语言层面实现了自动调度，这样屏蔽了很多内部细节，对外提供简单的语法关键字，大大简化了并发编程的思维转换和管理线程的复杂性。

而具体到编程语言，如 Golang，其实只用到了 CSP 的很小一部分，即理论中的 Process/Channel（对应到语言中的 goroutine/channel）：这两个并发原语之间没有从属关系， Process 可以订阅任意个 Channel，Channel 也并不关心是哪个 Process 在利用它进行通信；Process 围绕 Channel 进行读写，形成一套有序阻塞和可预测的并发模型。





粗略的语义描述可以参见 wiki, 但想要深入理解请走正道: 作者 Tony Hoare 写的 http://www.usingcsp.com/cspbook.pdf (呃, 其实我还没看完 ...). 而且作者至今还在更新 CSP 的理论.

CSP
http://www.usingcsp.com/cspbook.pdf

参考 CCS, CSP, LOTOS 的对比:
http://static.aminer.org/pdf/PDF/000/267/454/on_the_relationship_of_ccs_and_csp.pdf



参考
https://www.jdon.com/concurrent/actor-csp.html
https://www.zhihu.com/question/26192499/answer/46815754
https://www.jianshu.com/p/36e246c6153d
https://www.qfgolang.com/?p=2214


