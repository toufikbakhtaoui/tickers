package com.solyador.tickers.portfolio.mapper;

import com.solyador.tickers.portfolio.domain.Portfolio;
import com.solyador.tickers.portfolio.dto.PortfolioDto;
import org.springframework.stereotype.Component;

public class PortfolioMapper {

    private PortfolioMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static PortfolioDto portfolioToDto(Portfolio portfolio) {
        return PortfolioDto.builder()
                .name(portfolio.getName())
                .description(portfolio.getDescription())
                .build();
    }

    public static Portfolio dtoToPortfolio(PortfolioDto portfolioDto) {
        return Portfolio.builder()
                .name(portfolioDto.getName())
                .description(portfolioDto.getDescription())
                .build();
    }
}
