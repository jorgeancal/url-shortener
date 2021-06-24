package com.jorgeancal.urlshortener.controller;


import com.jorgeancal.urlshortener.model.dtos.SimpleURLDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UrlShortenerControllerIntegrationTest {

    @Autowired
    UrlShortenerController urlShortenerController;

    @Test
    void createShortUrl() {
        var simpleUrlDto = new SimpleURLDto("https://start.spring.io");
        var dto = urlShortenerController.createShortUrl(simpleUrlDto);
        Assertions.assertTrue(dto.getStatusCode().is2xxSuccessful());
        assertFalse(Objects.requireNonNull(dto.getBody()).getShorter().isEmpty());
        assertFalse(Objects.requireNonNull(dto.getBody()).getOriginal().isEmpty());
        assertFalse(Objects.requireNonNull(dto.getBody()).getCreated().toString().isEmpty());
        assertNotNull(Objects.requireNonNull(dto.getBody()).getId());
    }
    @Test
    void redirectToOriginal(){
        var response = urlShortenerController.redirectToOriginal("");
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void redirectToOriginal_200(){
        var simpleUrlDto = new SimpleURLDto("https://start.spring.io");
        var encodeResponse = urlShortenerController.createShortUrl(simpleUrlDto);

        String shortUrl = Objects.requireNonNull(encodeResponse.getBody()).getShorter();

        var response = urlShortenerController.redirectToOriginal(shortUrl);

        assertTrue(response.getStatusCode().is3xxRedirection());
        assertEquals("https://start.spring.io", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void redirectToOriginal_500(){
        var response = urlShortenerController.redirectToOriginal("1234");

        assertTrue(response.getStatusCode().is5xxServerError());
    }
}

