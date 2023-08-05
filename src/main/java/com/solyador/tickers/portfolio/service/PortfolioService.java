package com.solyador.tickers.portfolio.service;

import com.solyador.tickers.portfolio.domain.Portfolio;
import com.solyador.tickers.portfolio.dto.PortfolioDto;
import com.solyador.tickers.portfolio.mapper.PortfolioMapper;
import com.solyador.tickers.portfolio.repository.PortfolioRepository;
import com.solyador.tickers.shared.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PortfolioService {

    private static final String PORTFOLIO_WAS_NOT_FOUND = "Portfolio with id %s was not found";
    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public List<PortfolioDto> findAll() {
        var portfolios = portfolioRepository.findAll();
        return portfolios.stream()
                .map(PortfolioMapper::portfolioToDto)
                .toList();
    }

    public PortfolioDto findOne(UUID id) {
        var portfolio = checkPortfolio(id);
        return PortfolioMapper.portfolioToDto(portfolio);
    }

    public PortfolioDto create(PortfolioDto portfolioDto) {
        var portfolio = PortfolioMapper.dtoToPortfolio(portfolioDto);
        var createdPortfolio = portfolioRepository.save(portfolio);
        return PortfolioMapper.portfolioToDto(createdPortfolio);
    }

    public void update(PortfolioDto portfolioDto) {
        var portfolio = checkPortfolio(portfolioDto.getId());
        portfolio.setDescription(portfolioDto.getDescription());
    }

    public void delete(UUID id) {
        var portfolio = checkPortfolio(id);
        portfolioRepository.delete(portfolio);
    }

    private Portfolio checkPortfolio(UUID id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(PORTFOLIO_WAS_NOT_FOUND.formatted(id)));
    }
}
