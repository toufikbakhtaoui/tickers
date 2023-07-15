package com.solyador.tickers;

import com.solyador.tickers.portfolio.dto.PortfolioDto;
import com.solyador.tickers.portfolio.mapper.PortfolioMapper;
import com.solyador.tickers.portfolio.repository.PortfolioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TickersApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PortfolioControllerIntegrationTests {

    private final String PORTFOLIO_BASE_URL = "/api/v1/portfolios";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PortfolioRepository portfolioRepository;


    @Test
    @Sql(scripts = {"/clean.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testAllPortfolios() {
        //given
        var portfolios = portfolioRepository.findAll();
        assertEquals(2, portfolios.size());

        //when
        var response = this.restTemplate.exchange("http://localhost:" + port + PORTFOLIO_BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<PortfolioDto>>() {});

        //then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testAddPortfolio() {
        //given
        var portfolioDto = PortfolioDto.builder()
                .name("name")
                .description("description")
                .build();

        //when
        ResponseEntity<PortfolioDto> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + PORTFOLIO_BASE_URL, portfolioDto, PortfolioDto.class);

        //then
        assertEquals(201, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals("name", responseEntity.getBody().getName());
        assertEquals("description", responseEntity.getBody().getDescription());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testDeletePortfolio() {
        //given
        var portfolio = portfolioRepository.findAll().get(0);
        var portfolioDto = PortfolioMapper.portfolioToDto(portfolio);
        var portfolioId = portfolioDto.getId();

        //when
        ResponseEntity<String> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + PORTFOLIO_BASE_URL + "/" + portfolioId, HttpMethod.DELETE, null, String.class);

        //then
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals("Portfolio with ID "+ portfolioId + " is deleted.", responseEntity.getBody());
    }

    @Test
    @Sql(scripts = {"/clean.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testUpdatePortfolio() {
        //given
        var portfolio = portfolioRepository.findAll().get(0);
        var portfolioDto = PortfolioMapper.portfolioToDto(portfolio);
        portfolioDto.setName("new name");
        portfolioDto.setDescription("new description");

        var requestEntity = new HttpEntity<>(portfolioDto, null);

        //when
        ResponseEntity<PortfolioDto> responseEntity = this.restTemplate
                .exchange("http://localhost:" + port + PORTFOLIO_BASE_URL, HttpMethod.PUT, requestEntity, PortfolioDto.class);

        //then
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals("new name", responseEntity.getBody().getName());
        assertEquals("new description", responseEntity.getBody().getDescription());
    }
}
