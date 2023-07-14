package com.solyador.tickers;

import com.solyador.tickers.portfolio.dto.PortfolioDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TickersApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PortfolioControllerIntegrationTests {

    private final String PORTFOLIO_BASE_URL = "/api/v1/portfolios";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAllPortfolios() {
        var response = this.restTemplate.exchange("http://localhost:" + port + PORTFOLIO_BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<PortfolioDto>>() {});
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
