














---------------------------------------------------------------------------------------------------------------------
## CloudEvents

[CloudEvents官网](https://cloudevents.io/)  
[CloudEvents Specification](https://github.com/cloudevents/spec)  
[CloudEvents - Version 1.0.1](https://github.com/cloudevents/spec/blob/v1.0.1/spec.md)  
[]()  
[Java SDK for CloudEvents网站](https://cloudevents.github.io/sdk-java/)  
[Java SDK for CloudEvents Github](https://github.com/cloudevents/sdk-java)  
[]()  


A specification for describing event data in a common way
以通用方式描述事件数据的规范

Events are everywhere, yet event publishers tend to describe events differently.
事件无处不在，但事件发布者倾向于以不同的方式描述事件。

什么是 CloudEvents
CloudEvents 是一种规范，用于以通用格式描述事件数据，以提供跨服务、平台和系统的交互能力。
事件格式指定了如何使用某些编码格式来序列化 CloudEvent。支持这些编码的兼容 CloudEvents 实现必须遵循在相应的事件格式中指定的编码规则。所有实现都必须支持 JSON 格式。


CloudEvents是一种以通用方式描述事件数据的规范。CloudEvents旨在简化跨服务，平台及其他方面的事件声明和发送。CloudEvents　最初由　CNCF Severless 工作组提出。

A specification for describing event data in a common way
以通用方式描述事件数据的规范






[]()  
[定义无处不在的 Event 事件之 CloudEvents](https://developer.aliyun.com/article/703687)  
[CloudEvents规范](https://skyao.io/learning-serverless/spec/cloudevents/)  
[CloudEvents翻译](https://jimmysong.io/serverless-handbook/core/cloudevents.html)  
[CLOUDEVENTS三部曲:初识篇](https://ustack.io/2020-12-20-CloudEvents%E4%B8%89%E9%83%A8%E6%9B%B2:%E5%88%9D%E8%AF%86%E7%AF%87.html)  
[云原生基石之事件设计规范：CloudEvents](https://zhuanlan.zhihu.com/p/72094231)  
[]()  
[]()  







## OpenFaaS

[OpenFaaS官网](https://www.openfaas.com/)  
[OpenFaaS Github](https://github.com/openfaas/faas)  
[OpenFaaS文档](https://docs.openfaas.com/)  
[OpenFaaS对于CloudEvents事件驱动支持](https://docs.openfaas.com/reference/triggers/#cloudevents)  
[]()  






融合“消息、事件、流”于一体

目前业界有两个发展态势，一个是不可阻挡的云原生改造趋势，另一个是流计算时代的全面兴起。

RocketMQ 与 Kubernetes Operator、Prometheus、Knative、Envoy、Dapr、CloudEvents 等生态项目的整合已经被相关的顶级社区官方收录，可以提供开箱即用的用户体验。






## Hive系列之UDF，UDAF，UDTF

UDF，（User Defined Function）用户自定义函数


UDF：用户定义（普通）函数，只对单行数值产生作用；
UDAF：User- Defined Aggregation Funcation；用户定义聚合函数，可对多行数据产生作用；等同与SQL中常用的SUM()，AVG()，也是聚合函数；
UDTF：User-Defined Table-Generating Functions，用户定义表生成函数，用来解决输入一行输出多行；


Hive中有3种UDF：
- UDF：操作单个数据行，产生单个数据行；
- UDAF：操作多个数据行，产生一个数据行。
- UDTF：操作一个数据行，产生多个数据行一个表作为输出。


[Hive 系列 之 UDF，UDTF，UDAF](https://cloud.tencent.com/developer/article/1486700)  
[udf,udaf,udtf之间的区别](https://blog.csdn.net/wyqwilliam/article/details/84500578)  
[]()  





## OpenSchema

什么是 ADO？
ADO 是一项微软的技术
ADO 指 ActiveX 数据对象（ActiveX Data Objects）
ADO 是一个微软的 Active-X 组件
ADO 会随微软的 IIS 被自动安装
ADO 是一个访问数据库中数据的编程接口


OpenSchema 方法可返回 Recordset 对象，该对象包含有关数据源的模式信息。举例，schema 信息可包括表的名称，表中的列名，每列的数据类型。Recordset 将以只读、静态游标模式打开。



[飞书开放平台openSchema](https://open.feishu.cn/document/uYjL24iN/ukzN4IjL5cDOy4SO3gjM)
[OpenSchema 方法 (ADO)](https://docs.microsoft.com/zh-cn/office/client-developer/access/desktop-database-reference/openschema-method-ado)
[ADO OpenSchema 方法](https://www.w3school.com.cn/ado/met_conn_openschema.asp)

