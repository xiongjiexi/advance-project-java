package org.example;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The type Bean test.
 */
public class BeanTest {


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

    /**
     * xml中配置自动创建
     */
    @Test
    public void test2() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        School school = (School) ctx.getBean("school");
        System.out.println("school:" + school);
    }

    /**
     * 类中使用@ComponentScan指定扫描bean的相关注解（功能同xml配置scan）
     */
    @Test
    public void test3() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
        School school = (School) ctx.getBean("school");
        System.out.println("school:" + school);
    }

    /**
     * 使用@Bean在Java类中显示配置
     */
    @Test
    public void test4() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig2.class);
        School school = (School) ctx.getBean("school");
        System.out.println("school:" + school);
    }

    /**
     * 使用@Import,@Import指定要导入的组件类，在spring容器中的id为全类名。
     */
    @Test
    public void test5() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
        System.out.println("   context.getBeanDefinitionNames() ===>> " +
                String.join(",", ctx.getBeanDefinitionNames()));
        Student student = (Student) ctx.getBean("org.example.Student");
        System.out.println("student:" + student);
    }

    /**
     * 使用ImportSelector配合@Import实现
     */
    @Test
    public void test6() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
        System.out.println("   context.getBeanDefinitionNames() ===>> " +
                String.join(",", ctx.getBeanDefinitionNames()));
        Student student = (Student) ctx.getBean("org.example.Student");
        System.out.println("student:" + student);
    }

    /**
     * ImportBeanDefinitionRegistrar接口，配合@Import实现
     */
    @Test
    public void test7() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
        System.out.println("   context.getBeanDefinitionNames() ===>> " +
                String.join(",", ctx.getBeanDefinitionNames()));
        Student student = (Student) ctx.getBean("s001");
        System.out.println("s001:" + student);
    }

}
