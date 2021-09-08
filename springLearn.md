# Spring学习笔记

> 学习视频：尚硅谷 https://www.bilibili.com/video/BV1Vf4y127N5

## 一、<u>Spring简介</u>

* 概述：Spring 是==轻量级==的开源的JavaEE框架，IOC和AOP是Spring 的两个核心部分

  > 轻量级：Spring 需要配置的依赖（jar 包）较少，也不需要额外添加其他依赖，整体体积也较小，可以独立使用

* 目的：简化企业应用开发

* 特点

  * 方便解耦，简化开发
  * 支持AOP编程（不修改源码就能实现功能扩展）
  * 方便程序测试和事务管理操作
  * 方便与其他框架进行整合，如整合 MyBatis
  * 简化 JavaAPI 的使用

***

## 二、<u>IOC</u>

### 2.1 <u>IOC简介</u>

* 概述：Inversion Of Control ==控制反转==，意思就是==将对象的创建权反转（交给）了Spring==，或者说对象创建的控制权不是"使用者"，而是"框架"或者"容器"
* 作用与目的
  * 简化操作：将对象的创建和对象之间的调用交给Spring处理
  * 降低耦合度：对象之间的调用操作采用==工厂模式间接实现==，从而降低两个对象之间调用的耦合度

### 2.2 <u>IOC底层原理</u>

#### 2.2.1 <u>底层实现原理</u>

* **底层实现技术**：xml 解析、<u>工厂模式</u>、<u>反射</u>

* **IOC 实现过程**：IOC思想基于IOC容器完成，==IOC容器底层就是对象工厂==

  1. **创建对象（以xml 配置为例）**：在xml 配置文件中配置将要创建的对象

     ```xml
     <bean id="uDao" class="com.Key.dao.UserDao"></bean>
     ```

  2. **创建工厂类**：在工厂类中==解析xml 文件和通过反射创建对象==

     ```java
     public class UserFactory {
         public static UserDao getUserDao() {
             // 1. 解析xml文件，获取对应的class属性值，此处简略具体代码...
             String classValue = class属性值...;
             
             // 2. 通过反射创建对象
             UserDao uDao = (UserDao)Class.forName(classValue).newInstance();
             
             return uDao;
         }
     }
     ```

* IOC 底层中的==工厂模式==

  <img src="D:\学习笔记\SSM\Spring\image\factory.PNG" style="zoom:51%;" />

#### 2.2.2 <u>IOC接口</u>

* Spring 提供两个IOC 接口，两个接口都可以根据xml文件创建对象

  1. `BeanFactory`

     * IOC容器的基本实现，是Spring内部使用的接口，开发人员一般不使用

     * 特点：加载配置文件时不会创建对象，在获取对象时才创建

  2. **`ApplicationContext`**（推荐）

     * `BeanFactory`接口的子接口，提供更强大的功能，一般由开发人员使用
     * 特点：在加载配置文件的时候就已经创建好对象

     > 为什么使用ApplicationContext会更好：配置文件、创建对象等操作比较耗时耗资源，所以一般将这些操作放在项目启动（服务器启动）前完成，项目启动后就可以直接使用，而不是等到要使用时才来创建

* IOC接口的两个重要实现类

  1. `FileSystemXmlApplicationContext`：传入参数为文件的全路径，即在磁盘中的位置
  2. `ClassPathXmlApplicationContext`：传入参数为文件在项目中src的类路径，如果放在src目录下，直接写文件名即可

```java
// 加载spring配置文件（xml），创建工厂类对象
// 1. 方式一
BeanFactory context01 = new ClassPathXmlApplicationContext("bean01.xml");

// 2. 方式二
ApplicationContext context02 = new ClassPathXmlApllicationContext("bean01.xml");
```

* 通过工厂对象获取对应对象

```java
/* 
	根据创建的工厂类对象context，获取对应对象
	 - 第一个参数：xml文件中<bean>标签的id属性值
	 - 第二个参数：需要获取的对象对应的Class对象（如果不写，返回对象类型默认是Object）
*/
UserDao userDao = context.getBean("uDao", UserDao.class);
```

### 2.3 <u>Bean管理</u>

#### 2.3.1 <u>Bean管理简介</u>

* 概述：==Bean管理就是对象管理==，指的就是两个操作

  1. **Spring 创建对象**：==创建对象默认采用的是无参构造器==，所以类的定义中必须有无参构造器的声明或者不添加有参构造器
  2. **Spring 注入属性**：也就是 **DI**——Dependency Injection ==依赖注入==

  > DI与IOC的区别（面试常问）：DI是IOC的一种具体实现，表示依赖注入或注入属性，必须在已经创建好对象的基础上完成

* Bean管理操作的两种方式

  1. 基于xml 文件实现
  2. 基于注解方式实现（推荐）

#### 2.3.2 <u>基于xml文件</u>

##### * <u>创建对象</u>

* 在spring配置文件（xml）==使用<bean>标签==并添加对应属性

  ```xml
  <bean id="u" class="com.Key.spring.bean.User"></bean>
  ```

  > <bean>标签中两个重要属性
  >
  > * id：对象的唯一标识
  > * class：对象的全类名

##### <u>* 注入属性</u>

> 注意：2~5只使用了 set() 方法进行注入

###### 1. 注入普通类型属性

* **使用set() 方法进行注入**

  1. 定义一个类，添加setProperty() 方法

     ```java
     /**
      * User实体类
      *  - 成员变量一：name
      *  - 成员变量二：age
      */
     public class User {
         private String name;
         private int age;
         
         public void setName(String name) {
             this.name = name;
         }
         
         public void setAge(int age) {
             this.age = age;
         }
     }
     ```

  2. 在xml 文件中的<bean>标签中添加<property>标签，并添加相应属性

     ```xml
     <bean id="u" class="com.Key.spring.bean.User">
     	<property name="name" value="周瑜"></property>
         <property name="age" value="24"></property>
     </bean>
     ```

     > <property>标签中的两个属性
     >
     > * name：属性名。注意这里是==对应的是属性名==，即与方法setXxx() 中的xxx对应，不一定是成员变量名
     >
     > * value：对应属性值（成员变量的值）

* p名称空间注入：set() 方法注入的简化操作（了解即可）

  1. 在配置文件中的<beans>标签中创建p名称空间：`xmlns:p="http://www.springframework.org/schema/p"`

     ```xml
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:p="http://www.springframework.org/schema/p"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         
         <!-- code... -->
     </beans>
     ```

  2. 直接在<baen>标签的属性栏上添加属性 p:property 进行属性的注入

     > 注入外部Bean的写法：`p:uDao-ref="uDao"`

     ```xml
     <bean id="u" class="com.Key.spring.bean.User" p:name="周瑜" p:age="24"></bean>
     ```

* **使用有参构造器进行注入**

  1. 定义一个类，添加有参构造器

     ```java
     public class User {
         private String name;
         private int age;
         
         public User(String username, int age) {
             this.name = username;
             this.age = age;
         }
     }
     ```

  2. 在xml 文件中的<bean>标签中添加<constructor-arg>标签，并添加对应属性

     ```xml
     <bean id="u" class="com.key.spring.bean.User">
     	<constructor-arg name="username" value="诸葛亮"></constructor-arg>
         <constructor-arg name="age" value="21"></constructor-arg>
     </bean>
     ```

     > <constructor-arg>标签的重要属性
     >
     > * name：有参构造器中形参的名称。注意这里==对应的是形参名==，不一定是属性名，也不一定是成员变量名
     > * value：对应属性值（成员变量值）
     > * index：成员变量在类中定义的先后次序，从0开始

###### 2. 注入特殊类型属性

> 引入概念——**字面量**：==Java 中将数据称为字面量==，如在`int a = 2;`中，a是变量，2就是字面量

* 注入null值：直接在<property>标签体中添加<null/>标签即可

  ```xml
  <property name="name">
  	<null/>
  </property>
  ```

* 注入带特殊符号的属性值：特殊符号是指在xml 中有特殊用法的敏感字符，如<、>、&等，解决方法可参考 <u>Web学习笔记.md</u>

  1. 使用转义字符代替特殊符号（参考 <u>Web学习笔记.md</u>）

  2. 使用==<value>标签和CDATA区域==，把带有特殊符号值放在CDATA区域，如把”<<三国演义>>“赋值给属性bookName

     ```xml
     <property name="bookName">
     	<value><![CDATA[<<三国演义>>]]></value>
     </property>
     ```

