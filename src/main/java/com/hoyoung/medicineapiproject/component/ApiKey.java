package com.hoyoung.medicineapiproject.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApiKey {

    @Value("${medicine.api.encoding-key}")
    private String encodingKey;

    @Value("${medicine.api.decoding-key}")
    private String decodingKey;

}

