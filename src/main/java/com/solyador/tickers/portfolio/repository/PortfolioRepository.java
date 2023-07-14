package com.solyador.tickers.portfolio.repository;

import com.solyador.tickers.portfolio.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
}
