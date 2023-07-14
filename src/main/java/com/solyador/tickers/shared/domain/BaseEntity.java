package com.solyador.tickers.shared.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    private UUID id;

    protected BaseEntity() {
        this.id = UUID.randomUUID();
    }

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    protected Instant createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    protected Instant modifiedAt;

    @Version
    protected int version;
}
