- ServiceLoader 利用JDK里面的反向控制
- ServiceLoaderFactoryBean 
- AutowireCapableBeanFactory#createBean
- BeanDefinitionRegistry#registerBeanDefinition

### RumenzFactory接口和默认实现类DefaultRumenzFactory

---
**RumenzFactory.java**
```java
package com.rumenz;

public interface RumenzFactory {
    //jdk1.8 默认实现
    default   RumenzA rumenzFactory(){
        RumenzA rumenzA = new RumenzA();
        rumenzA.setId("123");
        rumenzA.setName("入门小站");
        return rumenzA;
    }
    default  RumenzA rumenzFactory(String id){
        return new RumenzA(id);
    }
}

```

**DefaultRumenzFactory.java**

```java
package com.rumenz;

public class DefaultRumenzFactory implements RumenzFactory{

}

```
---


## ServiceLoader 利用JDK里面的反向控制

**ServiceLoader.java**

```java

public final class ServiceLoader<S>
    implements Iterable<S>
{
    private static final String PREFIX = "META-INF/services/";
    //....
}
```

> 需要在classpath下创建META-INF/services/目录,在此目录下创建一个文件名为工厂接口的文件(注意不需要后缀),此文件里面放上此接口的全类路径

```
└── resources
    ├── META-INF
    │   └── services
    │       └── com.rumenz.RumenzFactory
    ├── application.properties
    └── beans.xml
    
注意:com.rumenz.RumenzFactory 是个文件
里面的内容为工厂接口:
com.rumenz.DefaultRumenzFactory

```


**调用DemoApplication.java**

```java
package com.rumenz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DemoApplication {
    public static  void main(String[] args) {
        ClassPathXmlApplicationContext  ca=new ClassPathXmlApplicationContext("beans.xml");
        ServiceLoader<RumenzFactory> serviceLoader= ServiceLoader.load(RumenzFactory.class,Thread.currentThread().getContextClassLoader());
        Iterator<RumenzFactory> iterator = serviceLoader.iterator();
        while(iterator.hasNext()){
            RumenzFactory next = iterator.next();
            System.out.println(next.rumenzFactory());
        }
    }
}

```

**输出**

```
RumenzA 无参构造方法
RumenzA{id='123', name='入门小站'}
```

## ServiceLoaderFactoryBean

> 生成一个ServiceLoader的Bean

**beans.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
<bean id="instance-by-serviceLoaderFactoryBean" class="org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean">
  <property name="serviceType" value="com.rumenz.RumenzFactory"></property>
</bean>
</beans>

```

**调用DemoApplication.java**

```java
package com.rumenz;

public class DemoApplication {
    public static  void main(String[] args) {
        ClassPathXmlApplicationContext  ca=new ClassPathXmlApplicationContext("beans.xml");

        ServiceLoader bean = ca.getBean("instance-by-serviceLoaderFactoryBean", ServiceLoader.class);
        Iterator iterator = bean.iterator();
        while (iterator.hasNext()){
            RumenzFactory next = (RumenzFactory) iterator.next();
            System.out.println(next.rumenzFactory());
        }
    }

}

```

**输出**

```
RumenzA 无参构造方法
RumenzA{id='123', name='入门小站'}
```

## AutowireCapableBeanFactory#createBean

**调用DemoApplication.java**

```java
package com.rumenz;

public class DemoApplication {
    public static  void main(String[] args) {
        ApplicationContext ac=new ClassPathXmlApplicationContext("beans.xml");
        AutowireCapableBeanFactory abf = ac.getAutowireCapableBeanFactory();
        DefaultRumenzFactory bean = abf.createBean(DefaultRumenzFactory.class);
        System.out.println(bean.rumenzFactory());
    }
}

```

**输出**

```
RumenzA 无参构造方法
RumenzA{id='123', name='入门小站'}
```

## BeanDefinitionRegistry#registerBeanDefinition

**调用DemoApplication.java**

```java
package com.rumenz;

public class DemoApplication {
    public static  void main(String[] args) {
         AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext();
        ac.register(DemoApplication.class);
        registerBean(ac,"rumenza");

        ac.refresh();
        RumenzA rumenza = (RumenzA) ac.getBean("rumenza");
        System.out.println(rumenza);
        ac.close();
    }
    public static void registerBean(BeanDefinitionRegistry reg,String beanName){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(RumenzA.class);
        reg.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition());

    }
}

```
源码:https://github.com/mifunc/Spring-Bean-special-instantiate



原文: [https://rumenz.com/rumenbiji/Spring-Bean-special-instantiate.html](https://rumenz.com/rumenbiji/Spring-Bean-special-instantiate.html)
