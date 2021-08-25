---
createTime: 2020/12/14 01:12:16
updateTime: 2021/7/4 23:07:12
timestamp: 1625411232210
---
# Java基础

[[toc]]



## 数据类型

Java中一共有两种数据类型：原始数据类型、引用数据类型。

## 原始数据类型

Java是完全面向对象的，但原始类型不是对象，其具有显式范围和数学行为。

byte、short、int、long都是`有符号二进制补码整数`，有范围限制。

float、double是`IEEE 754 浮点数`，不能用于精确计算。

| 类型    | 大小                                                         | 默认值 |
| ------- | ------------------------------------------------------------ | ------ |
| byte    | 1Byte，8bit，[-128,127]                                      | 0      |
| short   | 2Byte，[-32768,32767]                                        | 0      |
| int     | 4Byte，[-2^31,2^31-1] <br />[-2,147,483,648~2,147,483,647]<br />相当于+-21亿多，无符号则翻倍有40亿 | 0      |
| long    | 8Byte，[-2^63,2^63-1]                                        | 0L     |
| char    | 2Byte，Unicode字符（UTF-16）<br />范围：[\u0000,\uffff]<br />\u0000：0；\uffff：65535 | \u0000 |
| float   | 4Byte，单精度                                                | 0.0f   |
| double  | 8Byte，双精度                                                | 0.0d   |
| boolean | 大小无精确定义，依赖具体JVM                                  | false  |

boolean的大小问题：《Java虚拟机规范》给出了参考，有可能是1Byte，或4Byte，关键还要看具体JVM的实现。书中说明，boolean编译后会变为int类型，因此占4字节，而boolean数组编译后是byte数组，因此一个boolean站1字节。占4字节是因为当下CPU一次处理数据以及CPU寻址都是32位，因此4字节具有高效存取的特点。



### 原始数据类型的自动转换（隐式转换）

需要满足以下两个条件才能自动转换：

* 两种类型彼此兼容
* 目标类型的取值范围 大于 源数据类型

数值型的转换：byte -> short -> int -> long -> float -> double

字符型转位整型：char -> int



### 为什么long可以自动转换成float？





### 基本数据类型存放在什么位置？

分为两种情况：

1. 方法中的局部变量

    基本类型存放在方法栈中，周期同方法一致。

2. 类的成员变量

    类是在堆上创建的，类的成员变量同样也是。



### Java的参数传递是值传递还是引用传递？

Java参数传递方式本质只有值传递。

1. 如果参数是基本类型，传参会拷贝值

2. 如果参数是引用类型，传参会拷贝引用地址，不会动堆中的实例对象

共性：都是值传递，但传递的东西不一样。

从下面的案例可以看明白：

```java
public static void main(String[] args) {
    StringBuilder sb = new StringBuilder("iphone");
    foo(sb);
    System.out.println(sb); // iphone

    foo2(sb);
    System.out.println(sb); // iphone13

    int n = 0;
    foo(n);
    System.out.println(n); // 0
}

private static void foo(StringBuilder sb) {
    sb = new StringBuilder("ipad");
    System.out.println(sb); // ipad
}

private static void foo2(StringBuilder sb) {
    sb.append("13");
    System.out.println(sb); // iphone13
}

private static void foo(int n) {
    n = 1;
    System.out.println(n); // 1
}
```



## 字面量

字面量就是给原始数据类型和String赋值时的`源代码`。

如下右边就是字面量：

```java
boolean res = true;
char c = 'J';
byte b = 122;
short s = 10000;
int i = 99999;
String str = "字符串";
```



## 整型之间的比较（包括Integer和int）

1. Integer类型注意不要使用==比较，而应该是用equals
> ==都比较的是变量的值，由于Integer是对象类型，其值为堆地址。
但由于Integer的享元模式设计，值在[-128,127]区间内都会返回同一个Integer对象，因此区间内的对象使用==比较结果仍然正确，容易造成误解。

2. Integer和int比较会拆箱
> 包装类型和原始类型使用==比较也能获得正确的结果，是因为包装类型会拆箱成普通类型再进行比较。虽然比较结果正确，但容易产生bug，仍要避免。

## long和Long
Long的包装类型通过LongCache默认缓存了[-128,127]区间内的值（与Integer一样），因此拥有一样类似的特性。



## Switch-case条件控制语句

开发中偶尔会使用到Switch，但通常大家只了解以下常规知识点：

* switch的case中一定要记得使用break
* 如果case中没有break语句，会继续往下执行其他case、default语句。



这里列举两个小坑，来帮你深入熟悉Switch：

default可以写在任何位置，不一定作为switch的最后条件，但这种约束常常出现在编码规约中，用于避免大家踩坑

```java
int i = 0;
switch (i) {
    default:
        System.out.println("default");
    case 0:
        System.out.println("0");
    case 1:
        System.out.println("1");
}
// 结果输出：0 1
```

如果匹配到了对应的case后，没有加break语句，会继续往下执行，default条件不在下面，则不会执行，注意是继续往下执行！



若没有匹配到case条件，则会走default条件分支，若没有break语句，仍然会继续往下执行，而不会结束！

```java
int i = 0;
switch (i) {
    default:
        System.out.println("default");
    case 2:
        System.out.println("2");
    case 3:
        System.out.println("3");
}
// 结果输出：default 2 3
```



## return-finally

这是Java语法中的常见误区，也是笔试题中常考的点。






## 引用（四种引用类型）
> 强引用/弱引用/软引用/幻想引用

