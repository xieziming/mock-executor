package com.xieziming.stap.executor.mock;

import com.xieziming.stap.core.constants.ExecutionStatusType;
import com.xieziming.stap.core.execution.ExecutionRequest;
import com.xieziming.stap.core.execution.ExecutionRequestResult;
import com.xieziming.stap.core.model.execution.dto.ExecutionCandidateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Suny on 8/17/16.
 */
@Component
public class ExecutionManager {
    @Autowired
    private RestClient restClient;
    @Autowired
    private Executor executor;

    public void execute(){
        List<ExecutionCandidateDto> executionCandidateDtoList = restClient.getExecutionCandidateList();
        for(ExecutionCandidateDto executionCandidateDto : executionCandidateDtoList){
            if(!executionCandidateDto.getStatus().equalsIgnoreCase(ExecutionStatusType.INPROGRESS) && !executionCandidateDto.getStatus().equalsIgnoreCase(ExecutionStatusType.COMPLETED) ){
                ExecutionRequest executionRequest = ExecutionRequstFactory.create(executionCandidateDto.getId());
                int executionId = executionCandidateDto.getId();
                String testCaseName = executionCandidateDto.getTestCaseCandidateDto().getTestCase().getName();
                System.out.println("Request to execute "+testCaseName+" with id: "+executionId);
                ExecutionRequestResult executionRequestResult = restClient.requestExecution(executionRequest);
                if(executionRequestResult.getApproved()) {
                    System.out.println("Request approved.");
                    executor.execute(executionRequestResult.getExecutionDto());
                }else{
                    System.out.println("Request reject with reason: "+executionRequestResult.getRemark());
                }
            }
        }
    }

    public void keepExecute(){
        while (true){
            execute();
            try {
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
