package com.tutorialspoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.logging.Logger;

public class MainApp {

    static Logger log = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("Beans.xml");
        log.info("Going to create HelloWord Obj");

        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        obj.getMessage();

        TextEditor te = (TextEditor) context.getBean("textEditor");
        te.spellCheck();

        log.info("Exiting the program");

        Student student = (Student) context.getBean("student");
        student.getName();
        student.getAge();
        student.printThrowException();
    }
}