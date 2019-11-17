http://www.reactive-streams.org/
https://github.com/reactive-streams/reactive-streams-jvm

Reactive Streams是一项倡议，旨在为具有无阻塞背压的异步流处理提供标准。这包括针对运行时环境（JVM和JavaScript）以及网络协议的工作。

JDK9的java.util.concurrent.Flow中可用的接口在语义上等效于它们各自的Reactive Streams对应的1：1。这意味着将有一个迁移期，而库将在JDK中采用新类型，但是由于库的完全语义等效以及反应性流<->，预计该期将很短。流适配器库以及与JDK Flow类型直接兼容的TCK。

