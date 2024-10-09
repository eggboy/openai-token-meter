package io.jaylee.openai_token_meter.token.store.sqlserver;

import io.jaylee.openai_token_meter.token.domain.TokenCountStoreDTO;
import io.jaylee.openai_token_meter.token.store.TokenStoreWriter;
import io.jaylee.openai_token_meter.token.store.sqlserver.domain.TokenCountUsageSQLServerEntity;
import io.jaylee.openai_token_meter.token.store.sqlserver.repository.TokenUsageSQLServerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class SQLServerCountWriter implements TokenStoreWriter {

    @Autowired
    private TokenUsageSQLServerRepository tokenUsageSQLServerRepository;

    @Override
    public void writeTokenMetrics(TokenCountStoreDTO tokenCountStoreDTO) {
        log.info("Writing token count to SQL Server DB: {}", tokenCountStoreDTO);
        tokenUsageSQLServerRepository.save(convertToEntity(tokenCountStoreDTO));
    }

    private TokenCountUsageSQLServerEntity convertToEntity(TokenCountStoreDTO tokenCountStoreDTO) {
        UUID uuid = UUID.randomUUID();

        return new TokenCountUsageSQLServerEntity(uuid.toString(), tokenCountStoreDTO.getProductId(), tokenCountStoreDTO.getEndpoint(), tokenCountStoreDTO.getModel(), tokenCountStoreDTO.getEventTime(), tokenCountStoreDTO.getPromptTokens(), tokenCountStoreDTO.getCompletionTokens(), tokenCountStoreDTO.getTotalTokens());
    }
}