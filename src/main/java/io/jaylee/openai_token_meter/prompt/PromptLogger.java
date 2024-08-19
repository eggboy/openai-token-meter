package io.jaylee.openai_token_meter.prompt;

public interface PromptLogger {

    void logPrompt(String productId, String eventTime, String requestBody, String responseBody, int completionTokens, int promptTokens, int totalTokens);
}