### 参考地址
[Java四种引用包括强引用，软引用，弱引用，虚引用](https://www.cnblogs.com/yw-ah/p/5830458.html)

### 强引用
> 只要引用存在, 垃圾回收器永远不会回收
> 这也是我们常用的编码方式
```
Object obj = new Object();
```

### 软引用
> 非必须引用, 内存溢出之前进行回收

```
Object obj = new Object();
SoftReference<Object> sf = new SoftReference<Object>(Object);
obj = null;
sf.get();//有时会返回null
```
> sf是对obj的一个软引用, 通过sf.get()可以获取这个对象, 这个对象被标记为需要回收的对象时, 则返回null.
> 用途: 软引用主要用来实现类似缓存的功能, 在内容足够的情况下, 直接通过软引用取值, 无需从繁忙的真是数据来源查询, 提升速度; 
当内存不足, 会自动删除这部分数据, 从真实来源查询.

### 弱引用
> 第二次垃圾回收时回收
```
Object obj = new Object();
WeakReference<Object> wf = new WeakReference<Object>(obj);
obj = null;
wf.get();//有时会返回null
wf.isEnQueued();//返回是否被垃圾回收器标记为即将回收的垃圾
```
> 用途: 主要用于监控对象是否已经被垃圾回收器标记为即将回收的垃圾, 通过isEnQueued()

### 虚引用
> 无法通过引用取到对象值, 垃圾回收时回收\
> 虚引用每次在垃圾回收时都会被回收, 通过虚引用的get永远无法获取到数据, 因此也被成为幽灵引用.
```
Object obj = new Object();
PhantomReference<Object> pf = new PhantomReference<Object>(obj);
obj = null;
pf.get();//永远返回null
pf.isEnQueued();//返回是否从内存中已经删除
```
> 用途: 主要用于检测对象是否已经从内存中删除.





## 反射



### Class类中的方法

* `T newInstance()`
* `T cast(Object obj)`
* `T[] getEnumConstants()`
* `Class<? super T> getSuperclass()`
* `Constructor<T> getConstructor(Class... parameterTypes) `
* `Constructor<T> getDeclareConstructor(Class... parameterTypes)`



## 动态代理





## 字节码技术







## List的去重方式以及效率对比

### 使用contains方法去重
```java
private static void removeDuplicate(List<String> list) {
    List<String> result = new ArrayList<String>(list.size());
    for (String str : list) {
        if (!result.contains(str)) {
            result.add(str);
        }
    }
    list.clear();
    list.addAll(result);
}
```

### 使用LinkedHashSet去重
```java
private static void removeDuplicate(List<String> list) {
    LinkedHashSet<String> set = new LinkedHashSet<String>(list.size());
    set.addAll(list);
    list.clear();
    list.addAll(set);
}
```

### 使用HashSet去重
```java
private static void removeDuplicate(List<String> list) {
    HashSet<String> set = new HashSet<String>(list.size());
    List<String> result = new ArrayList<String>(list.size());
    for (String str : list) {
        if (set.add(str)) {
            result.add(str);
        }
    }
    list.clear();
    list.addAll(result);
}
```

### 效率比较
LinkedHashSet去重效率最好、并且支持顺序。

### 引用地址
[list去重](https://blog.csdn.net/u012156163/article/details/78338574)


## 去重
> 使用Set来去重, 可以通过stream中的方法转换

## 分组
1. List使用Map来做分组, 会有很长的代码, 而只是做这个很常规的操作
```
// TODO 待补充代码说明
```

2. 使用stream的groupingby, 可以方便的设置分组后的key, value内容

## Double超过6位就会变成科学计数法的解决方案
1. 使用DecimalFormat
```
DecimalFormat format = new DecimalFormat("0");
return format.format(db.doubleValue());
```

2. 使用NumberFormat
> 此方案不可行


## equals和hashCode必须成对出现
> 如果对象用作hashmap的key时，会使用这两个方法进行比较判断，如果只重写一个，可能会导致相等判断失败，进而影响hashmap的功能。


## 被序列化的类中的属性对象也需要支持序列化
> findbugs会检测到这种场景，并提示异常
`This Serializable class defines a non-primitive instance field which is neither transient, Serializable, or java.lang.Object, and does not appear to implement the Externalizable interface or the readObject() and writeObject() methods.  Objects of this class will not be deserialized correctly if a non-Serializable object is stored in this field.`

在支持序列化的类中，对其所有属性都需要同步支持序列化。
```java
public class Child implements Serializable {
    private Father father;
}

// 如果Father不实现Serializable，则Child序列化会报错
public class Father {
    private String name;
}
```

## 父类属性和子类属性之间的关系
### 同名属性无法被覆盖
父类属性和子类同名属性会同时存在，子类可以使用super来引用父类的同名属性，如果不存在同名属性（子类无此属性），子类也可省略super直接使用父类属性。

### 实例方法引用当前类的属性
**父类方法引用父类属性**，不会引用子类同名属性，原因是父类方法无法感知到子类属性的存在。

### 父类方法如何使用子类属性？
* 通过子类重写的方法获取子类属性值（需要维护两个同名属性）
* 在子类构造方法中赋值给父类属性（使用super调用父类属性或者方法赋值）
* 使用静态方法赋值（类似于上一种方式）

### 原理
:todo:

## i--/i++的逻辑
此类表达式会分为以下三个步骤，因此不是原子操作：
1. 获取i的值
2. 计算i+1或i-1
3. 赋值给i


## Java IO

* 什么是同步、异步；阻塞、非阻塞？

* 有哪些IO模式，说说他们是如何实现IO模型的。
BIO、伪异步IO模式、NIO模式、AIO模式

* NIO什么地方是同步的，什么地方是非阻塞的？AIO与NIO的差异在哪？


## JDK , JRE, JVM and JavaSE的关系

![JDK-JRE-JVM-JavaSE的关系](JDK-JRE-JVM-JavaSE的关系.png)
