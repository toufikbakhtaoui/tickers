package com.solyador.tickers.portfolio.controller;

import com.solyador.tickers.portfolio.dto.PortfolioDto;
import com.solyador.tickers.portfolio.service.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/api/v1/portfolios"}, produces = APPLICATION_JSON_VALUE)
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public ResponseEntity<List<PortfolioDto>> getAllPortfolios() {
        return ResponseEntity.ok(portfolioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDto> getPortfolioById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(portfolioService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<PortfolioDto> createPortfolio(@RequestBody @Valid PortfolioDto portfolio) {
        var createdPortfolio = portfolioService.create(portfolio);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdPortfolio);
    }

    @PutMapping
    public ResponseEntity<PortfolioDto> updatePortfolio(@RequestBody PortfolioDto portfolio) {
        portfolioService.update(portfolio);
        return ResponseEntity.status(HttpStatus.OK).body(portfolio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePortfolio(@PathVariable("id") UUID id) {
        portfolioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Portfolio deleted successfully");
    }
}
