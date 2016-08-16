package com.xieziming.stap.executor.mock;

import com.xieziming.stap.core.execution.ExecutionRequest;
import com.xieziming.stap.core.execution.ExecutionRequestResult;
import com.xieziming.stap.core.model.execution.dto.ExecutionCandidateDto;
import com.xieziming.stap.core.model.execution.dto.ExecutionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Suny on 8/15/16.ß
 */
@Component
public class RestClient {
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Autowired
    private RestTemplate restTemplate;

    @Value("${channelUrl}")
    private String channelUrl;

    public ExecutionDto getExecutionData(int executionId) {
        return restTemplate.getForObject(channelUrl + "/execution/{executionId}", ExecutionDto.class, executionId);
    }

    public List<ExecutionCandidateDto> getExecutionCandidateList() {
        ParameterizedTypeReference<List<ExecutionCandidateDto>> typeRef = new ParameterizedTypeReference<List<ExecutionCandidateDto>>() {};
        ResponseEntity<List<ExecutionCandidateDto>> responseEntity = restTemplate.exchange(channelUrl + "/execution/distributor/candidate", HttpMethod.GET, null, typeRef);
        List<ExecutionCandidateDto> executionCandidateDtoList = responseEntity.getBody();
        return executionCandidateDtoList;
    }

    public ExecutionRequestResult requestExecution(ExecutionRequest executionRequest) {
        return restTemplate.postForObject(channelUrl + "/execution/distributor/request", executionRequest, ExecutionRequestResult.class);
    }
}
