package io.jaylee.openai_token_meter.token;

import io.jaylee.openai_token_meter.token.domain.TokenCountStoreDTO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
public class TokenCountStore {
    private static final ConcurrentMap<String, String> modelMap = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, String> endpointMap = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, String> productMap = new ConcurrentHashMap<>();
    private static final ConcurrentMap<TokenCountMapKey, TokenCountMapValue> tokenUsageMap = new ConcurrentHashMap<>();

    public static void addModel(String model) {
        modelMap.put(model, model);
    }

    public static List<String> getModelList() {
        return modelMap.values()
                .stream().toList();
    }

    public static void addEndpoint(String endpoint) {
        endpointMap.put(endpoint, endpoint);
    }

    public static List<String> getEndpointList() {
        return endpointMap.values()
                .stream().toList();
    }

    public static void addProductId(String productId) {
        productMap.put(productId, productId);
    }

    public static List<String> getProductIdList() {
        return productMap.values()
                .stream().toList();
    }

    public static void addTokenCount(String productId, String endpoint, String model, LocalDateTime eventTime, int promptTokens, int completionTokens, int totalTokens) {
        String normalizedEventTime = eventTime.truncatedTo(ChronoUnit.MINUTES).toString();
        TokenCountMapKey key = new TokenCountMapKey(productId, endpoint, model, normalizedEventTime);

        tokenUsageMap.merge(key, new TokenCountMapValue(promptTokens, completionTokens, totalTokens), (existingValue, newValue) -> {
            existingValue.setPromptTokens(existingValue.getPromptTokens() + newValue.getPromptTokens());
            existingValue.setCompletionTokens(existingValue.getCompletionTokens() + newValue.getCompletionTokens());
            existingValue.setTokenCount(existingValue.getTokenCount() + newValue.getTokenCount());
            return existingValue;
        });
    }

    public static Optional<TokenCountStoreDTO> getTokenCount(String productId, String endpoint, String model, LocalDateTime eventTime) {
        printTokenUsageMap();
        TokenCountMapKey key = new TokenCountMapKey(productId, endpoint, model, eventTime.toString());
        TokenCountMapValue tokenCountMapValue = tokenUsageMap.get(key);

        if (tokenCountMapValue == null) {
            return Optional.empty();
        }

        return Optional.of(new TokenCountStoreDTO(productId, endpoint, model, eventTime, tokenCountMapValue.getPromptTokens(), tokenCountMapValue.getCompletionTokens(), tokenCountMapValue.getTokenCount()));
    }

    public static void removeTokenCount(String productId, String endpoint, String model, LocalDateTime eventTime) {
        TokenCountMapKey key = new TokenCountMapKey(productId, endpoint, model, eventTime.truncatedTo(ChronoUnit.MINUTES)
                                                                                         .toString());
        tokenUsageMap.remove(key);
    }

    public static void printTokenUsageMap() {
        tokenUsageMap.forEach((key, value) -> log.info(key + " : " + value));
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
class TokenCountMapKey {
    private String productId;
    private String endpoint;
    private String model;
    private String eventTime;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
class TokenCountMapValue {
    private int promptTokens;
    private int completionTokens;
    private int tokenCount;
}