* **注入数组、集合类型属性**（==List 和数组注入方式一样==，这里只演示数组）

  * 数组或集合元素类型为普通类型

    1. 创建一个类用于测试，类中定义有数组类型、set类型和Map类型的属性

       ```java
       public class Students {
           private String[] course;
           private Map<String,String> stuMap;
           private Set<String> stuSet;
       
           public void setCourse(String[] course) {
               this.course = course;
           }
       
           public void setStuMap(Map<String, String> stuMap) {
               this.stuMap = stuMap;
           }
       
           public void setStuSet(Set<String> stuSet) {
               this.stuSet = stuSet;
           }
       }
       ```

    2. 在spring配置文件中创建对象，并注入各个类型的属性（都是在<property>标签中添加）

       * 数组或List 集合类型注入：添加<array>或<list>标签，然后在<array>或<list>标签体中添加<value>进行赋值

         ```xml
         <list>
         	<value>高数</value>
         	<value>c语言</value>
         </list>
         ```

       * set 集合类型注入：添加<set>标签，然后在<set>标签体中添加<value>进行赋值

         ```xml
         <set>
         	<value>set1</value>
         	<value>set2</value>
         </set>
         ```

       * map 集合类型注入：添加<map>标签，然后在<map>标签体中添加<entry>标签，在<entry>标签的属性中进行赋值

         ```xml
         <map>
         	<entry key="周一" value="上课"></entry>
         	<entry key="周日" value="放假"></entry>
         </map>
         ```

         > <entry>标签的两个属性
         >
         > * key：对应map 集合中的键key
         > * value：对应map 集合中的值value

  * 数组或集合元素类型为对象：数组、集合类型的操作都是相似的，这里只演示List 集合

    1. 创建课程类，在学生类中添加元素类型是课程类的集合属性——`private List<Course> stuCourse;`

       ```java
       public class Course {
           private String cname;
       
           public void setCname(String cname) {
               this.cname = cname;
           }
       }
       ```

    2. 在spring 配置文件中创建多个课程类对象，然后再学生对象的创建中注入对应集合属性，集合中添加创建好的多个课程对象，==将<value>标签换成<ref>，并通过bean 属性赋值==

       ```xml
       <!-- 创建多个课程类对象 -->
       <bean id="c1" class="com.Key.spring.ioc.bean.Course">
           <property name="cname" value="java实验课"></property>
       </bean>
       <bean id="c2" class="com.Key.spring.ioc.bean.Course">
           <property name="cname" value="c语言实验课"></property>
       </bean>
       <bean id="c3" class="com.Key.spring.ioc.bean.Course">
           <property name="cname" value="数据结构实验课"></property>
       </bean>
       
       <!-- 创建学生对象，并对其多个数组、集合类型属性赋值 -->
       <bean id="stu" class="com.Key.spring.ioc.bean.Students">
           <!-- 这里省略其他属性的注入... -->
       
           <!-- 给list集合赋值，list集合元素类型是Course -->
           <property name="stuCourse">
               <list>
                   <ref bean="c1"></ref>
                   <ref bean="c2"></ref>
                   <ref bean="c3"></ref>
               </list>
           </property>
       </bean>
       ```

       > <ref>标签的属性——bean：集合中每个元素对象对应的唯一标识id 值

  * 通过==util 名称空间==实现数组或集合类型的注入（将集合类型属性的注入单独取出来）

    1. 在spring 配置文件的根标签<beans>的属性栏中创建util 名称空间

       * 添加属性——`xmlns:util="http://www.springframework.org/schema/util"`
       * 在`xsi:schemaLocation`属性的原有值后添加`http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd`

       > 创建util 名称空间的方法（通用）
       >
       > * 将属性`xmlns`的值`http://www.springframework.org/schema/beans`中的==<u>beans</u>换成<u>util</u>==
       > * 将属性`xsi:schemaLocation`的原有值`http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd`中的==所有<u>beans</u>都换成<u>util</u>==

       ```xml
       <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:util="http://www.springframework.org/schema/util"
              xsi:schemaLocation="http://www.springframework.org/schema/beans 				  http://www.springframework.org/schema/beans/spring-beans.xsd
                                  http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
           
           <!-- code... -->
       </beans>
       ```

    2. 在创建对象的外部使用\<util:list> 标签对集合类型属性进行赋值（其他集合类型的赋值类似——\<util:map>、\<util:set>）

       ```xml
       <!-- 使用util名称空间在创建对象外部对集合类型属性赋值 -->
       <util:list id="utilList">
           <value>大物</value>
           <value>离散</value>
       </util:list>
       ```

    3. 把已经注入完成的集合类型属性添加到对象中：在<property>标签中采用 ref 属性，与注入对象类属性的方式类似

       ```xml
       <!-- 创建学生对象，并注入属性 -->
       <bean id="stu" class="com.Key.spring.ioc.bean.Students">
           <!-- 这里省略其他属性的注入... -->
           
           <property name="course" ref="utilList"></property>
       </bean>
       ```

###### 3. 注入对象类型属性

* 用法：用于==有调用关系==的类之间，比如service类和dao类

  1. 创建好service类和dao类，在service类中定义dao类的成员变量，根据成员变量调用其方法

     ```java
     /**
      * 注意：这里只是简单演示，实操的时候需要添加接口类，在实现类进行以下操作
      */
     public class UserService {
         private UserDao uDao;
         
         // 要加上对应的set方法
         public void setUDao(UserDao uDao) {
             this.uDao = uDao;
         }
         
         public void update() {
             // 调用dao类对象的方法
             uDao.update();
             
             // code...
         }
     }
     ```

  2. 在spring配置文件（xml）中创建service类对象和dao类对象，并在service类对象中注入属性，有外部和内部两种方式

     * 外部Bean方式注入：==在外部创建好属性对应的类对象==，然后根据外部Bean的唯一标识id 进行注入

     ```xml
     <!-- 
     	在外部创建dao类对象
      	 - 注意：这里这是简单演示，实操的时候，class中写的应该是实现类的全类名，而不是接口，下同
     -->
     <bean id="uDao" class="com.Key.spring.dao.UserDao"></bean>
     
     <!-- 创建service类对象，并注入属性 -->
     <bean id="uService" class="com.Key.spring.service.UserService">
         <property name="uDao" ref="uDao"></property>
     </bean>
     ```

     > <property>标签中的两个属性
     >
     > * name：属性名
     >
     > * ref：对象类型属性在外部创建时<bean>标签中的id值（唯一标识）

     * 内部Bean的方式注入：==直接在<property>标签体（内部）中使用<bean>标签创建==属性对应的类对象

     ```xml
     <!-- 
     	创建service类对象，并注入属性
      	 - 在<property>标签体（内部）中创建属性对应的类对象
     	 - 内部的<bean>标签可以不添加id属性
     -->
     <bean id="uService" class="com.Key.spring.service.UserService">
         <property name="uDao">
         	<bean id="uDao" class="com.Key.spring.dao.UserDao"></bean>
         </property>
     </bean>
     ```

###### 4. 级联赋值

> 引入概念——一对多的关系：数据库的知识点，如一个班级对应多个学生，一个部门对应多个员工

1. 创建部门类和员工类，在员工类中添加部门类型的成员变量，表示两者一对多的关系

