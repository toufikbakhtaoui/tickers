package com.solyador.tickers.portfolio.domain;

import com.solyador.tickers.shared.domain.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Portfolio extends BaseEntity {
    private String name;
    private String description;
}
