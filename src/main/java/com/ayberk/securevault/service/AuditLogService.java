package com.ayberk.securevault.service;

import com.ayberk.securevault.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void logEvent(String message){
        System.out.println("Kafkaya giden log ---->"+message);

        kafkaTemplate.send(AppConstants.LOG_TOPIC, message);
    }
}
