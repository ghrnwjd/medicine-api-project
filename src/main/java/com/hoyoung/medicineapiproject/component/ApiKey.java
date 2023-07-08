package com.hoyoung.medicineapiproject.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApiKey {

    @Value("${api.medicine.encoding-key}")
    private String medicineEncodingKey;

    @Value("${api.medicine.decoding-key}")
    private String medicineDecodingKey;

    @Value("${api.chatGPT.key}")
    private String chatGPTkey;
}

