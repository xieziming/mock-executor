package com.xieziming.stap.executor.mock;

import com.xieziming.stap.core.constants.ExecutionResultType;
import com.xieziming.stap.core.constants.ExecutionStatusType;
import com.xieziming.stap.core.constants.LogLevel;
import com.xieziming.stap.core.model.execution.dto.ExecutionDto;
import com.xieziming.stap.core.model.execution.dto.ExecutionStepDto;
import com.xieziming.stap.core.model.execution.pojo.ExecutionLog;
import com.xieziming.stap.core.model.execution.pojo.ExecutionOutputText;
import com.xieziming.stap.core.model.testcase.dto.TestStepDto;
import com.xieziming.stap.core.model.testcase.pojo.TestAction;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * Created by Suny on 8/17/16.
 */

@Component
public class Executor {
    @Autowired
    private RestClient restClient;
    public void execute(ExecutionDto executionDto){
        try {
            restClient.markStartTime(executionDto.getId());
            List<ExecutionStepDto> executionStepDtoList = executionDto.getExecutionStepDtoList();
            ExecutionLog executionLog = new ExecutionLog();
            executionLog.setExecutionId(executionDto.getId());
            executionLog.setLevel(LogLevel.INFO);
            executionLog.setContent("Start to execute test case, total " + executionStepDtoList.size() + " steps.");
            restClient.writeExecutionLog(executionLog);
            for (ExecutionStepDto executionStepDto : executionStepDtoList) {
                executeStep(executionStepDto);
            }
            executionLog.setContent("All test step completed!");
            restClient.writeExecutionLog(executionLog);
            restClient.markExecutionResult(executionDto.getId(), ExecutionResultType.PASS);
        }catch (Exception e){
            ExecutionLog executionLog = new ExecutionLog();
            executionLog.setExecutionId(executionDto.getId());
            executionLog.setLevel(LogLevel.ERROR);
            executionLog.setContent("Exception during execution: " + ExceptionUtils.getFullStackTrace(e));
            restClient.writeExecutionLog(executionLog);
            restClient.markExecutionResult(executionDto.getId(), ExecutionResultType.FAILE);
        }finally {
            restClient.markExecutionStatus(executionDto.getId(), ExecutionStatusType.COMPLETED);
            restClient.markEndTime(executionDto.getId());
        }
    }

    private void executeStep(ExecutionStepDto executionStepDto) throws Exception {
        TestStepDto testStepDto = executionStepDto.getTestStepDto();
        TestAction testAction = testStepDto.getTestAction();
        int stepOrder = testStepDto.getStepOrder();
        String name = testAction.getName();
        String handler = testAction.getHandler();
        String parameter = testStepDto.getParameter();
        ExecutionLog executionLog = new ExecutionLog();
        executionLog.setExecutionStepId(executionStepDto.getId());
        executionLog.setLevel(LogLevel.INFO);
        executionLog.setContent("Start to execute step: "+stepOrder+" , action: "+name+", handler: "+handler+", parameter: "+parameter);

        Thread.sleep(new Random().nextInt(10000));
        if(new Random().nextInt(10) > 6) throw new Exception("mock exception");

        ExecutionOutputText executionOutputText = new ExecutionOutputText();
        executionOutputText.setExecutionStepId(executionStepDto.getId());
        executionOutputText.setType("Trade Information");
        executionOutputText.setField("Amount");
        executionOutputText.setValue(String.valueOf(new Random().nextInt(10000)));
        executionOutputText.setRemark("just a remark");
        restClient.writeExecutionOutputText(executionOutputText);
    }
}
