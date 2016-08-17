package com.xieziming.stap.executor.mock;

import com.xieziming.stap.core.execution.ExecutionRequest;

/**
 * Created by Suny on 8/17/16.
 */
public class ExecutionRequstFactory {

    public static ExecutionRequest create(int executionId){
        ExecutionRequest executionRequest = new ExecutionRequest();
        executionRequest.setExecutionId(executionId);
        executionRequest.setExecutor(ExecutorContext.getExecutor());
        executionRequest.setHost(ExecutorContext.getHostName());
        return executionRequest;
    }
}
