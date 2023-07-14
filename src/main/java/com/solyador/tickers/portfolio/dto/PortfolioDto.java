package com.solyador.tickers.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioDto {

    private UUID id;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String name;

    private String description;
}