2. 在spring 配置文件中创建两个类的对象，并对员工属性的注入中==同时对其所在的部门的属性进行赋值==，实现级联赋值

   * 通过外部Bean或内部Bean的方式（这里只演示外部）

     ```xml
     <!-- 
     	在外部创建部门对象 
     	 - 部门对象有属性deptName，这里直接赋值
     -->
     <bean id="dept" class="com.Key.spring.bean.Department">
     	<property name="deptName" value="技术部"></property>
     </bean>
     
     <!-- 
     	创建员工对象，并注入属性 
     	 - 外部部门对象的属性已经赋值，这里直接通过ref属性注入部门对象即可 
     -->
     <bean id="emp" class="com.Key.spring.bean.Employee">
         <property name="empName" value="孙尚香"></property>
         <property name="gender" value="女"></property>
         <property name="empDept" ref="dept"></property>
     </bean>
     ```

   * 通过`对象.属性`（这里对象是指员工中的部门属性）的方式（这里是基于外部Bean的方式，内部Bean的方式也可以）

     * 先在员工类中==添加部门属性对应的get方法==，不然无法找到`对象.属性`中的对象

     * 在员工对象创建的<bean>标签体中添加`<property name="empDept.deptName" value="销售部"></property>`

     ```xml
     <!-- 
     	在外部创建部门对象 
     	 - 部门对象有属性deptName，这里直接赋值
     -->
     <bean id="dept" class="com.Key.spring.bean.Department"></bean>
     
     <!-- 
     	创建员工对象，并注入属性 
     	 - 外部部门对象的属性已经赋值，这里直接通过ref属性注入部门对象即可 
     -->
     <bean id="emp" class="com.Key.spring.bean.Employee">
         <property name="empName" value="孙尚香"></property>
         <property name="gender" value="女"></property>
         <property name="empDept" ref="dept"></property>
         <property name="empDept.deptName" value="销售部"></property>
     </bean>
     ```

###### 5. 自动装配

> PS：==一般使用注解方式实现自动装配==，不使用xml 文件；而且自动装配==适用于自定义对象类型的属性==

* 概述：==只需声明属性的名称或者属性的类型==即可完成属性的注入，不需要使用<property>标签手动给各个属性赋值

* 用法：在<bean>标签中添加属性——`autowire`，属性值有两种

  * **byName**：表示根据Bean 的属性名来赋值，自定义的对象创建的<bean>标签中的==id值必须与对象类型的属性名保持一致==

  * **byType**：表示根据Bean 的属性类型来赋值，属性对应的对象创建在同一个xml 文件中只能出现一次，即==同一个xml 文件不能创建与属性对应对象的类型一样的对象==

    > 解释：当autowire的值为byType 时，spring 会根据属性的类型找到对应的对象（根据<bean>标签中的class属性值），然后将该对象赋值给对应属性，如果同一个xml 文件中出现多个类型一样的对象，spring 就不知道哪一个才是需要赋值的，会报错

##### * <u>FactoryBean</u>

* 概述：spring 中有两种类型的Bean，一种是普通的Bean，一种是==工厂Bean==——Factory

* 普通Bean和工厂Bean的区别

  * 普通Bean：在配置文件中定义的bean 类型和返回类型是一样的
  * 工厂Bean：在配置文件中定义的==bean 类型和返回类型可以不一样==

* FactoryBean的实现

  1. 创建一个类，实现 FactoryBean<T> 接口，作为自定义的工厂对象`MyBean`

  2. 在spring配置文件中创建自定义的工厂对象

     ```xml
     <bean id="myBean" class="com.Key.spring.bean.MyBean"></bean>
     ```

  3. 在自定义的工厂类中实现接口的 getObject()方法，方法中==定义实际返回的bean 类型==（即泛型 T）

     ```java
     public class MyBean implements FactoryBean<User> {
         @Override
         public User getObject() throws Exception {
             // 创建User对象并返回
             return new User("曹操", 43);
         }
     }
     ```

##### * <u>注入外部属性</u>

> 引入概念——德鲁伊Druid数据库连接池属性信息
>
> * 数据库驱动类：prop.driverClass=com.mysql.jdbc.Driver（MySQL 5之后是 com.mysql.cj.jdbc.Driver）
> * 数据库地址：prop.url=jdbc:mysql://localhost:3306/test
> * 数据库用户名：prop.username=root
> * 数据库用户密码：prop.password=***

* 概述：一般用于配置数据库连接的属性信息，需要注入的文件一般命名为 jdbc.properties

* 使用步骤

  1. 创建 jdbc.properties 文件，文件中输入对应的数据库连接信息（druid 的需要的数据库连接信息）

  2. 在spring 配置文件中==引入context 名称空间==（创建方法略，可参考util 名称空间的创建）

  3. 在spring 配置文件中==使用context 名称空间中的 \<context:property-placehoder> 标签==将外部属性文件 jdbc.properties 引入

     ```xml
     <context:property-placehoder location="classpath:jdbc.properties"/>
     ```

     >  \<context:property-placehoder> 标签的属性——location：外部属性文件的位置，如果文件在src目录下，可直接写为 `classpath:jdbc.properties`

  4. 在<bean>标签中使用<property>标签注入外部属性，使用方法与注入其他普通类型属性一样，但 ==value 的值用表达式代替==，表达式为——`${属性文件中属性名}`（EL表达式？）

     ```xml
     <bean id="dataSource" class="com.alibaba.druid.pool.DriverDataSource">
         <property name="driverClassName" value="${prop.driverClass}"></property>
         <property name="url" value="${prop.url}"></property>
         <property name="username" ref="${prop.username}"></property>
         <property name="password" value="${prop.password}"></property>
     </bean>
     ```

     > PS：<property>标签中的name属性值都是DriverDataSource 类中的属性，所以是固定的，不是自定义的

#### 2.3.3 <u>基于注解</u>

> 什么是注解：==代码特殊标记==
>
> 1. 格式为：`@注解名称(属性名=属性值,属性名={属性值1,属性值2})`。各个属性之间用`,`隔开，同一个属性的多个属性值用`{}`括起来，并用`,`隔开
> 2. 使用方式：可以作用在类、方法、成员变量上
> 3. 作用：简化xml 配置

##### * <u>开启注解</u>

* 导入jar 包：spring-aop-...-RELEASE.jar

* **开启组件扫描**：有两种方式

  1. **在xml 文件中配置**：在xml 文件中==添加context 名称空间==，然后==使用\<context:component-scan>标签==

     ```xml
     <context:component-scan base-package="com.Key"></context:component-scan>
     ```

     > \<context:component-scan>标签中的`base-package`属性：需要扫描的包的全路径，有两种写法
     >
     > 1. 如果需要扫描的包在不同包下，则可以添加多个包的全路径，不同包通过`,`隔开
     >
     >    ```xml
     >    <context:component-scan base-package="com.Key.dao,com.Key.service"></context:component-scan>
     >    ```
     >
     > 2. 如果需要扫描的包在同一个包下，则直接写需要扫描的包的上一层包即可（包），如dao包和service包都在com.Key下，则直接写"com.Key"即可
     >
     >    ```xml
     >    <context:component-scan base-package="com.Key"></context:component-scan>
     >    ```

     * xml 文件开启组件扫描的细节操作

       1. 指定注解进行扫描

          ```xml
          <!-- 设置只扫描含有@Controller注解 -->
          <context:component-scan base-package="com.Key" use-default-filter="false">
          	<context:include-filter type="annotation" 
                                      expression="...Controller"></context:include-filter>
          </context:component-scan>
          ```

       2. 排除某种注解进行扫描

          ```xml
          <!-- 设置除了@Controller注解不扫描，其他注解都扫描 -->
          <context:component-scan base-package="com.Key">
          	<context:exclude-filter type="annotation" 
                                      expression="...Controller"></context:exclude-filter>
          </context:component-scan>
          ```

          > 各个标签及其属性说明
          >
          > * `use-default-filters`属性：表示是否使用默认的filters（过滤器）
          >   1. 默认值为"true"，表示使用默认过滤器，则对于指定包中==所有注解都进行扫描==
          >   2. 如果值为"false"，表示不使用默认过滤器，==只扫描指定的注解==
          >
          > * `<context:include-filter>`标签：设置包含过滤器——设置==指定注解==（include）的过滤器
          > * `<context:exclude-filter>`标签：设置非包含过滤器——设置==排除某个注解==（exclude）的过滤器
          > * `type`属性：值为"annotation"，表示扫描的是注解
          > * `expression`属性：==指定注解名的位置==，如`org.springframework.stereotype.Controller`

  2. **创建配置类**

     * 使用：创建一个类，在类的上面==添加`@Configuration`和`@ComponentScan`注解==，并在`@ComponentScan`注解中添加`basePackage`属性（对应xml 配置中的`base-package`属性），属性值的写法与`base-package`属性一样

       > PS：`basePackage`属性是数组，可以添加多个属性值，所以需要将属性值写在`{}`中

       ```java
       @Configuration
       @ComponentScan(basePackages = {"com.Key"})
       public class SpringConfig {
           // code...
       }
       ```

     * 作用：脱离xml 文件配置，实现==完全注解开发==

     * **获取工厂对象**：实现完全注解开发说明不能通过加载xml 配置文件获取工厂对象，而是需要==加载配置类的字节码文件（.class）来获取==

       ```java
       // 1. 通过加载xml 配置文件获取工厂对象
       ApplicationContext context01 = new ClassPathXmlApplicationContext("bean.xml");
       
       // 2. 通过加载配置类(SpringConfig)字节码文件获取工厂对象
       ApplicationContext context02 = new AnnotationConfigApplicationContext(SpringConfig.class);
       ```

