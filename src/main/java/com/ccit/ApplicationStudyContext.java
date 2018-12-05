package com.ccit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationStudyContext {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new MyClassPathXmlApplicationContext("classpath:applicationContext.xml");
        applicationContext.start();
    }

}
