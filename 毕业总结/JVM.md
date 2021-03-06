---
createTime: 2021/4/27 23:14:36
updateTime: 2021/5/2322:58:26
timestamp: 1621781906396
---

# JVM入门与进阶

[[toc]]

## 简介JVM
一个基于栈的Java虚拟机</br>
JVM是一个任何参数都提供了默认值的虚拟机

## java的核心优势
1. 生态完善
2. 非常好的跨平台语言
3. 有自己的虚拟机和垃圾回收器（能很好的管理内存）

## Java是如何运行的（简化）
.java文件编译生成.class文件，也就是`Java字节码文件`，java运行时将字节码文件通过`类加载器`加载到jvm中并在内存中生成对象实例。

## JVM参数的设置

* 为什么建议将Xmx和Xms设置成一样的？
* 如果各个区参数未设置好，存在动态扩容问题是个什么场景？
* MinorGC和MajorGC跟youngGC和FullGC的区别？

**内存分配参数：**
* Xms
* Xmx
* Xmn
* PermSize/MetaspaceSize
* MaxPermSize/MetaspaceSize
* Xss

**GC策略参数：**
* SurvivorRatio
* PretenureSzieThreshold
* MaxTenuringThreshold
* ParallelRefProcEnabled
* ParallelGCThreads
* UseSerialGC
* UseParNewGC
* CMSParallelRemarkEnabled
* UseParallelGC
* UseAdaptiveSizePolicy
* MaxTimeRatio
* MaxGCPauseMillis（常和G1配合使用）
* UseParallelOldGC
* UseConcMarkSweepGC
* CMSInitiatingOccupancyFraction
* UseCMSInitiatingOccupancyOnly
* UseCMSCompactAtFullCollection
* CMSFullGCsBeforeCompaction
* CMSClassUnloadingEnabled
* UseG1GC
* DisableExplicitGC

G1配置参数：
* G1NewSizePercent
* G1MaxNewSziePercent
* G1HeapRegionSize
* ConcGCThreads
* InitiatingHeapOccupancyPercent
* G1HeapWastePercent
* G1MixedGCCountTarget
* G1PrintRegionLivenessInfo
* G1ReservePercent
* G1SummarizeRSetStats
* G1TraceConcRefinement
* GCTimeRatio

**GC日志参数：**
* Xloggc
* UseGCLogFileRotation
* NumberOfGCLogFiles
* GCLogFileSize
* PrintGCDetails
* PrintGCDateStamps
* PrintTenuringDistribution
* PrintGCApplicationStoppedTime
* PrintHeapAtGC

**异常参数：**
* HeapDumpOnOutOfMemoryError(-XX:+HeapDumpOnOutOfMemoryError)
* HeapDumpPath(-XX:HeapDumpPath=/data/dump/jvm.dump)

**其他：**
* server(-server)
* TieredCompilation(-XX:+TieredCompilation)