##### * <u>创建对象</u>

* 创建对象有4种方式，4种注解方式都是一样的，只是==用于标识项目开发中不同层的类==

  1. **@Component**：适用于普通组件和普通类
  2. **@Service**：适用于业务逻辑层（Service层）
  3. **@Controller**：适用于Web层、控制器（Controller层）
  4. **@Repository**：适用于持久层、Dao层

* 给对象添加唯一标识：基于以上注解，添加`value`属性（不是`id`）

  * `value`属性对应xml 配置中<bean>标签的`id`属性

  * 可以省略`value`属性，==默认值为第一个字母小写的类名==

  ```java
  @Service(value = "userService")
  public class UserService {
      public void add() {
          System.out.println("基于注解方式创建对象...");
      }
  }
  ```

##### * <u>注入属性</u>

> PS：使用注解方式注入属性时，==内部已经封装好set 方法，不需要在类中添加==

###### 1. 注入普通类型属性

* **@Value**：在成员变量上添加`@Value`注解，==注解中添加`value`属性==，属性值就是成员变量的值

  ```java
  @Value(value = "刘备")
  private String name;
  ```

###### 2. 注入对象类型属性

* 三种注解方式：下面的测试代码是基于dao层和service层的关系——在UserService类中添加UserDao类型的成员变量，其中UserDao是接口类，UserDaoImpl是对应的实现类

  1. **@Autowired**：根据成员变量对应的==对象类型自动装配==，可以单独使用

     > PS：根据变量对应的对象类型自动装配==前提是该对象类型接口只有一个实现类==

     ```java
     @Autowired
     private UserDao userDao;
     ```

  2. **@Qualifier**：根据成员变量对应的==对象唯一标识（对象名）==注入属性，==与`@Autowire`一起使用==，需要添加`value`属性，属性值为变量对应的对象唯一标识

     > PS：当变量对应的对象类型接口（UserDao）==有多个实现类（UserDaoImpl）时==，需要添加`@Qualifier`注解，并标注对象名，找到指定的实现类对象，不能直接使用`@Autowire`注解

     ```java
     @Autowired
     @Qualifier(value = "userDaoImpl01")
     private UserDao userDao;
     ```

  3. **@Resource**：可以根据类型注入，也可以名称注入，可以单独使用。

     * 根据类型：直接在成员变量前加上`@Resource`

       ```java
       @Resource
       private UserDao userDao;
       ```

     * 根据名称：需要添加`name`属性（不是`value`），属性值为变量对应的对象唯一标识（跟`@Qualifier`中的`value`一样）

       ```java
       @Resource(name = "userDaoImpl02")
       private UserDao userDao;
       ```

  > PS：`@Autowire`和`@Qualifier`是spring中特有的注解，而==`@Resource`是Java中自带的注解==（在java拓展包javax中），所以spring官方推荐使用前两种

#### 2.3.4 <u>spring bean 作用域</u>

> 引入概念
>
> * 单实例对象：对于所有的请求都是同一个对象去处理，相当于静态代码，只加载一次，使用多次
> * 多实例对象：对于多次请求，创建多个对象去处理

* spring 中创建的bean ==默认是单例实例的==

##### * <u>xml文件中配置</u>

* 修改spring 中的bean 作用域（设置单例还是多例）：==在<bean>标签中添加属性——scope==，scope 的值由多种选择

  1. **singleton**
     * 默认值，表示创建的对象是单实例的
     * 将scope 属性这是为 singleton 后，加载spring 配置文件时就会把对象创建出来

  2. **prototype**
     * 将创建的对象设置为多实例
     * 将scope 属性这是为 prototype 后，==加载spring 配置文件时不会创建对象==，在获取对象或使用对象的时候才会创建
  3. request：将对象的作用域设置为request 域
  4. session：将对象的作用域设置为session 域

  ```xml
  <bean id="u" class="com.Key.spring.bean.User" scope="prototype"></bean>
  ```

##### * <u>注解配置</u>

* 在需要修改的作用域的类上添加`@Component`和`@Scope`注解，注解中直接添加值即可，值的选择和默认值同上

  > PS：需要Bean 需要被创建后才能修改其作用域，所以必须先添加`@Component`注解（其他创建对象的注解也可）

  ```java
  @Component
  @Scope("prototype")
  public class User {
      // code...
  }
  ```

#### 2.3.5 <u>spring bean 生命周期</u>

> 参考博文：https://blog.csdn.net/cool_summer_moon/article/details/106149339

<img src="D:\学习笔记\SSM\Spring\image\beanLife.PNG" style="zoom:51%;" />

##### * <u>四个基础阶段</u>

* bean 的声明周期可==初步分为四个阶段==

  1. **Bean 的实例化阶段**：执行Bean 的无参构造器

  2. **Bean 的属性注入阶段**：执行Bean 的所有成员变量对应的set 方法

  3. **Bean 的初始化阶段**：执行自定义的初始化方法或Java 接口内部方法

     > 获取Bean阶段：在Bean 的初始化阶段后、销毁阶段前还有==获取和使用Bean 的阶段==
     >
     > ```java
     > // 获取对象
     > BeanLifeDemo lifeDemo = context.getBean("bl", BeanLifeDemo.class);
     > // 使用对象
     > System.out.println(lifeDemo);
     > ```

  4. **Bean 的销毁阶段**：执行自定义的销毁方法或Java 接口内部方法（需要手动执行）

##### * <u>添加后置处理器</u>

* 后置处理器：创建一个类实现BeanPostProcessor 接口，并实现接口的两个方法
* **执行时间**：在Bean 初始化阶段的前后分别执行后置处理器的`postProcessBeforeInitialization()`方法和`postProcessAfterInitialization()`方法

##### * <u>实现aware类型接口</u>

> aware：意识到、感知的意思

* aware类型接口：能感知到所有`Aware`前面的含义的接口，如BeanNameAware、ApplicationContextAware

* 接口的作用：==加载资源到Spring==中，`Aware`前面的名字对应加载的资源

* **执行时间**：Bean 属性注入阶段之后、后置处理器的`postProcessBeforeInitialization()`方法之前

* 各个aware 接口的执行顺序以及各自对应的执行方法（去掉`Aware`，前面加上`set`）

  1. BeanNameAware --> `setBeanName()`
  2. BeanClassLoaderAware --> `setBeanClassLoader()`
  3. BeanFactoryAware --> `setBeanFactory()`
  4. EnvironmentAware --> `setEnvironment()`
  5. ResourceLoaderAware --> `setResourceLoader()`
  6. ApplicationEventPublisherAware --> `setApplicationEventPublisher()`
  7. ApplicationContextAware --> `setApplicationContex()`

  > `BeanFactory`和`ApplicationContext`的区别：`BeanFactoryAware`接口之前加载的资源都是公共的，之后加载的资源都是`ApplicationContext`独有的

##### * <u>Bean 初始化的方式</u>

> PS：常用的方法是前面两种，即自定义初始化方法，销毁方法也一样

* 实现Bean 初始化有三种方式
  1. 基于xml 文件：在Bean 对应的类中==自定义一个初始化方法==，并在配置文件的<bean>标签中添加`init-method`属性，==值为自定义的初始化方法名==
  2. 基于注解：在Bean 对应的类中自定义一个初始化方法，方法上添加注解`@PostConstruct`
  3. 实现接口（基于Java）：让Bean 对应的类实现`InitializingBean`接口，并实现接口的`afterPropertiesSet()`方法

##### * <u>Bean 销毁的方式</u>

