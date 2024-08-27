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
    private static final String SEQUENCE = "token_count_usage_id_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE, allocationSize = 50)
    private Long id;
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

    public TokenCountUsageSQLServerEntity(String productId, String endpoint, String model, LocalDateTime eventTime, int promptTokens, int completionTokens, int totalTokens) {
        this.productId = productId;
        this.endpoint = endpoint;
        this.model = model;
        this.eventTime = eventTime;
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }
}
