package io.jaylee.openai_token_meter.token.event;

import io.jaylee.openai_token_meter.token.TokenCountStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenMetricsEventListener {

    @EventListener
    @Async
    public void handleTokenMetricsEvent(TokenMetricsEvent tokenMetricsEvent) {
        log.info("handleTokenMetricsEvent : {}", tokenMetricsEvent.toString());

        TokenCountStore.addProductId(tokenMetricsEvent.getProductId());
        TokenCountStore.addEndpoint(tokenMetricsEvent.getEndpoint());
        TokenCountStore.addModel(tokenMetricsEvent.getModel());

        TokenCountStore.addTokenCount(
                tokenMetricsEvent.getProductId(),
                tokenMetricsEvent.getEndpoint(),
                tokenMetricsEvent.getModel(),
                tokenMetricsEvent.getEventTime(),
                tokenMetricsEvent.getPromptTokens(),
                tokenMetricsEvent.getCompletionTokens(),
                tokenMetricsEvent.getTotalTokens()
        );
    }
}