> PS：Bean 的销毁不会自动执行，需要==手动调用工厂对象的 close() 方法==
>
> ```java
> /*
> 	创建工厂对象
> 	 - 注意这里是直接采用ClassPathXmlApplicationContext作为对象类型，而不是Application接口
> 	  - 因为close()方法是在ClassPathXmlApplicationContext中创建的，不是实现于Application接口
> 
> */
> ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanLifeDemo.xml");
> 
> // code...
> 
> // 关闭工厂对象，同时销毁Bean（执行对应的销毁方法）
> context.close();
> ```

* 实现Bean 初始化有三种方式
  1. 基于xml 文件：在Bean 对应的类中==自定义一个销毁方法==，并在配置文件的<bean>标签中添加`destroy-method`属性，==值为自定义的销毁方法名==
  2. 基于注解：在Bean 对应的类中自定义一个销毁方法，方法上添加注解`@PreDestroy`
  3. 实现接口（基于Java）：让Bean 对应的类实现`DisposableBean`接口，并实现接口的`destroy()`方法

##### * <u>测试程序</u>

* 测试Bean --> BeanLifeDemo.java

  ```java
  public class BeanLifeDemo implements 
      InitializingBean, DisposableBean, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware,
          EnvironmentAware, ResourceLoaderAware, ApplicationEventPublisherAware, ApplicationContextAware {
  
  /*+---------------------------------------实例化阶段---------------------------------------------+*/
  
      public BeanLifeDemo() {
          System.out.println("一、实例化 --> 执行无参构造器...");
      }
  
  
  /*+---------------------------------------注入属性阶段---------------------------------------------+*/
      private String name;
      private int age;
      public void setName(String name) {
          System.out.println("二、注入属性 --> 2.1 执行setName()");
          this.name = name;
      }
      public void setAge(int age) {
          System.out.println("二、注入属性 --> 2.2 执行setAge()");
          this.age = age;
      }
      @Override
      public String toString() {
          return "BeanLifeDemo{" +
                  "name='" + name + '\'' +
                  ", age=" + age +
                  '}';
      }
  
  
  /*+---------------------------------------实现各个aware 接口的各个set方法---------------------------------------------+*/
  
      @Override
      public void setBeanClassLoader(ClassLoader classLoader) {
          System.out.println("三、实现aware 接口 --> 3.2 对应BeanClassLoaderAware接口");
      }
  
      @Override
      public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
          System.out.println("三、实现aware 接口 --> 3.3 对应BeanFactoryAware接口");
      }
  
      @Override
      public void setBeanName(String s) {
          System.out.println("三、实现aware 接口 --> 3.1 对应BeanNameAware接口");
      }
  
      @Override
      public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          System.out.println("三、实现aware 接口 --> 3.7 对应ApplicationContextAware接口");
      }
  
      @Override
      public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
          System.out.println("三、实现aware 接口 --> 3.6 对应ApplicationEventPublisherAware接口");
      }
  
      @Override
      public void setEnvironment(Environment environment) {
          System.out.println("三、实现aware 接口 --> 3.4 对应EnvironmentAware接口");
      }
  
      @Override
      public void setResourceLoader(ResourceLoader resourceLoader) {
          System.out.println("三、实现aware 接口 --> 3.5 对应ResourceLoaderAware接口");
      }
  
  
  /*+---------------------------------------初始化阶段---------------------------------------------+*/
  
      /**
       * 自定义初始化方法
       *  - 基于xml文件：在<bean>中添加 init-method 属性，属性值为方法名
       *  - 基于注解：在方法上添加注解 @PostConstruct
       */
      public void initMethod() {
          System.out.println("五、初始化 --> 5.2 自定义初始化方法");
      }
  
      /**
       * 实现Java 接口内部的初始化方法
       *  - 实现 InitializingBean 接口
       *  - 不用配置和添加注解
       */
      @Override
      public void afterPropertiesSet() throws Exception {
          System.out.println("五、初始化 --> 5.1 Java接口内部的初始化方法");
      }
  
  
  /*+---------------------------------------销毁阶段---------------------------------------------+*/
      /**
       * 自定义销毁方法
       *  - 基于xml文件：在<bean>中添加 destroy-method 属性，属性值为方法名
       *  - 基于注解：在方法上添加注解 @PreDestroy
       */
      public void destroyMethod() {
          System.out.println("八、销毁 --> 8.2 自定义销毁方法");
      }
  
      /**
       * 实现Java 接口内部的销毁方法
       *  - 实现 DisposableBean 接口
       *  - 不用配置和添加注解
       */
      @Override
      public void destroy() throws Exception {
          System.out.println("八、销毁 --> 8.1 Java接口内部的销毁方法");
      }
  }
  ```

* 自定义后置处理器 --> MyBeanPostProcessor.java

  ```java
  public class MyBeanPostProcessor implements BeanPostProcessor {
  
      /**
       * 在Bean 初始化前执行的方法
       */
      @Override
      public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
          System.out.println("四、将Bean交给后置处理器 --> Bean初始化前执行...");
          return bean;
      }
  
      /**
       * 在Bean 初始化后执行的方法
       */
      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
          System.out.println("六、将Bean交给后置处理器 --> Bean初始化后执行...");
          return bean;
      }
  }
  ```

* 基于xml 文件配置 --> beanLifeDemo.xml

  ```xml
  <!-- 创建自定义的后置处理器对象（配置后置处理器） -->
  <bean id="myBeanPost" class="com.Key.spring.ioc.beanlife.MyBeanPostProcessor"></bean>
  
  <!--
      创建bean生命周期演示对象
       - 注入属性
       - 配置自定义的初始化方法和销毁方法
  -->
  <bean id="bl" class="com.Key.spring.ioc.beanlife.BeanLifeDemo" init-method="initMethod" destroy-method="destroyMethod">
      <property name="name" value="宋江"></property>
      <property name="age" value="54"></property>
  </bean>
  ```

* 测试方法

  ```java
  @Test
  public void testBeanLife() {
      // 加载spring配置文件，获取工厂对象
      ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanLifeDemo.xml");
      // 获取对象
      BeanLifeDemo lifeDemo = context.getBean("bl", BeanLifeDemo.class);
  
      System.out.println("七、获取实例化对象 --> " + lifeDemo);
  
      // 关闭工厂对象，即销毁Bean，spring bean不会自动销毁，必须调用工厂对象的close() 方法
      context.close();
  }
  ```

* 测试结果

  <img src="D:\学习笔记\SSM\Spring\image\lifeDemoResult.PNG" style="zoom:60%;" />

***

## 三、<u>AOP</u>

### 3.1 <u>AOP简介</u>

* 概述：Aspect Oriented Programming ==面向切面（方面）编程==，意思是==不通过修改源代码的方式，在主干功能上添加新的功能==
* 作用与目的：利用AOP可以对业务逻辑的==各个部分进行隔离==，从而使得业务逻辑各部分之间的==耦合度降低==，提高程序的可重用性，同时提高了开发的效率

### 3.2 <u>AOP底层原理</u>

> 引入概念——动态代理：代理模式的实现方式之一，即在在内存中形成代理类

* AOP底层通过==动态代理实现==，根据==有无接口==将动态代理分为两种——JDK和CGLIB

  1. **JDK动态代理**：需要增强的是==接口中的方法==，则要创建对应接口的==实现类代理对象==，通过实现类代理对象增强方法的功能
     * 通过==Proxy 类==来实现，实现步骤可参见 <u>设计模式.md</u>

  2. CGLIB动态代理：需要增强的是==普通类（POJO）的方法==，则要创建对应类的==子类代理对象==，通过子类代理对象增强方法的功能

### 3.3 <u>AOP操作</u>

#### 3.3.1 <u>AOP操作术语</u>

* **连接点(Joinpoint)**：可以被增强的方法
* **切入点(Pointcut)**：实际被增强的方法
* **通知、增强(Advice)**：实现增强功能的逻辑部分（增强代码），有5种通知类型
  1. 前置通知：在被增强的方法前执行
  2. 后置通知（返回通知）：在被增强的方法后（方法返回值之后）执行，如果方法出现异常可能不会执行
  3. 环绕通知：在被增强的方法前后都执行
  4. 异常通知：被增强的方法出现异常时执行
  5. 最终通知：在被增强的方法后执行，==类似于`finally`语句==，即不管被增强的方法是否出现异常都会执行
* **切面**：表示一个动作——==将通知应用到切入点上（实现增强功能）==

#### 3.3.2 <u>AOP操作的实现</u>

