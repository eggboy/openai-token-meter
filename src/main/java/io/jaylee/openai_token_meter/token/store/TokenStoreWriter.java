package io.jaylee.openai_token_meter.token.store;

import io.jaylee.openai_token_meter.token.domain.TokenCountStoreDTO;


public interface TokenStoreWriter {
    void writeTokenMetrics(TokenCountStoreDTO tokenCountStoreDTO);
}