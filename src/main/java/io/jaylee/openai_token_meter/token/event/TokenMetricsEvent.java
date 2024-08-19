package io.jaylee.openai_token_meter.token.event;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class TokenMetricsEvent {
    private String productId;
    private String endpoint;
    private String model;
    private LocalDateTime eventTime; //2024-08-12T11:04:00
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;
}