* **实现原理**

  * **基于AspectJ**：Spring AOP一般都是==基于AspectJ 实现的==

    >  PS：AspectJ 不是Spring的组成部分，实际上是对AOP编程思想的一个实践，

  * **切入点表达式**：在进行AOP的配置时，需要使用切入点表达（作为属性值）

    * 作用：==指定和标识==出需要增强的类和方法

    * **语法格式**：`execution([权限修饰符] [返回类型] [被增强的类的全类名].[被增强的方法名](参数列表));`

      > 1. 权限修饰符一般用 `*`代替，表示全部权限修饰符都符合
      > 2. 返回类型可以省略
      > 3. 参数列表用`..`代替

    * 三种切入点表达式案例（凡是所有都用`*`代替）

      1. 标识出具体的被增强类和被增强方法：`execution(* com.Key.dao.UserDao.add(..));`
      2. 标识出具体的被增强类，类中所有方法都被增强：`execution(* com.Key.dao.UserDao.*(..));`
      3. 只标识出具体的包，包中所有类和类中所有方法都被增强：`execution(* com.Key.dao.*.*(..));`

  * 导入相关jar包：spring-aspects-..jar、com.springsource.net.sf.cglib-..jar、com.springsource.org.aopalliance-..jar、com.springsource.org.aspectj.weaver-...jar

* **具体实现**

  * **注解方式实现（重点）**：实现步骤如下

    1. 创建需要被增强的类和方法

    2. 创建增强类，在类中创建多个方法，通过在方法上添加注解，使每个方法对应不同的通知类型（5种）

       > PS：以上每个注解中都有一个属性`value`，==属性值就是切入点表达式==，通过切入点表达式精确找到被增强类和方法

       * **@Before**：对应前置通知

         ```java
         // 前置通知
         @Before(value = "execution(* com.Key.bean.User.add(..))")
         public void before() {
             System.out.printf("增强方法——前置通知");
         }
         ```

       * **@AfterReturning**：对应后置通知（返回通知）

         ```java
         // 后置通知
         @AfterReturning(value = "execution(* com.Key.bean.User.add(..))")
         public void afterReturning() {
             System.out.printf("增强方法——后置通知");
         }
         ```

       * **@Around**：对应环绕通知

         > PS：环绕通知在被增强方法前后都执行，则需要添加一个形参`ProceedingJoinPoint proceedingJoinPoint`，然后调用该形参的方法——`proceed()`执行被增强的方法

         ```java
         // 环绕通知
         @Around(value = "execution(* com.Key.bean.User.add(..))")
         public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
             System.out.printf("方法前执行");
             
             // 执行被增强的方法
             proceedingJoinPoint.proceed();
             
             System.out.printf("方法后执行");
         }
         ```

       * **@AfterThrowing**：对应异常通知

         ```java
         // 异常通知
         @AfterThrowing(value = "execution(* com.Key.bean.User.add(..))")
         public void afterThrowing() {
             System.out.printf("增强方法——异常通知");
         }
         ```

       * **@After**：对应最终通知

         ```java
         // 最终通知
         @After(value = "execution(* com.Key.bean.User.add(..))")
         public void after() {
             System.out.printf("增强方法——最终通知");
         }
         ```

    3. **实现通知（增强）的配置**

       * 在spring 配置文件中==开启组件扫描==（使用注解的必须操作）

         ```xml
         <context:component-scan base-package="com.Key"></context:component-scan>
         ```

       * 使用注解创建出被增强类对象以及增强类对象：在对应类上添加`@Component`注解即可

         ```java
         // 创建被增强类对象
         @Component(value = "user")
         public class User {
             // 被增强方法
             public void add() {
                 System.out.println("被增强方法...");
             }
         }
         
         /*+----------------------------------------------------+*/
         
         // 创建增强类对象
         @Component(value = "userProxy")
         public class UserProxy {
             // 增强方法(前置通知)
             @Before(value = "execution(* com.Key.bean.User.add(..))")
             public void before() {
                 System.out.printf("增强方法——前置通知");
             }
         }
         ```

       * 使用注解创建代理对象：在增强类上==添加`@Aspect`注解==

         ```java
         // 创建增强类对象并创建出对应的代理对象
         @Component(value = "userProxy")
         @Aspect
         public class UserProxy {
             // 增强方法(前置通知)
             @Before(value = "execution(* com.Key.bean.User.add(..))")
             public void before() {
                 System.out.printf("增强方法——前置通知");
             }
         }
         ```

       * ==开启代理对象==

         1. xml 文件配置：先开启aop 名称空间，再使用\<aop:aspectj-autoproxy>标签

            ```xml
            <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
            ```

         2. 使用注解（完全注解开发）：创建配置类，在配置类上添加`@EnableAspectJAutoProxy`，注解中添加`proxyTargetClass`属性，属性值置为true（默认是false）

            ```java
            @Configuration
            @ComponentScan(basePackage = {"com.Key"})
            @EnableAspectJAutoProxy(proxyTargetClass = true)
            public class SpringConfig {
                // code...
            }
            ```

    4. **抽取公共切入点**

       * 使用：在增强类中创建一个方法`pointCut()`，方法上添加`@Pointcut`注解，注解中添加`value`属性（可省略），属性值就是抽取出来的==公共切入点表达式==，将含有公共切入点表达式的增强方法上的表达式都改成`pointCut()`即可

         ```java
         // 抽取公共切入点
         @Pointcut(value = "execution(* com.Key.bean.User.add(..))")
         public void pointCut() {
             // code...
         }
         
         @After(value = "pointCut()")
         public void after() {
             // code...
         }
         ```

       * 作用：减少冗余的表达式，而且修改表达式时只需要修改一处即可

    5. **设置增强类优先级**：当有多个增强类对同一个类及其方法进行增强时，需要对每个增强类设置优先级

       * 使用：在每个增强类上添加`@Order`注解，注解中添加一个非负数的数值，该==数值越小优先级越高（最小为0）==

         ```java
         // 设置优先级为1的增强类
         @Component("uP")
         @Aspect
         @Order(1)
         public class UserProxy {
             @Before
             public void before() {
                 System.out.println("增强方法");
             }
         }
         ```

  * xml 文件配置（了解即可）：使用步骤如下

    1. 创建被增强类和增强类，以及各自的方法

    2. 在spring 配置文件中创建被增强类对象和增强类对象

       ```xml
       <!-- 创建被增强类对象 -->
       <bean id="user" class="com.Key.bean.User"></bean>
       
       <!-- 创建增强类对象 -->
       <bean id="userProxy" class="com.Key.bean.UserProxy"></bean>
       ```

    3. 在spring 配置文件中配置切入点：需要先引入aop 名称空间

       ```xml
       <!-- 配置aop增强 -->
       <aop:config>
       	<!-- 设置切入点 -->
           <aop:pointcut id="pc" expression="execution(* com.Key.bean.User.add(..))"></aop:pointcut>
           
           <!-- 
       		设置切面（将通知应用到具体切入点上） 
       		 - <aop:aspect> 中ref属性值为增强类的唯一标识id
       		 - <aop:before/> 标签表示前置通知，类似还有<aop:after>等
       		 - <aop:before/> 中的method属性值为对应的增强方法
       		 - <aop:before/> 中的pointcut-ref属性值为切入点的唯一标识id
       	-->
           <aop:aspect ref="userProxy">
           	<aop:before method="before()" pointcut-ref="pc"/>
           </aop:aspect>
       </aop:config>
       ```

***

## 四、<u>JDBCTemplate</u>

### 4.1 <u>JDBCTemplate概述</u>

* 概述：JDBCTemplate是==Spring 对JDBC进行封装==，简化JDBC的使用，更加方便地对数据库进行操作

