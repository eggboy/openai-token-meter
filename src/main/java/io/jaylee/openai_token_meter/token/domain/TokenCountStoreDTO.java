package io.jaylee.openai_token_meter.token.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TokenCountStoreDTO {
    private String productId;
    private String endpoint;
    private String model;
    private LocalDateTime eventTime;
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;
}
