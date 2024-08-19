package io.jaylee.openai_token_meter.token.store.postgres;

import io.jaylee.openai_token_meter.token.domain.TokenCountStoreDTO;
import io.jaylee.openai_token_meter.token.store.TokenStoreWriter;
import io.jaylee.openai_token_meter.token.store.postgres.domain.TokenCountUsageEntity;
import io.jaylee.openai_token_meter.token.store.postgres.repository.TokenUsageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostgresCountWriter implements TokenStoreWriter {

    @Autowired
    private TokenUsageRepository tokenUsageRepository;

    @Override
    public void writeTokenMetrics(TokenCountStoreDTO tokenCountStoreDTO) {
        log.info("Writing token count to Postgres DB: {}", tokenCountStoreDTO);
        tokenUsageRepository.save(convertToEntity(tokenCountStoreDTO));
    }

    // Create a method to convert to TokenCountUsageEntity from TokenCountStoreDTO to store into DB. TokenCountUsageEntity doesn't user Builder pattern.
    private TokenCountUsageEntity convertToEntity(TokenCountStoreDTO tokenCountStoreDTO) {
        return new TokenCountUsageEntity(tokenCountStoreDTO.getProductId(), tokenCountStoreDTO.getEndpoint(), tokenCountStoreDTO.getModel(), tokenCountStoreDTO.getEventTime(), tokenCountStoreDTO.getPromptTokens(), tokenCountStoreDTO.getCompletionTokens(), tokenCountStoreDTO.getTotalTokens());
    }
}