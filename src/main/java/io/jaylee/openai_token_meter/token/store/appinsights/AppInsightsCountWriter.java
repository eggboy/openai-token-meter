package io.jaylee.openai_token_meter.token.store.appinsights;

import io.jaylee.openai_token_meter.token.domain.TokenCountStoreDTO;
import io.jaylee.openai_token_meter.token.store.TokenStoreWriter;
import org.springframework.stereotype.Component;

@Component
public class AppInsightsCountWriter implements TokenStoreWriter {
    @Override
    public void writeTokenMetrics(TokenCountStoreDTO tokenCountStoreDTO) {
    }
}
