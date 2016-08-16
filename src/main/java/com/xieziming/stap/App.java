package com.xieziming.stap;

import com.xieziming.stap.core.constants.ExecutionStatusType;
import com.xieziming.stap.core.execution.ExecutionRequest;
import com.xieziming.stap.core.execution.ExecutionRequestResult;
import com.xieziming.stap.core.model.execution.dto.ExecutionCandidateDto;
import com.xieziming.stap.executor.mock.RestClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {
    public static void main( String[] args ) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        RestClient restClient = (RestClient)ac.getBean("restClient");
        //ExecutionDto executionDto = restClient.getExecutionData(1);

        List<ExecutionCandidateDto> executionCandidateDtoList = restClient.getExecutionCandidateList();
        System.out.println("there are total "+ executionCandidateDtoList.size());

        ExecutionRequest executionRequest = new ExecutionRequest();
        for(ExecutionCandidateDto executionCandidateDto : executionCandidateDtoList){
            if(!executionCandidateDto.getStatus().equalsIgnoreCase(ExecutionStatusType.INPROGRESS) && !executionCandidateDto.getStatus().equalsIgnoreCase(ExecutionStatusType.COMPLETED) ){
                executionRequest.setExecutionId(executionCandidateDto.getId());
                executionRequest.setExecutor("mock executor");
                executionRequest.setHost("suny's mac");
                System.out.println("Send Request:");
                System.out.println(executionRequest);
                ExecutionRequestResult executionRequestResult = restClient.requestExecution(executionRequest);
                System.out.println("Request result:");
                System.out.println(executionRequestResult);
            }else{
                System.out.println("Execution :"+executionCandidateDto.getTestCaseCandidateDto().getTestCase().getName()+" is :"+executionCandidateDto.getStatus());
            }
        }
    }
}
