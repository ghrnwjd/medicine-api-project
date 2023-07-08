package com.hoyoung.medicineapiproject.service;


import com.hoyoung.medicineapiproject.component.ApiKey;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class InputService {

    private ApiKey apiKey;
    private static final String ENDPOINT = "https://api.openai.com/v1/completions";
    public InputService(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    public String generateText(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey.getChatGPTkey());
        System.out.println(apiKey.getChatGPTkey());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model","text-davinci-003");
        requestBody.put("prompt", prompt);
        requestBody.put("temperature", 0.5f);
        requestBody.put("max_tokens", 100);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);
        return response.toString();
    }

}
