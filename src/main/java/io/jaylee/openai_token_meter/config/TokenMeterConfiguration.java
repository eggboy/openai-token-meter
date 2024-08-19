package io.jaylee.openai_token_meter.config;

import com.fasterxml.jackson.core.JsonFactory;
import io.jaylee.openai_token_meter.token.store.TokenStoreWriter;
import io.jaylee.openai_token_meter.token.store.postgres.PostgresCountWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TokenMeterConfiguration {

    @Bean
    @Primary
    public TokenStoreWriter getTokenCountWriter() {
        return new PostgresCountWriter();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return new JsonFactory();
    }
}
