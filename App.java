package com.spring.student;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ApplicationContext c = new AnnotationConfigApplicationContext(Config.class);
        Student s= c.getBean(Student.class);
        System.out.println(s);
        
    }
}
