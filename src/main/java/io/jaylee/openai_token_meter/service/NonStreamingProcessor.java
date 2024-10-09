package io.jaylee.openai_token_meter.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jaylee.openai_token_meter.token.event.TokenMetricsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class NonStreamingProcessor {
    private final Gson gson;
    private static final boolean PROMPT_LOGGING_ENABLED = false;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void process(String payload) {
        log.debug(payload);
        JsonObject jsonObject = JsonParser.parseString(payload).getAsJsonObject();
        JsonObject responseBodyJson = JsonParser.parseString(jsonObject.get("ResponseBody").getAsString())
                                                .getAsJsonObject();
        JsonObject usage = responseBodyJson.getAsJsonObject("usage");

        String productId = jsonObject.get("ProductId").getAsString();
        String[] endpointDetails = getEndpointDetails(jsonObject.get("EndpointUrl").getAsString());
        String endpoint = endpointDetails[0];
        String model = endpointDetails[1];

        // EventTime format is "2024-08-12T16:19:22+00:00"
        LocalDateTime eventTime = LocalDateTime.parse(jsonObject.get("EventTime")
                                                                .getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:sszzz"));

        int promptTokens = usage.get("prompt_tokens").getAsInt();
        int completionTokens = usage.get("completion_tokens").getAsInt();
        int totalTokens = usage.get("total_tokens").getAsInt();

        applicationEventPublisher.publishEvent(TokenMetricsEvent.builder().productId(productId)
                                                                .endpoint(endpoint)
                                                                .model(model)
                                                                .eventTime(eventTime)
                                                                .promptTokens(promptTokens)
                                                                .completionTokens(completionTokens)
                                                                .totalTokens(totalTokens)
                                                                .build());
        if (PROMPT_LOGGING_ENABLED) {
            String requestBody = jsonObject.get("RequestBody").getAsString();
            String requestId = jsonObject.get("RequestId").getAsString();
            log.info("Request Id: {}, RequestBody: {}", requestId, requestBody);
        }
    }

    private String[] getEndpointDetails(String urlString) {
        try {
            URI uri = new URI(urlString);
            String hostname = uri.getHost();
            String[] tokens = Arrays.stream(uri.getPath().split("/"))
                    .filter(Objects::nonNull)
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
            String modelName = tokens[Arrays.asList(tokens).indexOf("deployments") + 1];
            
            return new String[]{hostname, modelName};
        } catch (URISyntaxException e) {
            log.error("Invalid URI syntax: {}", urlString, e);
            return new String[]{"", ""};
        }
    }
}