[jvm参数设置参考](https://www.cnblogs.com/jmcui/p/12051328.html)



## JVM内存分布

![jdk6内存划分](jdk6内存划分.png)



## 垃圾收集算法

### 如何判断对象需要回收

### 分代收集理论

### 标记-清除

### 标记-复制

### 标记-整理



## HotSpot的垃圾收集器（GC）



以下是7种HotSpot GC的搭配关系图：



有不需要STW的垃圾收集器吗？



通常用哪些指标衡量垃圾收集器？



### Serial收集器

工作机制是什么（什么算法实现、和其他收集器的组合有什么）？是指单线程吗？单线程是垃圾收集线程只有一个吗？

现在还有使用它的场景吗？

觉得它基本没有使用的场景，那你说下它的优缺点吧。



### ParNew收集器

工作机制？什么算法实现？

什么时候使用？

与Serial收集器相比如何？



### Parallel Scavenge收集器

工作机制及算法实现是什么？

与ParNew的区别是什么？

为什么它常被称作“吞吐量优先收集器”？

使用它常设置哪些JVM参数？





### Serial Old收集器

工作机制和内容分别是什么？



### Parallel Old收集器

工作机制和内容分别是什么？

怎么组合使用？



### CMS收集器

工作机制是什么？（有四个步骤，你知道是哪些吗？）

什么时候会触发STW？什么时候不需要STW？

它有哪些优缺点？

JDK9已经不推荐使用CMS收集器了。





### G1收集器

JDK9发布之后，G1取代Parallel Scavenge+Parallel Old组合，成为服务端模式下默认收集器。

工作机制是什么？



### Shenandoah收集器



### ZGC收集器



### Epsilon收集器





## 解析GC日志

所有对象的创建，生命周期中的使用，到最后销毁的整个过程，都是由GC模块管理的，真正应该称之为`内存管理器`。



打印GC日志：
`java -XX:+PrintGCDetails xxxclass` 执行时，会打印gc的详细日志

`-XX:+PrintGCDateStamps` 日志会打印时间戳

`-Xloggc:gc.demo.log` 将gc日志输出到指定文件gc.demo.log

下面是产生YoungGC时的日志示例：
```log
jesse$ java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xmx2g -Xms2g javaclass
2021-05-15T15:57:00.829+0800: [GC (Allocation Failure) [PSYoungGen: 524800K->87022K(611840K)] 524800K->153159K(2010112K), 0.0405788 secs] [Times: user=0.19 sys=0.12, real=0.04 secs] 
2021-05-15T15:57:00.971+0800: [GC (Allocation Failure) [PSYoungGen: 611822K->87024K(611840K)] 677959K->266402K(2010112K), 0.0534516 secs] [Times: user=0.23 sys=0.20, real=0.05 secs] 
2021-05-15T15:57:01.120+0800: [GC (Allocation Failure) [PSYoungGen: 611495K->87035K(611840K)] 790873K->382782K(2010112K), 0.0576040 secs] [Times: user=0.36 sys=0.09, real=0.06 secs] 
2021-05-15T15:57:01.270+0800: [GC (Allocation Failure) [PSYoungGen: 611835K->87027K(611840K)] 907582K->497519K(2010112K), 0.0584238 secs] [Times: user=0.36 sys=0.11, real=0.06 secs] 
2021-05-15T15:57:01.421+0800: [GC (Allocation Failure) [PSYoungGen: 611827K->87029K(611840K)] 1022319K->621339K(2010112K), 0.0617433 secs] [Times: user=0.34 sys=0.15, real=0.06 secs] 
2021-05-15T15:57:01.577+0800: [GC (Allocation Failure) [PSYoungGen: 611711K->87035K(321024K)] 1146020K->733392K(1719296K), 0.0685264 secs] [Times: user=0.31 sys=0.19, real=0.08 secs] 
执行结束!共生成对象次数:11859
Heap
 PSYoungGen      total 321024K, used 96528K [0x00000000d5580000, 0x0000000100000000, 0x0000000100000000)
  eden space 233984K, 4% used [0x00000000d5580000,0x00000000d5ec53c8,0x00000000e3a00000)
  from space 87040K, 99% used [0x00000000fab00000,0x00000000ffffeea0,0x0000000100000000)
  to   space 232448K, 0% used [0x00000000e3a00000,0x00000000e3a00000,0x00000000f1d00000)
 ParOldGen       total 1398272K, used 646357K [0x0000000080000000, 0x00000000d5580000, 0x00000000d5580000)
  object space 1398272K, 46% used [0x0000000080000000,0x00000000a7735530,0x00000000d5580000)
 Metaspace       used 2546K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 273K, capacity 386K, committed 512K, reserved 1048576K
```

接下来我们分析下GC日志具体有什么内容：先看日志第一行
1. `2021-05-15T15:57:00.829+0800`是`-XX:+PrintGCDateStamps`加上后打印出来的时间戳
2. `GC (Allocation Failure)`表示gc执行的原因，此处意为分配失败。
3. `PSYoungGen: 524800K->87022K(611840K)`表示young区占用的内存（对象）从524800K压缩到了87022K，括号里的611840K代表整个young区的最大容量
4. `524800K->153159K(2010112K)`后面这段表示整个堆内存的内存占用，这里与上面young区差的就是old区。
5. `0.0405788 secs`表示YoungGC的时间
6. `[Times: user=0.19 sys=0.12, real=0.04 secs]`表示youngGC的用户耗时、系统耗时、实际耗时。

*可以看出第一次垃圾回收时young区和整个堆内存的内存占用是一致的，这是因为最开始还没有对象晋升到old区。*

图文示例：

![youngGC日志释义](youngGC日志释义.png)



下面是FullGC日志：

```log
2021-05-17T23:01:02.587+0800: [Full GC (Ergonomics) [PSYoungGen: 17594K->0K(113664K)] [ParOldGen: 308012K->265261K(341504K)] 325607K->265261K(455168K), [Metaspace: 2540K->2540K(1056768K)], 0.0694247 secs] [Times: user=0.52 sys=0.01, real=0.06 secs] 
2021-05-17T23:01:02.668+0800: [GC (Allocation Failure) [PSYoungGen: 56832K->17484K(113664K)] 322093K->282745K(455168K), 0.0064969 secs] [Times: user=0.05 sys=0.00, real=0.00 secs] 
2021-05-17T23:01:02.685+0800: [GC (Allocation Failure) [PSYoungGen: 74213K->18540K(113664K)] 339474K->300211K(455168K), 0.0120198 secs] [Times: user=0.09 sys=0.00, real=0.01 secs] 
2021-05-17T23:01:02.706+0800: [GC (Allocation Failure) [PSYoungGen: 75341K->25265K(113664K)] 357011K->323768K(455168K), 0.0140829 secs] [Times: user=0.11 sys=0.00, real=0.01 secs] 
2021-05-17T23:01:02.721+0800: [Full GC (Ergonomics) [PSYoungGen: 25265K->0K(113664K)] [ParOldGen: 298502K->282852K(341504K)] 323768K->282852K(455168K), [Metaspace: 2540K->2540K(1056768K)], 0.0725073 secs] [Times: user=0.56 sys=0.00, real=0.07 secs]
```

分析FullGC日志：第一行

1. `Full GC (Ergonomics)`代表执行的是FullGC，`Ergonomics`的中文意思是人体工程学，也就是根据算法估算下次可能会无法分配内存，*提前执行一次FullGC*。

出现FullGC的常见原因：

* Full GC (Allocation Failure)

* Full GC (Ergonomics)

* Full GC (Metadata GC Threshold)

> 在JVM中的垃圾收集器中的Ergonomics就是负责自动的调解gc暂停时间和吞吐量之间的平衡，然后你的虚拟机性能更好的一种做法。

2. `[PSYoungGen: 17594K->0K(113664K)]`young区在FullGC时会清空掉。
3. `[ParOldGen: 308012K->265261K(341504K)]`old区在FullGC时会进行压缩。
4. `[Metaspace: 2540K->2540K(1056768K)]`元数据区执行FullGC时没有变化，jdk7是PSPermGen（永久代），jdk8更改为meta区（元空间）。
5. `0.0694247 secs`代表FullGC的耗时
6. `[Times: user=0.52 sys=0.01, real=0.06 secs]`与youngGC一样。



图文示例：

![FullGC日志详解](FullGC日志详解.png)



**上面是JDK8默认使用的垃圾收集器`并行GC`，下面指定`串行GC`执行同样程序：**

```log
jesse$ java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseSerialGC  -Xmx100m -Xms100m GCLogAnalysis

2021-05-18T23:47:27.922+0800: [GC (Allocation Failure) 2021-05-18T23:47:27.922+0800: [DefNew: 26896K->3392K(30720K), 0.0044572 secs] 26896K->8430K(99008K), 0.0045628 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-05-18T23:47:27.936+0800: [GC (Allocation Failure) 2021-05-18T23:47:27.936+0800: [DefNew: 30443K->3391K(30720K), 0.0076210 secs] 35482K->17402K(99008K), 0.0076747 secs] [Times: user=0.00 sys=0.01, real=0.01 secs] 
2021-05-18T23:47:27.957+0800: [GC (Allocation Failure) 2021-05-18T23:47:27.957+0800: [DefNew: 30719K->3372K(30720K), 0.0048223 secs] 44730K->25250K(99008K), 0.0048821 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-05-18T23:47:27.970+0800: [GC (Allocation Failure) 2021-05-18T23:47:27.970+0800: [DefNew: 30700K->3381K(30720K), 0.0050724 secs] 52578K->33409K(99008K), 0.0051447 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
2021-05-18T23:47:27.982+0800: [GC (Allocation Failure) 2021-05-18T23:47:27.982+0800: [DefNew: 30709K->3389K(30720K), 0.0073571 secs] 60737K->44768K(99008K), 0.0074114 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-05-18T23:47:27.996+0800: [GC (Allocation Failure) 2021-05-18T23:47:27.996+0800: [DefNew: 30453K->3391K(30720K), 0.0061754 secs] 71832K->53531K(99008K), 0.0062303 secs] [Times: user=0.00 sys=0.01, real=0.01 secs] 
2021-05-18T23:47:28.009+0800: [GC (Allocation Failure) 2021-05-18T23:47:28.009+0800: [DefNew: 30550K->3389K(30720K), 0.0062526 secs] 80690K->62820K(99008K), 0.0063055 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-18T23:47:28.020+0800: [GC (Allocation Failure) 2021-05-18T23:47:28.020+0800: [DefNew: 30622K->30622K(30720K), 0.0000235 secs]2021-05-18T23:47:28.020+0800: [Tenured: 59431K->68237K(68288K), 0.0158607 secs] 90053K->71211K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0159623 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-05-18T23:47:28.042+0800: [Full GC (Allocation Failure) 2021-05-18T23:47:28.042+0800: [Tenured: 68237K->68246K(68288K), 0.0152800 secs] 98730K->79069K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0153404 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2021-05-18T23:47:28.061+0800: [Full GC (Allocation Failure) 2021-05-18T23:47:28.061+0800: [Tenured: 68246K->67959K(68288K), 0.0149245 secs] 98878K->84519K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0149797 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
```
可以看出`UseSerialGC`在young区使用了`DefNew`垃圾收集器，`Tenured`则是代表old区，并且在某一次非FullGC时，同时执行了young区和old区的垃圾回收。其他与并行GC区别不大。

**下面指定CMS GC：**
```log
jesse$ java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC  -Xmx100m -Xms100m GCLogAnalysis

2021-05-19T00:11:15.132+0800: [GC (Allocation Failure) 2021-05-19T00:11:15.132+0800: [ParNew: 27229K->3391K(30720K), 0.0037007 secs] 27229K->10610K(99008K), 0.0037781 secs] [Times: user=0.01 sys=0.02, real=0.00 secs] 
-------此处发生多次GC (Allocation Failure)-------
2021-05-19T00:11:15.192+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 41471K(68288K)] 45517K(99008K), 0.0002588 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.193+0800: [CMS-concurrent-mark-start]
2021-05-19T00:11:15.193+0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.193+0800: [CMS-concurrent-preclean-start]
2021-05-19T00:11:15.194+0800: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.194+0800: [CMS-concurrent-abortable-preclean-start]
2021-05-19T00:11:15.198+0800: [GC (Allocation Failure) 2021-05-19T00:11:15.198+0800: [ParNew: 30667K->3376K(30720K), 0.0064299 secs] 72138K->54772K(99008K), 0.0064830 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
2021-05-19T00:11:15.210+0800: [GC (Allocation Failure) 2021-05-19T00:11:15.210+0800: [ParNew: 30704K->3391K(30720K), 0.0049883 secs] 82100K->62027K(99008K), 0.0050630 secs] [Times: user=0.04 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.221+0800: [GC (Allocation Failure) 2021-05-19T00:11:15.221+0800: [ParNew: 30708K->30708K(30720K), 0.0000506 secs]2021-05-19T00:11:15.221+0800: [CMS2021-05-19T00:11:15.221+0800: [CMS-concurrent-abortable-preclean: 0.001/0.028 secs] [Times: user=0.10 sys=0.00, real=0.03 secs] 
 (concurrent mode failure): 58635K->65869K(68288K), 0.0192665 secs] 89344K->65869K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0194171 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-05-19T00:11:15.247+0800: [GC (Allocation Failure) 2021-05-19T00:11:15.247+0800: [ParNew: 27072K->27072K(30720K), 0.0000383 secs]2021-05-19T00:11:15.247+0800: [CMS: 65869K->68067K(68288K), 0.0169597 secs] 92942K->72852K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0171223 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-05-19T00:11:15.264+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 68067K(68288K)] 73863K(99008K), 0.0004737 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.264+0800: [CMS-concurrent-mark-start]
2021-05-19T00:11:15.265+0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.265+0800: [CMS-concurrent-preclean-start]
2021-05-19T00:11:15.266+0800: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.266+0800: [CMS-concurrent-abortable-preclean-start]
2021-05-19T00:11:15.266+0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.266+0800: [GC (CMS Final Remark) [YG occupancy: 9697 K (30720 K)]2021-05-19T00:11:15.266+0800: [Rescan (parallel) , 0.0002076 secs]2021-05-19T00:11:15.266+0800: [weak refs processing, 0.0000140 secs]2021-05-19T00:11:15.266+0800: [class unloading, 0.0002604 secs]2021-05-19T00:11:15.266+0800: [scrub symbol table, 0.0003168 secs]2021-05-19T00:11:15.267+0800: [scrub string table, 0.0002255 secs][1 CMS-remark: 68067K(68288K)] 77765K(99008K), 0.0010911 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-05-19T00:11:15.267+0800: [CMS-concurrent-sweep-start]
2021-05-19T00:11:15.267+0800: [CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.267+0800: [CMS-concurrent-reset-start]
2021-05-19T00:11:15.267+0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.271+0800: [GC (Allocation Failure) 2021-05-19T00:11:15.271+0800: [ParNew: 30694K->30694K(30720K), 0.0001260 secs]2021-05-19T00:11:15.271+0800: [CMS: 68067K->68203K(68288K), 0.0138106 secs] 98761K->81654K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0140288 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]

----------------此处省略多行类似GC日志----------------

2021-05-19T00:11:15.337+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 68117K(68288K)] 92674K(99008K), 0.0006324 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.338+0800: [CMS-concurrent-mark-start]
2021-05-19T00:11:15.338+0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.338+0800: [CMS-concurrent-preclean-start]
2021-05-19T00:11:15.339+0800: [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-05-19T00:11:15.339+0800: [CMS-concurrent-abortable-preclean-start]
2021-05-19T00:11:15.339+0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.339+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.339+0800: [CMS (concurrent mode failure): 68117K->68261K(68288K), 0.0082180 secs] 98833K->92954K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0082750 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-05-19T00:11:15.349+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.349+0800: [CMS: 68261K->68227K(68288K), 0.0194007 secs] 98965K->94020K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0194681 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-05-19T00:11:15.368+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 68227K(68288K)] 94301K(99008K), 0.0003624 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.369+0800: [CMS-concurrent-mark-start]
2021-05-19T00:11:15.369+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.369+0800: [CMS2021-05-19T00:11:15.371+0800: [CMS-concurrent-mark: 0.001/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
 (concurrent mode failure): 68227K->68281K(68288K), 0.0221579 secs] 98721K->95980K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0222169 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-05-19T00:11:15.392+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.392+0800: [CMS: 68281K->68213K(68288K), 0.0216581 secs] 98827K->96411K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0217184 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
2021-05-19T00:11:15.414+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 68213K(68288K)] 96995K(99008K), 0.0004368 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.415+0800: [CMS-concurrent-mark-start]
2021-05-19T00:11:15.415+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.415+0800: [CMS2021-05-19T00:11:15.417+0800: [CMS-concurrent-mark: 0.001/0.002 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
 (concurrent mode failure): 68213K->68198K(68288K), 0.0213393 secs] 98855K->96402K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0214075 secs] [Times: user=0.03 sys=0.00, real=0.02 secs] 
2021-05-19T00:11:15.437+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.437+0800: [CMS: 68198K->68143K(68288K), 0.0054181 secs] 98795K->97051K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0055051 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-05-19T00:11:15.443+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 68143K(68288K)] 97426K(99008K), 0.0007019 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.444+0800: [CMS-concurrent-mark-start]
2021-05-19T00:11:15.444+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.444+0800: [CMS2021-05-19T00:11:15.446+0800: [CMS-concurrent-mark: 0.001/0.002 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
 (concurrent mode failure): 68143K->68143K(68288K), 0.0032796 secs] 98614K->97544K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0033336 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
2021-05-19T00:11:15.448+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.448+0800: [CMS: 68143K->68143K(68288K), 0.0013556 secs] 98839K->98223K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0014120 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.449+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.450+0800: [CMS: 68143K->68143K(68288K), 0.0011270 secs] 98546K->98546K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0011929 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.451+0800: [Full GC (Allocation Failure) 2021-05-19T00:11:15.451+0800: [CMS: 68143K->68125K(68288K), 0.0152224 secs] 98546K->98527K(99008K), [Metaspace: 2540K->2540K(1056768K)], 0.0152626 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2021-05-19T00:11:15.466+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 68125K(68288K)] 98527K(99008K), 0.0002910 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-05-19T00:11:15.467+0800: [CMS-concurrent-mark-start]
Exception in thread "main" 2021-05-19T00:11:15.467+0800: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-05-19T00:11:15.467+0800: [CMS-concurrent-preclean-start]
java.lang.OutOfMemoryError: Java heap space
	at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:42)
	at GCLogAnalysis.main(GCLogAnalysis.java:25)

```

可以看出CMS GC在young区使用的是`ParNew`垃圾收集器，在老年代就是使用CMS垃圾收集器了，可以发现CMS垃圾收集会执行很多步骤。

**下面执行G1 GC：**

```log
jesse$ java -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+UseG1GC  -Xmx100m -Xms100m GCLogAnalysis

2021-05-19T00:45:29.601+0800: [GC pause (G1 Evacuation Pause) (young) 29M->10093K(100M), 0.0032925 secs]
2021-05-19T00:45:29.621+0800: [GC pause (G1 Evacuation Pause) (young) 45M->24M(100M), 0.0048468 secs]
2021-05-19T00:45:29.642+0800: [GC pause (G1 Evacuation Pause) (young)-- 75M->49M(100M), 0.0049933 secs]
2021-05-19T00:45:29.647+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 50M->48M(100M), 0.0021580 secs]
2021-05-19T00:45:29.650+0800: [GC concurrent-root-region-scan-start]
2021-05-19T00:45:29.650+0800: [GC concurrent-root-region-scan-end, 0.0000263 secs]
2021-05-19T00:45:29.650+0800: [GC concurrent-mark-start]
2021-05-19T00:45:29.651+0800: [GC concurrent-mark-end, 0.0012330 secs]
2021-05-19T00:45:29.651+0800: [GC remark, 0.0010260 secs]
2021-05-19T00:45:29.652+0800: [GC cleanup 54M->54M(100M), 0.0003197 secs]
2021-05-19T00:45:29.658+0800: [GC pause (G1 Evacuation Pause) (young)-- 82M->69M(100M), 0.0033098 secs]
2021-05-19T00:45:29.661+0800: [GC pause (G1 Evacuation Pause) (mixed) 70M->63M(100M), 0.0017333 secs]
2021-05-19T00:45:29.665+0800: [GC pause (G1 Evacuation Pause) (young) 75M->67M(100M), 0.0018694 secs]
2021-05-19T00:45:29.667+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 68M->67M(100M), 0.0012085 secs]
2021-05-19T00:45:29.669+0800: [GC concurrent-root-region-scan-start]
2021-05-19T00:45:29.669+0800: [GC concurrent-root-region-scan-end, 0.0001312 secs]
2021-05-19T00:45:29.669+0800: [GC concurrent-mark-start]
2021-05-19T00:45:29.670+0800: [GC concurrent-mark-end, 0.0009429 secs]
2021-05-19T00:45:29.670+0800: [GC remark, 0.0010988 secs]
2021-05-19T00:45:29.671+0800: [GC cleanup 75M->74M(100M), 0.0003737 secs]
2021-05-19T00:45:29.672+0800: [GC concurrent-cleanup-start]
2021-05-19T00:45:29.672+0800: [GC concurrent-cleanup-end, 0.0000206 secs]
2021-05-19T00:45:29.673+0800: [GC pause (G1 Evacuation Pause) (young) 82M->70M(100M), 0.0015186 secs]
2021-05-19T00:45:29.675+0800: [GC pause (G1 Evacuation Pause) (mixed) 78M->64M(100M), 0.0022592 secs]
2021-05-19T00:45:29.679+0800: [GC pause (G1 Evacuation Pause) (mixed) 70M->65M(100M), 0.0014884 secs]
2021-05-19T00:45:29.680+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 66M->66M(100M), 0.0006846 secs]
2021-05-19T00:45:29.681+0800: [GC concurrent-root-region-scan-start]
2021-05-19T00:45:29.681+0800: [GC concurrent-root-region-scan-end, 0.0000456 secs]
2021-05-19T00:45:29.681+0800: [GC concurrent-mark-start]
2021-05-19T00:45:29.682+0800: [GC concurrent-mark-end, 0.0008500 secs]
2021-05-19T00:45:29.682+0800: [GC remark, 0.0010961 secs]
2021-05-19T00:45:29.683+0800: [GC cleanup 72M->72M(100M), 0.0003134 secs]
2021-05-19T00:45:29.684+0800: [GC pause (G1 Evacuation Pause) (young) 75M->68M(100M), 0.0017291 secs]
2021-05-19T00:45:29.687+0800: [GC pause (G1 Evacuation Pause) (mixed) 77M->70M(100M), 0.0025035 secs]
2021-05-19T00:45:29.691+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 73M->71M(100M), 0.0014056 secs]
2021-05-19T00:45:29.692+0800: [GC concurrent-root-region-scan-start]
2021-05-19T00:45:29.692+0800: [GC concurrent-root-region-scan-end, 0.0001464 secs]
2021-05-19T00:45:29.692+0800: [GC concurrent-mark-start]
2021-05-19T00:45:29.693+0800: [GC concurrent-mark-end, 0.0009321 secs]
2021-05-19T00:45:29.693+0800: [GC remark, 0.0011006 secs]
2021-05-19T00:45:29.694+0800: [GC cleanup 78M->78M(100M), 0.0003301 secs]
2021-05-19T00:45:29.695+0800: [GC pause (G1 Evacuation Pause) (young) 79M->74M(100M), 0.0013411 secs]
2021-05-19T00:45:29.698+0800: [GC pause (G1 Evacuation Pause) (mixed) 81M->73M(100M), 0.0018896 secs]
2021-05-19T00:45:29.700+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 74M->73M(100M), 0.0007626 secs]
2021-05-19T00:45:29.700+0800: [GC concurrent-root-region-scan-start]
2021-05-19T00:45:29.700+0800: [GC concurrent-root-region-scan-end, 0.0000164 secs]
2021-05-19T00:45:29.700+0800: [GC concurrent-mark-start]
2021-05-19T00:45:29.702+0800: [GC concurrent-mark-end, 0.0010740 secs]
2021-05-19T00:45:29.702+0800: [GC remark, 0.0012480 secs]
2021-05-19T00:45:29.703+0800: [GC cleanup 82M->82M(100M), 0.0004452 secs]
2021-05-19T00:45:29.704+0800: [GC pause (G1 Evacuation Pause) (young)-- 83M->82M(100M), 0.0013198 secs]
2021-05-19T00:45:29.706+0800: [GC pause (G1 Evacuation Pause) (mixed)-- 85M->85M(100M), 0.0007048 secs]
2021-05-19T00:45:29.706+0800: [Full GC (Allocation Failure)  85M->72M(100M), 0.0134278 secs]
2021-05-19T00:45:29.720+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 74M->73M(100M), 0.0019245 secs]
2021-05-19T00:45:29.722+0800: [GC concurrent-root-region-scan-start]
2021-05-19T00:45:29.722+0800: [GC concurrent-root-region-scan-end, 0.0000880 secs]
2021-05-19T00:45:29.722+0800: [GC concurrent-mark-start]
2021-05-19T00:45:29.723+0800: [GC pause (G1 Humongous Allocation) (young)-- 77M->75M(100M), 0.0013619 secs]
2021-05-19T00:45:29.725+0800: [GC pause (G1 Evacuation Pause) (young) 77M->75M(100M), 0.0008103 secs]
2021-05-19T00:45:29.726+0800: [GC concurrent-mark-end, 0.0033717 secs]
2021-05-19T00:45:29.726+0800: [GC remark, 0.0012500 secs]
2021-05-19T00:45:29.727+0800: [GC cleanup 77M->77M(100M), 0.0004065 secs]
2021-05-19T00:45:29.728+0800: [GC pause (G1 Evacuation Pause) (young)-- 77M->76M(100M), 0.0008329 secs]
2021-05-19T00:45:29.729+0800: [GC pause (G1 Humongous Allocation) (young)-- 77M->77M(100M), 0.0010191 secs]
2021-05-19T00:45:29.730+0800: [Full GC (Allocation Failure)  77M->74M(100M), 0.0031904 secs]
2021-05-19T00:45:29.733+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 76M->75M(100M), 0.0012407 secs]
2021-05-19T00:45:29.735+0800: [GC concurrent-root-region-scan-start]
2021-05-19T00:45:29.735+0800: [GC concurrent-root-region-scan-end, 0.0000535 secs]
2021-05-19T00:45:29.735+0800: [GC concurrent-mark-start]
2021-05-19T00:45:29.735+0800: [GC pause (G1 Evacuation Pause) (young)-- 76M->76M(100M), 0.0010960 secs]
2021-05-19T00:45:29.736+0800: [Full GC (Allocation Failure)  76M->76M(100M), 0.0023247 secs]
2021-05-19T00:45:29.739+0800: [GC concurrent-mark-abort]
2021-05-19T00:45:29.739+0800: [GC pause (G1 Evacuation Pause) (young)-- 77M->77M(100M), 0.0027059 secs]
2021-05-19T00:45:29.742+0800: [Full GC (Allocation Failure)  77M->75M(100M), 0.0026869 secs]
2021-05-19T00:45:29.745+0800: [GC pause (G1 Evacuation Pause) (young)-- 76M->76M(100M), 0.0010872 secs]
2021-05-19T00:45:29.746+0800: [Full GC (Allocation Failure)  76M->75M(100M), 0.0035007 secs]
2021-05-19T00:45:29.750+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark)-- 76M->76M(100M), 0.0015863 secs]
2021-05-19T00:45:29.751+0800: [GC concurrent-root-region-scan-start]
2021-05-19T00:45:29.751+0800: [GC concurrent-root-region-scan-end, 0.0000255 secs]
2021-05-19T00:45:29.751+0800: [GC concurrent-mark-start]
2021-05-19T00:45:29.751+0800: [GC pause (G1 Humongous Allocation) (young) 76M->76M(100M), 0.0008159 secs]
2021-05-19T00:45:29.752+0800: [Full GC (Allocation Failure)  76M->75M(100M), 0.0023190 secs]
2021-05-19T00:45:29.755+0800: [GC pause (G1 Evacuation Pause) (young) 76M->75M(100M), 0.0010713 secs]
2021-05-19T00:45:29.756+0800: [GC concurrent-mark-abort]
2021-05-19T00:45:29.756+0800: [GC pause (G1 Evacuation Pause) (young)-- 76M->76M(100M), 0.0008249 secs]
2021-05-19T00:45:29.757+0800: [Full GC (Allocation Failure)  76M->76M(100M), 0.0021754 secs]
2021-05-19T00:45:29.760+0800: [GC pause (G1 Evacuation Pause) (young)-- 77M->77M(100M), 0.0011265 secs]
2021-05-19T00:45:29.761+0800: [Full GC (Allocation Failure)  77M->76M(100M), 0.0022905 secs]
2021-05-19T00:45:29.763+0800: [Full GC (Allocation Failure)  76M->76M(100M), 0.0026052 secs]
2021-05-19T00:45:29.766+0800: [GC pause (G1 Evacuation Pause) (young) 76M->76M(100M), 0.0011091 secs]
2021-05-19T00:45:29.767+0800: [GC pause (G1 Evacuation Pause) (young) (initial-mark) 76M->76M(100M), 0.0008196 secs]
2021-05-19T00:45:29.768+0800: [GC concurrent-root-region-scan-start]
2021-05-19T00:45:29.768+0800: [GC concurrent-root-region-scan-end, 0.0000298 secs]
2021-05-19T00:45:29.768+0800: [GC concurrent-mark-start]
2021-05-19T00:45:29.768+0800: [Full GC (Allocation Failure)  76M->250K(100M), 0.0022981 secs]
2021-05-19T00:45:29.771+0800: [GC concurrent-mark-abort]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOfRange(Arrays.java:3664)
	at java.lang.String.<init>(String.java:207)
	at java.lang.StringBuilder.toString(StringBuilder.java:407)
	at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:58)
	at GCLogAnalysis.main(GCLogAnalysis.java:25)
```
同样可以看出G1GC也是有类似于CMSGC一样的标记清除步骤。





## JVM工具

### 查看当前java进程的信息

jhsdb必须在jdk11及以上使用
sudo jhsdb jmap --heap --pid xxx





## 字节码文件（Class文件）

任何一个class文件都对应着唯一的一个类或接口的定义信息。

class文件不需要以磁盘文件的形式存在，可以动态生成，也可以通过网络传输到类加载器种。

Java字节码是由`单字节的指令`组成的，也就是`一个byte（8bit）`组成，所以理论上最多只能有256（2^8）个指令操作码（opcode）。实际上到目前为止，Java规范里只使用了200个左右的操作码，<br/>
字节码文件是十六进制文件，可以使用nodepad++打开即可。











## 指令操作码（opcode）
根据指令的性质，最多分为四个大类：

## 类的生命周期

## 类的加载时机



## 对象内存计算

[参考地址](https://cloud.tencent.com/developer/article/1552089)



## Q&A

### 字节码的偏移量多出来的位置为什么要补00？

### java8默认使用的GC算法是什么？

### 如何切换JVM垃圾收集器？

### 什么时候会发生OOM，一般都是由什么导致的？

### G1 GC的1的意思是什么？有了解过吗？




## 资料
[深入JVM - 实例详解invoke相关操作码](https://github.com/cncounter/translation/blob/master/tiemao_2020/41_invoke_opcode/README.md)