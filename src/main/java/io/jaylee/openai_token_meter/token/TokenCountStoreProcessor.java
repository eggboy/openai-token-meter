package io.jaylee.openai_token_meter.token;


import io.jaylee.openai_token_meter.token.domain.TokenCountStoreDTO;
import io.jaylee.openai_token_meter.token.store.TokenStoreWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCountStoreProcessor {

    private final TokenStoreWriter tokenStoreWriter;

    @Scheduled(fixedRate = 30000)
    public void writeTokenCountToDataStore() {
        LocalDateTime twoMinutesAgo = LocalDateTime.now(ZoneOffset.UTC).minusMinutes(2).truncatedTo(ChronoUnit.MINUTES);

        for (String productId : TokenCountStore.getProductIdList())
            for (String endpoint : TokenCountStore.getEndpointList())
                for (String model : TokenCountStore.getModelList()) {
                    Optional<TokenCountStoreDTO> tokenCount = TokenCountStore.getTokenCount(productId, endpoint, model, twoMinutesAgo);
                    tokenCount.ifPresentOrElse(tokenCountStoreDTO -> {
                        tokenStoreWriter.writeTokenMetrics(tokenCountStoreDTO);
                        TokenCountStore.removeTokenCount(productId, endpoint, model, twoMinutesAgo);
                    }, () -> log.info("No token count found for the given time {} for product id : {}, endpoint : {}, model {}", twoMinutesAgo.toString(), productId, endpoint, model));

                    log.debug("writeTokenCountToDataStore - Product ID: {} Endpoint: {} Model: {} Event Time: {} Token Count: {}", productId, endpoint, model, twoMinutesAgo, tokenCount);

                }
    }
}