* 使用步骤

  1. 导入相关jar 包：druid...jar、mysql-connector-java-...jar、spring-jdbc-...jar、spring-tx-...jar、（spring-orm-...jar）

  2. 在spring 配置文件中配置druid数据库连接池，这里使用引入外部属性文件的方式（需要先引入context名称空间），也可以直接把值写上

     ```xml
     <!-- 引入外部属性文件（jdbc.properties） -->
     <context:property-placehoder location="classpath:jdbc.properties"/>
     
     <!-- 配置druid数据库连接池 -->
     <bean id="ds" class="com.alibaba.druid.pool.DriverDataSource">
         <property name="driverClassName" value="${prop.driverClass}"></property>
         <property name="url" value="${prop.url}"></property>
         <property name="username" ref="${prop.username}"></property>
         <property name="password" value="${prop.password}"></property>
     </bean>
     ```

  3. 在spring 配置文件中==创建JdbcTemplate 对象==，并==注入dataSource属性==（jdbcTemplate中一个属性）

     ```xml
     <bean id="jt" class="org.springframework.jdbc.core.JdbcTemplate">
         <!-- 注入jdbcTemplate 属性 -->
     	<property name="dataSource" ref="ds"></property>
     </bean>
     ```

     > PS：<property>标签中的ref属性值为连接池的唯一标识id

  4. 创建配置类，开启组件扫描（或者在spring 配置文件配置）

     ```java
     @Configuration
     @ComponentScan(basePackages = {"com.Key"})
     public class SpringConfig {
         // code...
     }
     ```

  5. 分别创建service 层和dao 层的实现类，在service 层中添加dao类的成员变量，在dao 层中添加jdbcTemplate 成员变量，并使用注解方式创建对象和注入属性

     > PS：下列代码中省略了接口类

     * UserServiceImpl

       ```java
       @Service("uService")
       public class UserServiceImpl implements UserService {
           
           // 添加dao 层成员变量
           @Autowried
           private UserDao userDao;
           
           // code...
       }
       ```

     * UserDaoImpl 

       ```java
       @Repository("uDao")
       public class UserDaoImpl implements UserDao {
           
           // 添加jdbcTemplate 成员变量
           @Autowried
           private JdbcTemplate jt;
           
           // code...
       }
       ```

### 4.2 <u>CRUD的具体实现</u>

* **增删改操作**

  > PS：增删改操作都是类似的，只需改变SQL语句，这里只演示增加操作

  1. 创建带占位符的SQL语句字符串

     ```java
     String sql = "insert into user values(null,?,?)";
     ```

  2. 创建对象数组Object[]，Object[]中的元素值对应SQL语句中每一个占位符

     ```java
     // 假设数据库表对象的实体类为User（外部传进来）
     Object[] args = {user.getName(), user.getAge()};
     ```

  3. 调用jdbcTemplate的方法——`update()`

     > 方法中有两个参数
     >
     > 1. SQL字符串
     > 2. 对象数组，该参数是可变参数，即对象数组的长度是可变的，因为占位符的数量不确定

     ```java
     jt.update(sql, args);
     ```

* **查询操作**

  * 查询返回值为普通类型

    1. 创建SQL语句字符串

       ```java
       String sql = "select count(*) from user";
       ```

    2. 调用jdbcTemplate的方法——`queryForObject()`

       > 方法中有两个参数
       >
       > 1. SQL字符串
       > 2. 返回值的class 对象，如返回值为int型，则该参数为`Integer.class`

       ```java
       jt.queryForObject(sql, Integer.class);
       ```

  * **查询返回值为对象类型**

    1. 创建SQL语句

       ```java
       String sql = "select * from user where id = ?";
       ```

    2. 调用jdbcTemplate的方法——`queryForObject()`

       > 方法中有三个参数
       >
       > 1. SQL字符串
       > 2. **RowMapper<T>**接口对象（泛型T 表示返回对象类型），使用中采用其实现类**BeanPropertyRowMapper<T>**代替即可，用于==将查询到的数据封装到对象中并返回==。创建实现类对象时，需要传入返回对象类型的class对象
       > 3. 可变参数，即占位符对应的对象数组

       ```java
       User u = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
       ```

  * **查询返回值为集合类型**

    1. 创建SQL语句

       ```java
       String sql = "select * from user";
       ```

    2. 调用jdbcTemplate的方法——`query()`

       > 方法中有三个参数
       >
       > 1. SQL字符串
       > 2. **RowMapper<T>**接口对象，与`queryForObject()`中的第二个参数一样
       > 3. 可变参数，即占位符对应的对象数组

       ```java
       List<User> uList = jt.query(sql, new BeanPropertyRowMapper<User>(User.class));
       ```

  * 批量操作

    > PS：批量添加、删除和修改操作都是类似的，这里只演示添加

    1. 创建SQL语句（就是简单的添加语句）

       ```java
       String sql = "insert into user value(null, ?, ?)";
       ```

    2. 调用jdbcTemplate 的方法——`batchUpdate()`

       > 方法中有两个个参数
       >
       > 1. SQL字符串
       > 2. batchArgs——List集合，集合元素为对象数组，每一个==对象数组（集合元素）表示一组添加数据==，数组中的各个元素对应SQL语句中的占位符

       ```java
       // 外部传入的List集合——batchA
       jt.batchUpdate(sql, batchArgs);
       ```

***

## 五、<u>Spring事务管理</u>

### 5.1 <u>事务的简介</u>

* 概述：事务时数据库操作最基本的单元，逻辑上表示一组操作要么都成功，如果出现失败就都失败

  >  典型场景：**银行转账**

* **事务的特点（ACID）**

  1. **原子性(Atomicity)**：事务是最基本的数据库操作，==不可再分==（要么都成功，要么都失败）
  2. **一致性(Consistency)**：事务的==整体保持不变==，比如客户1和客户2两人的总余额为2千，那么他们两人无论怎么相互转账，两人的总额还是2千
  3. **隔离性(Isolation)**：各个事务之间不会相互影响
  4. **持久性(Durability)**：事务一旦提交，事务中的各个==操作都会永久保存下来==，即一旦提交事务，已修改的数据库信息就不能恢复

### 5.2 <u>spring事务管理</u>

> PS：事务管理一般添加到业务逻辑层（service层）

* 底层原理：spring 事务管理底层是==基于spring AOP 完成的==，即AOP又是基于java的动态代理完成的

* spring 进行事务管理的两种方式

  1. 编程式事务管理（一般不采用）：直接在事务的操作中添加`try...catch()...`语句捕获异常，在`try{}`语句块中开始处==开启事务==，最后==提交事务==，在`catch(){}`语句块（出现异常时）中==回滚事务==
  2. **声明式事务管理**：利用spring事务管理相关的API，并使用注解或xml 文件进行配置即可

* spring事务管理的API：spring提供一个接口`PlatformTransactionManager`，作为==事务管理器==，针对不同的框架又有不同的实现类

  * `DataSourceTransactionManager`实现类：适用于jdbc模板（jdbcTemplate）、spring整合Mybatis框架
  * `HibernateTransactionManager`实现类：适用于spring整合Hibernate框架

