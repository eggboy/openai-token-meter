package io.jaylee.openai_token_meter.token.store.postgres.repository;

import io.jaylee.openai_token_meter.token.store.postgres.domain.TokenCountUsagePostgresEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenUsagePostgresRepository extends JpaRepository<TokenCountUsagePostgresEntity, Long> {
}