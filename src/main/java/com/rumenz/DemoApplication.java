package com.rumenz;

import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DemoApplication {
    public static  void main(String[] args) {

        //instanceByServiceLoader();
        //instanceByServiceLoaderFactoryBean();
        // instanceByAutoireCapableBeanFactory();
        //instanceByBeanDefinitionRegistry();



    }

    //4.BeanDefinitionRegistry

    public static void instanceByBeanDefinitionRegistry(){
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

    // 3. AutowireCapableBeanFactory

    public static void instanceByAutoireCapableBeanFactory(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("beans.xml");
        AutowireCapableBeanFactory abf = ac.getAutowireCapableBeanFactory();
        DefaultRumenzFactory bean = abf.createBean(DefaultRumenzFactory.class);
        System.out.println(bean.rumenzFactory());

    }


    // 2.ServiceLoaderFactoryBean
    public static void instanceByServiceLoaderFactoryBean(){
        ClassPathXmlApplicationContext  ca=new ClassPathXmlApplicationContext("beans.xml");
        ServiceLoader bean = ca.getBean("instance-by-serviceLoaderFactoryBean", ServiceLoader.class);
        Iterator iterator = bean.iterator();
        while (iterator.hasNext()){
            RumenzFactory next = (RumenzFactory) iterator.next();
            System.out.println(next.rumenzFactory());

        }

    }
    //1.ServiceLoader
    public static void instanceByServiceLoader(){
        ServiceLoader<RumenzFactory> serviceLoader= ServiceLoader.load(RumenzFactory.class,Thread.currentThread().getContextClassLoader());
        Iterator<RumenzFactory> iterator = serviceLoader.iterator();
        while(iterator.hasNext()){
            RumenzFactory next = iterator.next();
            System.out.println(next.rumenzFactory());
        }
    }

}
