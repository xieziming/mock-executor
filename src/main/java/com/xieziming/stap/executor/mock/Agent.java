package com.xieziming.stap.executor.mock;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Suny on 8/17/16.
 */
public class Agent {
    public static void main(String[] args ) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ExecutionManager executionManager = (ExecutionManager) ac.getBean("executionManager");
        executionManager.keepExecute();
    }
}
