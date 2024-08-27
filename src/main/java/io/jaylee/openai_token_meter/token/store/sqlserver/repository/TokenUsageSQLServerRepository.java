package io.jaylee.openai_token_meter.token.store.sqlserver.repository;

import io.jaylee.openai_token_meter.token.store.sqlserver.domain.TokenCountUsageSQLServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenUsageSQLServerRepository extends JpaRepository<TokenCountUsageSQLServerEntity, Long> {
}