# Spring

## spring组件扫描/组件加载

1. `@Component`注解的类被扫描时会加载成组件bean
> a. 但是需要显式配置`组件扫描`范围
> b. `组件扫描`可以使用xml或者java config配置

2. 组件扫描的方式
> a. 使用`@ComponentScan`注解配置类, 可以让spring扫描此配置类同包或者子包中所有的`组件`.
> b. 使用`xml`配置的`spring context命名空间(<context:component-scan base-package="cn.jessex.xxx.xxxx">)`组件扫描.

3. 组件扫描注解`@ComponentScan`的几种使用
> a. 直接传值(value).
> b. 使用`@ComponentScan(basePackages = "xx.xxx")`, 可以接收单个String或者String[]数组, 但此时是类型不安全的.
> c. 使用`@ComponentScan(basePackageClasses = my.class)`, 可以接收class或class[]数组, 但重构代码同样会引发问题.
>
> > 只有以上三种方式可以配置此组件扫描注解, 但是仍然会存在一些问题, 因此, 可以写一些空标记接口(marker interface)用来进行扫描, 这样能保持代码重构友好.

4. 组件(bean)的ID
> a. spring为bean设置默认ID, 为类名`首字母小写`.
> b. 给`@ComponentScan`传值设置ID
> c. 使用Java DI规范的`@Named`设置ID(Spring支持此注解替换@ComponentScan)



## Spring创建bean

0. 使用Xml创建bean

    > 过去Spring只提供了xml配置的方式, 但现在已经不建议使用此方式了, 因为对于维护没有自动化配置和JavaConfig方便.
    >
    > 此时bena类中不需要使用任何注册bean的注解，完全由spring读取xml然后去配置。

    ```xml
    <!--使用xml的bean标签创建&装配bean-->
    <bean id="student001" class="org.example.Student">
        <property name="id" value="1" />
        <property name="name" value="jesse001" />
    </bean>
    <bean id="student002" class="org.example.Student">
        <property name="id" value="2" />
        <property name="name" value="jesse002" />
    </bean>
    <bean id="class1" class="org.example.Klass">
        <property name="students">
            <list>
                <ref bean="student001" />
                <ref bean="student002" />
            </list>
        </property>
    </bean>
    <bean id="school" class="org.example.School">
        <property name="klass" ref="class1" />
        <property name="student" ref="student002" />
    </bean>
    ```
    
    验证类：
    
    ```java
    /**
     * 使用xml加载bean
     */
    @Test
    public void test1() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student001 = (Student) ctx.getBean("student001");
        System.out.println("student001:" + student001);
        Student student002 = (Student) ctx.getBean("student002");
        System.out.println("student002:" + student002);
    }
    ```
    
    
    
1. xml配置自动scan，扫描注解创建bean

    > spring提供了多种支持注解创建bean的方式。
    >
    > xml配置`<context:component-scan base-package="org.example" />`，然后在需要创建的bean类中，使用注解即可创建bean当前bean。

    测试类：

    ```java
    /**
     * xml中配置自动创建
     */
    @Test
    public void test2() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        School school = (School) ctx.getBean("school");
        System.out.println("school:" + school);
    }
    ```

2. 类中使用@ComponentScan指定扫描bean的相关注解（功能同xml配置scan）

    > 与之前不同的是，这里不能使用xml的applicationContext来加载了，因为需要加载java类的配置，因此使用`new AnnotationConfigApplicationContext(Config.class)`。
    >
    > `@ComponentScan`默认会扫描该配置类所在包及其子包下的所有bean。

    Java配置类：

    ```java
    @ComponentScan
    public class BeanConfig {
    }
    ```

    测试类：

    ```java
    /**
     * 类中使用@ComponentScan指定扫描bean的相关注解（功能同xml配置scan）
     */
    @Test
    public void test3() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
        School school = (School) ctx.getBean("school");
        System.out.println("school:" + school);
    }
    ```