* **声明式事务管理操作**

  > PS：在进行spring 事务管理前需要先完成jdbcTemplate 的相关配置

  * **注解方式**

    1. 在spring 配置文件中==配置事务管理器==（创建事务管理器对象），并注入dataSource属性

       ```xml
       <bean id="tManager" class="org.springframework.jdbc.dataSource.DataSourceTransactionManager">
       	<!-- 注入数据源dataSource属性 -->
           <property name="dataSource" ref="ds"></property>
       </bean>
       ```

       > PS：<property>标签中的ref属性值为连接池的唯一标识id

    2. 在spring 配置文件中==开启事务注解==：需要先==开启tx 名称空间==，然后使用\<tx:annotation-driven>

       ```xml
       <tx:annotation-driven transaction-manager="tManager"></tx:annotation-driven>
       ```

       > PS：transaction-manager的属性值就是事务管理器对象的唯一标识id

    3. 添加`@Transactional`注解

       * 在类上添加：类中的全部方法都添加事务
       * 在对应的方法上添加：只在该方法上添加事务

       ```java
       // 在类上添加
       @Transactional
       public class UserService {
           // code...
       }
       
       /*+------------------------------------------------+*/
       
       // 在类中对应的方法中添加
       public class UserService {
           
           @Transactional
           public void add() {
               // code...
           }
       }
       ```

    4. 注解参数设置

       1. **设置传播行为**——`propagation`

          * 事务方法：对==数据库数据进行修改==的操作，如添加、修改、删除等

          * 传播行为：==多事务方法之间相互调用==（直接调用）的过程中事务的管理方式，如下例子

            ```java
            // update()方法
            public void update() {
                // code...
            }
            
            /*+---------------------------------------------------+*/
            
            // add()方法中开启事务，并调用update()方法
            @Transactional
            public void add() {
                // 调用update()方法
                update();
                
                // code...
            }
            ```

            > PS：==事务方法不一定要开启事务==（添加`@Transactional`注解），以上述为例，`update()`方法没有开启事务，`add()`方法开启了事务，但两个方法都是事务方法，所以`add()`中调用`update()`就是多事务方法之间的相互调用

          * spring 中事务传播行为：一共有7种，其中有两个比较常用（以上述例子来讲解）

            1. `REQUIRED`
               * 如果`add()`开启了事务，则在调用`update()`方法后，`update()`会==使用当前`add()`的事务==完成操作
               * 如果`add()`没有开启事务，则在调用`update()`方法后，`update()`会==创建一个新的事务来==完成操作
            2. `REQUIRED_NEW`：无论`add()`是否有开启事务，在调用`update()`方法后，都会创建一个新的事务来完成操作

       2. **设置隔离级别**——`isolation`：针对事务的并发操作中遇到的==三种读问题==设置隔离级别

          * 三种读问题：脏读（最严重）、不可重复读、幻读（虚读）

            > PS：三种读问题的解释可参见数据库的学习，这里省略

          * 四种隔离级别以及对应问题的解决

            | 隔离级别                     | 脏读 | 不可重复读 | 幻读 |
            | ---------------------------- | ---- | ---------- | ---- |
            | 读未提交（READ UNCOMMITTED） | 有   | 有         | 有   |
            | 读已提交（READ COMMITTED）   | 无   | 有         | 有   |
            | 重复读（REPEATABLE READ）    | 无   | 无         | 有   |
            | 串行化（SERIALIZABLE）       | 无   | 无         | 无   |

            > PS：Mysql中默认的隔离级别为==重复读（REPEATABLE READ）==

       3. 设置超时时间——`timeout`：==设定事务需要在一定时间内提交==，如果超出这段时间还不进行提交就会回滚

          * 时间的设定：超时时间`timeout`的属性值==以秒为单位==，==默认值为-1==，-1表示没有超时时间（即没有出现异常就正常提交事务，出现异常就回滚事务，而不是超时时间为0）

       4. 设置是否只读——`readOnly`：设置==对数据库数据的操作是否只读==

          * 只读：`readOnly`的属性值设为true，表示只能对数据库的数据进行查询操作
          * 非只读：`readOnly`的属性值设为false，==false是默认值==，表示能对数据库数据进行CRUD操作

       5. 设置是否回滚：对==异常==进行选择性回滚

          * `rollbackFor`：属性值为异常的类对象，表示遇到该异常就进行回滚，如`Exception.class`
          * `noRollbackFor`：属性值为异常的类对象，表示遇到该异常就不进行回滚，如`Exception.class`

    5. 测试类

       ```java
       public class TranTest {
       
           /**
            * 测试不使用事务的转账操作
            */
           @Test
           public void testUpdateMoney() {
               // 创建工厂对象
               ApplicationContext context = new ClassPathXmlApplicationContext("transaction.xml");
       
               // 获取service（注意这里只能是service接口.class）
               CustomService cService = context.getBean("cService", CustomService.class);
       
               // 调用service方法
               cService.updateMoney();
           }
       }
       ```

       > PS：测试类中通过工厂对象获取service对象时，在`getBean()`中==注入的是service类型受事务开启的影响==
       >
       > 1. 没有开启事务：`getBean()`中的参数可以是`service接口.class`，也可以是`service实现类.class`，因为在没有开启事务时，spring会为我们注入==IOC容器中的对应service类型的bean对象==
       > 2. **开启了事务**：`getBean()`中的参数只能是`service接口.class`，因为==spring 管理事务是基于spring AOP==，而AOP又是基于Java的动态代理实现的，由于==存在service接口，spring 会采用JDK动态代理==，即使用Proxy 类来实现动态代理。因此通过`getBean()`获取的是==接口的代理对象，通过这个代理对象来增强方法，实现事务控制==。也就是说，如果开启事务（配置声明式事务），`getBean()`中就只能注入service接口而不能注入service实现类（如果写实现类会报错！）

  * xml 文件配置（从xml 配置中可以明显看出spring 事务管理底层是基于spring AOP）

    1. 配置事务管理器（创建事务管理器对象，与注解方式一样）

       ```xml
       <bean id="tManager" class="org.springframework.jdbc.dataSource.DataSourceTransactionManager">
       	<property name="dataSource" ref="ds"></property>
       </bean>
       ```

    2. 配置通知（advice）

       ```xml
       <!-- 配置通知 -->
       <tx:advice id="txAdvice">
       	<!-- 配置事务管理和事务参数 -->
           <tx:attributes>
               <!-- 在对应方法（可以多个）上开启事务，并设置事务的参数 -->
           	<tx:method name="add" propagation="REQUIRED"/>
               <tx:method name="update*" propagation="REQUIRED"/>
           </tx:attributes>
       </tx:advice>
       ```

       > \<tx:method>标签中的`name`属性：属性值为==指定规则的方法名==，如`update*`就表示==前面带有update的方法都开启事务==

    3. 配置切入点（pointcut）和切面（将通知应用到切入点上）

       ```xml
       <aop:config>
       	<!-- 配置切入点 -->
           <aop:pointcut id="pt" expression="execution(* com.Key.service.UserService.*(..))"/>
           
           <!-- 配置切面 -->
           <aop:advisor advice-ref="txAdvice" pointcut-ref="pt"/>
       </aop:config>
       ```

       > 几个标签中属性说明
       >
       > 1. \<aop:pointcut>中的`expression`属性值为==切入点表达式==，指定要开启事务的方法和类
       > 2. \<aop:advisor>中的`advice-ref`和`pointcut-ref`属性值分别为通知的唯一标识id和切入点的唯一标识id

* **完全注解开发**实现事务管理

  1. 创建配置类——TxConfig

  2. 在配置类上添加相关注解：`@Configuration`（设定为配置类）、`@ComponentScan(basePackages = {"com.Key")`（开启组件扫描）、`@EnableTransactionManagement`（开启事务）

  3. 在配置类中创建各个类对象：添加==每个类的`getXxx()`方法==，返回对应的对象，并在==方法上添加`@Bean`注解==

     > PS：如果需要在某个类的`getXxx()`方法中使用其他类对象，==只需把该对象作为参数传入即可==。因为在配置类中创建了类对应的`getXxx()`方法，spring ==IOC容器中就会存在对应的Bean对象==，所以在方法参数中添加需要使用的类对象，就能在方法中直接使用，而无需在方法中使用new创建（==相当于使用`@Autowired`注解==根据类型创建对象类型的属性）

  ```java
  @Configuration
  @Component(basePackage = {"com.Key"})
  @EnableTransactionManagement
  public class TxConfig {
      
      // 创建数据库连接池对象
      @Bean
      public DruidDataSource getDruidDataSource() {
          // 先new 一个对象
          DruidDataSource ds = new DruidDataSource();
          
          // 引入外部属性文件
          Properties pro = new Properties();
          pro.load(TxConfig.class.getClassLoader().getResourceAsStream("jdbc.properties"));
          
          // 注入外部属性文件中的对应属性值（也可以不采用引入的外部属性文件，直接注入对应属性值）
          ds.setDriverClassName(pro.getProperty("prop.driverClass"));
          ds.setUrl(pro.getProperty("prop.url"));
          ds.setUsername(pro.getProperty("prop.username"));
          ds.setPassword(pro.getProperty("prop.password"));
          
          // 最后返回注入好属性的对象
          return ds;
      }
      
      // 创建jdbcTemplate 对象
      @Bean
      public JdbcTemplate getJdbcTemplate(DruidDataSource ds) {
          // 先new 一个对象
          JdbcTemplate jt = new JdbcTemplate();
          
          // 注入dataSource 属性
          jt.setDataSource(ds);
          
          return jt;
      }
      
      // 创建事务管理器对象
      @Bean
      public DataSourceTransactionManager getDataSourceTransactionManager(DruidDataSource ds) {
          // 先new 一个对象
          DataSourceTransactionManager tManager = new DataSourceTransactionManager();
          
          // 注入dataSource 属性
          tManager.setDataSource(ds);
          
          return tManager;
      }
  }
  ```

