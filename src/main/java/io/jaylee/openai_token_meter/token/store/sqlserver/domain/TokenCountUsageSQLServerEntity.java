package io.jaylee.openai_token_meter.token.store.sqlserver.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "token_count_usage")
public class TokenCountUsageSQLServerEntity {
    @Id
    private String id;
    @NotNull
    private String productId;
    @NotNull
    private String endpoint;
    @NotNull
    private String model;
    @NotNull
    private LocalDateTime eventTime;

    private int promptTokens;
    private int completionTokens;
    private int totalTokens;

    public TokenCountUsageSQLServerEntity(String id, String productId, String endpoint, String model, LocalDateTime eventTime, int promptTokens, int completionTokens, int totalTokens) {
        this.id = id;
        this.productId = productId;
        this.endpoint = endpoint;
        this.model = model;
        this.eventTime = eventTime;
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }
}