3. 使用@Bean在Java类中显示配置

    > 注解使用在Method上，通过方法的返回值创建bean，方法的入参会在spring容器中寻找存在的bean进行注入，方法名是bean的名字。

    Java bean配置类：

    ```java
    @Configuration
    public class BeanConfig2 {
        @Bean
        public Student student() {
            Student s = new Student();
            s.setId(999);
            s.setName("@beanConfig jesse999");
            return s;
        }
    
        @Bean
        public Klass klass(Student student) {
            Klass klass = new Klass();
            klass.setStudents(Collections.singletonList(student));
            return klass;
        }
    
        @Bean
        public School school(Klass klass, Student student) {
            School school = new School();
            school.setKlass(klass);
            school.setStudent(student);
            return school;
        }
    }
    ```

    测试类：

    ```java
    @Test
    public void test4() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig2.class);
        School school = (School) ctx.getBean("school");
        System.out.println("school:" + school);
    }
    ```

4. 使用@Import

    > @Import指定要导入的组件类，在spring容器中的id为全类名。

    Java配置类：

    ```java
    @Import(Student.class)
    public class BeanConfig {
    }
    ```

    测试类：

    ```java
    @Test
    public void test5() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
        System.out.println("   context.getBeanDefinitionNames() ===>> " +
                String.join(",", ctx.getBeanDefinitionNames()));
        Student student = (Student) ctx.getBean("org.example.Student");
        System.out.println("student:" + student);
    }
    ```

    

5. 使用ImportSelector配合@Import实现

    Java配置类：

    ```java
    public class MyImportSelector implements ImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{"org.example.Student"};
        }
    }
    ```

    测试类：

    ```java
    @Test
    public void test6() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
        System.out.println("   context.getBeanDefinitionNames() ===>> " +
                String.join(",", ctx.getBeanDefinitionNames()));
        Student student = (Student) ctx.getBean("org.example.Student");
        System.out.println("student:" + student);
    }
    ```

    

6. ImportBeanDefinitionRegistrar接口，配合@Import实现

    Java配置类：

    ```java
    public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
            // 指定Bean类
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Student.class);
            // 注册一个Bean，并指定Bean名
            registry.registerBeanDefinition("s001", beanDefinition);
        }
    }
    ```

    测试类：

    ```java
    @Test
    public void test7() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
        System.out.println("   context.getBeanDefinitionNames() ===>> " +
                String.join(",", ctx.getBeanDefinitionNames()));
        Student student = (Student) ctx.getBean("s001");
        System.out.println("s001:" + student);
    }
    ```

    

7. 手动注入Bean容器，有些场景下需要代码动态注入，以上方式都不适用。这时就需要创建 对象手动注入。通过DefaultListableBeanFactory注入。

## Spring的装配bean

0. 使用Xml装配bean

    > 请看Spring创建bean的xml配置。
    
    

1. 使用`@Autowired`自动装配

   > a. 此注解可以用在类的任何地方, 都能帮助对应参数进行装配.
   > b. `@Autowired(required = false)`可以设置为false, 装配时没有发现对应bean不会抛出异常, 但需要程序检查NPException
   > c. 当有多个bean满足装配时(产生歧义时), 会抛出异常.
   > d. 可以使用Java规范提供的`@Inject`替换.

2. 使用`@Bean`创建bean
   
   > a. `@Bean`创建bean只受限于Java.



### 自动装配时引发歧义

1. `@Primary`

2. `@Qualifier`

### @Autowired, @Qualifier,@Resource三者的区别与关系

@Autowired//默认按type注入
@Qualifier("cusInfoService")//一般作为@Autowired()的修饰用
@Resource(name="cusInfoService")//默认按name注入，可以通过name和type属性进行选择性注入

一般@Autowired和@Qualifier一起用，@Resource单独用。
当然没有冲突的话@Autowired也可以单独用

**混合配置时的引用问题**

1. JavaConfig配置的引用方式
> 使用`@Import`
>> a. 一个config引入另一个时使用
>> b. 创建一个上级config, 同时引入多个子config(可以传入数组class[])

2. JavaConfig中引入Xml配置
> 使用`@ImportResource`

3. Xml配置引用方式
> 使用`<import>`标签导入

4. Xml中引入JavaConfig
> 使用`<bean>`导入JavaConfig配置类
