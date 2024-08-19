package io.jaylee.openai_token_meter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OpenAITokenMeterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenAITokenMeterApplication.class, args);
    }

}
