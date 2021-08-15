


<meta name = "referrer" content = "no-referrer" />

作者： [Grey](https://www.greyzeng.com/)

原文地址：[Spring的轻量级实现](https://www.greyzeng.com/p/1d42af57.html)


## 源码

[github](https://github.com/GreyZeng/lite-spring)

## 使用TDD开发方法

[示例](https://gitee.com/greyzeng/tdd_demo)

1. 写一个测试用例
2. 运行：失败
3. 写Just enough的代码，让测试通过
4. 重构代码保持测试通过，

然后循环往复。

<!--more-->

## 说明

- 本文是参考公众号：码农翻身 的[从零开始造Spring](https://mp.weixin.qq.com/s/gbvdwpPtQcjyaigRBDjd-Q) 教程的学习笔记
- 仅实现核心功能
- 基于spring-framework-3.2.18.RELEASE版本
- 日志和异常处理待完善



## Step1 通过XML实例化一个对象
> 解析XML文件，拿到Bean的id和完整路径，通过反射方式实例化一个对象。

tag: step1
## Step2 基础工作

- 日志支持：log4j2 + SLF4j
- 异常处理
> 所有异常的顶层：BeansException

## Step3 封装BeanDefinition
DefaultBeanFactory中的BEAN_MAP目前只包括了beanClassName信息，后续如果要扩展其他的信息，肯定需要增加字段，所以我们需要抽象出一个接口，方便后续扩展其他的字段。


## Step4 封装Resource
在BeanFactory初始化的时候，传入的是XML格式的配置信息，比如bean-v1.xml, Spring会把这个抽象成一个Resource，常见Resource有
FileSystemResource->从文件地址读配置
ClassPathResource->从classpath下读配置
BeanFactory在创建Bean的时候，只关注Resource即可。

tag: step-4-2-resource


## Step5 封装XML的解析逻辑到专门的一个类中
XmlBeanDefinitionReader
用于解析XML，传入Resource，即可获取所有BeanDefinition
由于要把BeanDefinition放入BEAN_MAP中，所以XmlBeanDefinitionReader需要持有一个DefaultBeanFactory，且DefaultBeanFactory需要有注册BeanDefinition和获取BeanDefintion的能力，这样DefaultBeanFactory的职责就不单一了，所以需要抽象出一个BeanDefinitionRegistry，这个BeanDefinitionRegistry负责注册BeanDefinition和获取BeanDefintion的能力，XmlBeanDefinitionReader持有BeanDefinitionRegistry即可将解析生成的BeanDefinition注入BEAN_MAP中。

修改BeanFactoryV1Test这个测试用例，重新执行测试


tag: vstep5-final




## Step6 单例多例模式的配置实现
测试：BeanFactoryV1Test：testSingletonBean，testGetPrototypeBean

解析XML的XmlBeanDefinitionReader需要增加scope的解析逻辑
BeanDefinition这个数据结构增加是否单例，是否多例的属性
DefaultBeanFactory中getBean的时候，判断是否单例，如果是单例，则复用对象，如果是多例，则new新的对象。
抽象SingletonBeanRegistry这个接口，用于注册和获取单例对象
DefaultSingletonBeanRegistry实现这个接口


tag:vstep6-scope


## Step7 整合，抽象ApplicationContext
我们使用Spring的时候，一般是这样做的：
```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("mycontainer.xml");
UserService userService = (UserService) ctx.getBean("userService");
```
或
```java
ApplicationContext ctx = new FileSystemApplicationContext("src\\test\\resources\\bean-v1.xml");
UserService userService = (UserService) ctx.getBean("userService");
```
现在，我们需要抽象出ApplicationContext这个类来实现如上的功能
ApplicationContext

- ClassPathXmlApplicationContext（从classpath中读配置文件）
- FileSystemApplicationContext（从文件目录中读取配置文件）
- ....

tag: vstep7-applicationcontext-v1


通过观察发现，ClassPathXmlApplicationContext和FileSystemApplicationContext大部分代码都是相同的，只有在获取Resource的时候，方法不一样，所以，我们通过模板方法这个设计模式，来抽象出公有方法到一个抽象类，所有ClassPathXmlApplicationContext和FileSystemApplicationContext都实现这个抽象类。

tag: vstep7-applicationcontext-v2

## Step8 注入Bean和字符串常量
形如：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="userService" class="org.spring.service.v2.UserService">
        <property name="accountDao" ref="accountDao"/>
        <property name="itemDao" ref="itemDao"/>
        <property name="owner" value="test"/>
        <property name="version" value="2"/>
        <property name="checked" value="on"/>
    </bean>

    <bean id="accountDao" class="org.spring.dao.v2.AccountDao">
    </bean>
    <bean id="itemDao" class="org.spring.dao.v2.ItemDao">
    </bean>
</beans>
```
达到的目的就是，可以把“整型，字符串类型，简单对象类型”注入到一个Bean中。
这里我们需要解决以下几个问题：

1. 把字符串转成各种各样的Value
   1. String->Integer/int
   1. String->Boolean/boolean
   1. ......

注：**以上转换器的实现都是基于jdk中java.bean包中的PropertyEditorSupport这个类来完成的。**

   - CustomBooleanEditor
   - CustomNumberEditor
   - ....

每种类型的转换都通过类似的方式实现，然后Spring抽象出了TypeConvert这个接口，并把这些转换器加入一个特定的Map中，Map的key就是要转换的目标的类型，Value就是对应的转换器的实现类，即可实现类型转换。

2. 调用Bean的setXXX方法把这些Value值set到目标Bean中
   1. 抽象出PropertyValue
   1. BeanDefiniton需要增加方法获取PropertyValue
   1. GenericBeanDefinition中需要增加List<PropertyValue>
   1. XmlBeanDefinitionReader解析XML的时候，把List<PropertyValue>识别出来并加入BeanDefinition中（RuntimeBeanReference，TypedStringValue）
   1. BeanDefinitionValueResolver 把对应的PropertyValue给初始化好
   1. **setXXX的背后实现利用的是jdk原生：java.beans.Introspector 来实现 **见（DefaultBeanFactory的populateBean方法）




tag: vstep8-inject


## Step9 构造器注入


处理形如以下的配置：
```xml
<bean id="userService" class="org.spring.service.v3.UserService">
        <constructor-arg ref="accountDao"/>
        <constructor-arg ref="itemDao"/>
        <constructor-arg value="1"/>
    </bean>

    <bean id="accountDao" class="org.spring.dao.v3.AccountDao">
    </bean>
    <bean id="itemDao" class="org.spring.dao.v3.ItemDao">
    </bean>
```


参考测试：
ApplicationContextTestV3，在v3版本的UserService中，定义两个构造方法，需要识别出正确的构造方法。

1. 和Step8中类似，抽象出ConstructorArgument用于表示一个构造函数信息，每个BeanDefinition中持有这个对象
1. XmlBeanDefinitionReader需要负责解析出ConstuctorArgument（parseConstructorArgElements）
1. DefaultBeanFactory通过指定构造函数来生成Bean对象（ConstructorResolver注入Bean实例到构造方法中）

注：这里指定的构造函数的查找逻辑为：解析出XML的构造函数的参数列表，和通过反射拿到对应的构造函数的参数列表进行对比（每个参数的类型和个数必须一样）


tag:vstep9-constructor


## Step10 实现注解
实现两个注解：@Component  @Autowired（只针对属性注入，暂时不考虑方法注入）
且需要实现如下的XML的解析：
```xml
<?xml version="1.0" encoding="UTF-8"?>


<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context.xsd">

<context:component-scan base-package="org.spring.service.v4,org.spring.dao.v4">
</context:component-scan>


</beans>
```

1. 定义注解Component  ，Autowired
1. 需要实现的第一个功能是：给一个包名，扫描获取到这个包以及子包下面的所有Class【PackageResourceLoaderTest】包名--> *.class 【涉及一个递归调用】
1. 由于第二步中的BeanDefinition不像之前的xml那样，会对Bean配置一个id，所以，这里解析出来的Bean定义需要自动生成一个BeanId（默认先取value的配置，否则就就是类名第一个字母小写，抽象BeanNameGenerator来专门对Bean定义ID），同时，Spring中单独新建了一个AnnotatedBeanDefinition接口来定义包含注解的BeanDefinition, 封装第二步中的方法+BeanId的生成到一个类中：【ClassPathBeanDefinitionScannerTest】。
1. 实现了第3步以后，我们得到了对应的Class文件，我们需要通过某种方式去解析这个Class文件，拿到这个Class中的所有信息，特别是注解信息。使用ASM这个jar【ASM的原始用法见：ClassReaderTest】 *.class -> class Info  
1. 步骤3虽然实现了读取Class中的信息这件事。但是用ASM的原生方式解析不太方便，解析ClassMetaData和Annotation都需要定义一个Visitor，所以Spring抽象了一个接口（MetadataReader）来封装ASM的实现【MetadataReaderTest】
1. 拿到Bean中的所有Field（带注解的），并把他实例化成一个对象 ： class info 中的Field -> new instance()【DependencyDescriptorTest】
1. 将这个对象注入目标Bean中，new Instance() ——注入——>bean 【InjectionMetadataTest】
1. 处理XML解析，注入ScannerBeanDefinition
1. **整合，涉及到Bean初始化和Bean的生命周期【AutowiredAnnotationProcessorTest】**

![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596469260329-1c2cdd5a-90b1-495d-83f0-98caaf31eec1.png#align=left&display=inline&height=641&margin=%5Bobject%20Object%5D&name=image.png&originHeight=641&originWidth=694&size=220260&status=done&style=none&width=694)
![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596469280772-ad90aa43-43c2-4801-b311-e635efe44184.png#align=left&display=inline&height=667&margin=%5Bobject%20Object%5D&name=image.png&originHeight=667&originWidth=1007&size=322924&status=done&style=none&width=1007)
![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596469384687-7a7bd3c6-7fd5-45ff-922d-7b88782edbfc.png#align=left&display=inline&height=631&margin=%5Bobject%20Object%5D&name=image.png&originHeight=631&originWidth=895&size=242580&status=done&style=none&width=895)
![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596469497976-2f3956c3-1876-431b-b8c9-729f701a2c51.png#align=left&display=inline&height=610&margin=%5Bobject%20Object%5D&name=image.png&originHeight=610&originWidth=811&size=276330&status=done&style=none&width=811)
tag:vstep10-annotation-final






## step11 实现Aop






```xml
<context:component-scan
		base-package="org.litespring.service.v5,org.litespring.dao.v5">
	</context:component-scan>

	<bean id="tx" class="org.litespring.tx.TransactionManager" />
	
	<aop:config>
		<aop:aspect ref="tx">
			<aop:pointcut id="placeOrder" 
                    expression="execution(* org.litespring.service.v5.*.placeOrder(..))" />
			<aop:before pointcut-ref="placeOrder" method="start" />
			<aop:after-returning pointcut-ref="placeOrder"	method="commit" />	
			<aop:after-throwing pointcut-ref="placeOrder" method = "rollback"/>		
		</aop:aspect>
	</aop:config>
```
![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596980182372-3d01884b-31e0-48d5-8607-73928f2ebf60.png#align=left&display=inline&height=603&margin=%5Bobject%20Object%5D&name=image.png&originHeight=603&originWidth=1001&size=249280&status=done&style=none&width=1001)![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596980163933-50a7bdb1-4eb5-4353-b045-52960d3cc6d5.png#align=left&display=inline&height=635&margin=%5Bobject%20Object%5D&name=image.png&originHeight=635&originWidth=1039&size=265745&status=done&style=none&width=1039)

1. 一些术语：Joint Point, Pointcut, Advice(拦截器，Before, After, Around....）
1. 为什么要用aop：日志，安全，事务 作为切面和业务代码正交
1. 运行时动态生成类

![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596639232652-2fd478af-bc9d-44da-b2ca-5b0e56a963fe.png#align=left&display=inline&height=556&margin=%5Bobject%20Object%5D&name=image.png&originHeight=556&originWidth=876&size=220615&status=done&style=none&width=876)

3. PointcutTest: 给定一个表达式，然后判断某个类的某个方法是不是匹配这个表达式【依赖AspectJ】
   1. Pointcut：
   1. MethodMatcher： 给一个method，判断是否满足指定条件
   1. AspectJExpressionPointcut
4. MethodLocatingFactoryTest：通过Bean的名称（"tx")和方法名（"start"）定位到这个Method，然后反射调用这个Method
4. 指定指定顺序的链式调用 （Aop alliance，使用了责任链这个设计模式） ReflectiveMethodInvocationTest，需要debug查看调用链路。

![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596981990553-7ca3285c-d76d-4f53-9bb6-32e7e4a8876c.png#align=left&display=inline&height=630&margin=%5Bobject%20Object%5D&name=image.png&originHeight=630&originWidth=1044&size=266321&status=done&style=none&width=1044)

6. 动态代理，在一个方法前后增加一些逻辑，而不用改动原始代码。CGlibTest, 其中testFilter方法是表示支持多个aop操作，(使用)
   1. 普通类：CGLib
   1. 接口：Jdk自带



7. CglibAopProxyTest

![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596987811324-94b18bee-9bc8-4c81-82bb-b382915323bf.png#align=left&display=inline&height=633&margin=%5Bobject%20Object%5D&name=image.png&originHeight=633&originWidth=1055&size=310792&status=done&style=none&width=1055)


tag: vaop-v1
-----

8. 抽象AbstractV5Test
8. BeanFactoryTestV5：配置文件->Advice
8. XML解析生成BeanDefinition
   1. aop标签的内容其实是包含在GenericDefinitionBean里面, 通过人工合成的，嵌套的Beandefinition处理
11. BeandefinitionTestV5

ConfigBeanDefinitionParser.java
![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596979597946-cb9fbbdc-a6b8-40e8-989b-2ea54f7f3b45.png#align=left&display=inline&height=544&margin=%5Bobject%20Object%5D&name=image.png&originHeight=544&originWidth=1040&size=277690&status=done&style=none&width=1040)
![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596979642602-0152c8d6-601b-423b-a36f-1ce224d048c8.png#align=left&display=inline&height=613&margin=%5Bobject%20Object%5D&name=image.png&originHeight=613&originWidth=1019&size=397342&status=done&style=none&width=1019)
![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1597070435356-c982ca1f-a22a-42d4-ac07-425f62bf393e.png#align=left&display=inline&height=587&margin=%5Bobject%20Object%5D&name=image.png&originHeight=587&originWidth=720&size=200302&status=done&style=none&width=720)

11. BeanFactoryTestV5

嵌套Bean的处理

12. ApplicationContextTest5

tag: vaop-v2
-----

12. 实现jdk动态代理

AspectJAutoProxyCreator 
JdkAopProxyFactory


tag:vaop-v3
![image.png](https://cdn.nlark.com/yuque/0/2020/png/757806/1596979439545-c578424a-bd86-4eaf-b43b-a358e3369d51.png#align=left&display=inline&height=473&margin=%5Bobject%20Object%5D&name=image.png&originHeight=473&originWidth=818&size=229671&status=done&style=none&width=818)
