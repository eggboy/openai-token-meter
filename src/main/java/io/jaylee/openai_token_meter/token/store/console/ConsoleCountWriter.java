package io.jaylee.openai_token_meter.token.store.console;

import io.jaylee.openai_token_meter.token.domain.TokenCountStoreDTO;
import io.jaylee.openai_token_meter.token.store.TokenStoreWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsoleCountWriter implements TokenStoreWriter {

    @Override
    public void writeTokenMetrics(TokenCountStoreDTO tokenCountStoreDTO) {
        log.info("ConsoleCountWriter - Product ID: {} Endpoint: {} Model: {} Event Time: {} Prompt Tokens: {} Completion Tokens: {} Total Tokens: {}",
                 tokenCountStoreDTO.getProductId(),
                 tokenCountStoreDTO.getEndpoint(),
                 tokenCountStoreDTO.getModel(),
                 tokenCountStoreDTO.getEventTime(),
                 tokenCountStoreDTO.getPromptTokens(),
                 tokenCountStoreDTO.getCompletionTokens(),
                 tokenCountStoreDTO.getTotalTokens());
    }
}
