package io.jaylee.openai_token_meter.token.store.postgres.repository;

import io.jaylee.openai_token_meter.token.store.postgres.domain.TokenCountUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenUsageRepository extends JpaRepository<TokenCountUsageEntity, Long> {
}