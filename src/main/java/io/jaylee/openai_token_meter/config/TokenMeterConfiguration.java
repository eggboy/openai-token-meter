package io.jaylee.openai_token_meter.config;

import com.fasterxml.jackson.core.JsonFactory;
import io.jaylee.openai_token_meter.token.store.TokenStoreWriter;
import io.jaylee.openai_token_meter.token.store.postgres.PostgresCountWriter;
import io.jaylee.openai_token_meter.token.store.sqlserver.SQLServerCountWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TokenMeterConfiguration {

    @Bean
    @Primary
    public TokenStoreWriter getTokenCountWriter() {
        return new SQLServerCountWriter();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return new JsonFactory();
    }
}
