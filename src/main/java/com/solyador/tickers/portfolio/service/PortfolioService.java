package com.solyador.tickers.portfolio.service;

import com.solyador.tickers.portfolio.dto.PortfolioDto;
import com.solyador.tickers.portfolio.mapper.PortfolioMapper;
import com.solyador.tickers.portfolio.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PortfolioService {

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
        var portfolio = portfolioRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        return PortfolioMapper.portfolioToDto(portfolio);
    }

    public PortfolioDto create(PortfolioDto portfolioDto) {
        var portfolio = PortfolioMapper.dtoToPortfolio(portfolioDto);
        var createdPortfolio = portfolioRepository.save(portfolio);
        return PortfolioMapper.portfolioToDto(createdPortfolio);
    }

    public void update(PortfolioDto portfolioDto) {
        var portfolio = portfolioRepository.findById(portfolioDto.getId())
                .orElseThrow(() -> new RuntimeException("not found"));
        portfolio.setDescription(portfolioDto.getDescription());
    }

    public void delete(UUID id) {
        var portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        portfolioRepository.delete(portfolio);
    }
}
