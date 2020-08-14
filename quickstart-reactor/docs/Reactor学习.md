1、
2、

---------------------------------------------------------------------------------------------------------------------

http://projectreactor.io
http://www.reactive-streams.org/
https://github.com/reactor/reactor-core
https://github.com/reactor/reactor-netty

Reactor 是一个运行在 Java8 之上的响应式流框架，它提供了一组响应式风格的 API


Flux 和 Mono
Flux 和 Mono 是 Reactor 中的两个基本概念。

Flux 表示的是包含 0 到 N 个元素的异步序列。
在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息。
当消息通知产生时，订阅者中对应的方法 onNext(), onComplete()和 onError()会被调用。

既然是“数据流”的发布者，Flux和Mono都可以发出三种“数据信号”：元素值、错误信号、完成信号，
错误信号和完成信号都是终止信号，完成信号用于告知下游订阅者该数据流正常结束，错误信号终止数据流的同时将错误传递给下游订阅者。

不过，这三种信号都不是一定要具备的：
    首先，错误信号和完成信号都是终止信号，二者不可能同时共存；
    如果没有发出任何一个元素值，而是直接发出完成/错误信号，表示这是一个空数据流；
    如果没有错误信号和完成信号，那么就是一个无限数据流。


Mono 表示的是包含 0 或者 1 个元素的异步序列。该序列中同样可以包含与 Flux 相同的三种类型的消息通知。

Flux 和 Mono 之间可以进行转换。
对一个 Flux 序列进行计数操作，得到的结果是一个 Mono<Long>对象。
把两个 Mono 序列合并在一起，得到的是一个 Flux 对象。


Reactor中的发布者（Publisher）由Flux和Mono两个类定义，它们都提供了丰富的操作符（operator）。
一个Flux对象代表一个包含0..N个元素的响应式序列，
而一个Mono对象代表一个包含零/一个（0..1）元素的结果。





示例参考
https://www.infoq.cn/article/reactor-by-example
https://www.infoq.com/articles/reactor-by-example/
https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html
https://zhuanlan.zhihu.com/p/55974997


Mono使用
https://blog.csdn.net/songhaifengshuaige/article/details/79248343


Reactor详解
https://my.oschina.net/u/1000241/blog/3125451


Reactor Netty Reference Guide
https://projectreactor.io/docs/netty/release/reference/index.html


---------------------------------------------------------------------------------------------------------------------


TCP/HTTP/UDP client/server with Reactor over Netty http://projectreactor.io
https://github.com/reactor/reactor-netty


---------------------------------------------------------------------------------------------------------------------