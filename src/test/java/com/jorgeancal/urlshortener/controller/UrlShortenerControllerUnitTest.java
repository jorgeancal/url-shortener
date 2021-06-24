package com.jorgeancal.urlshortener.controller;

import com.jorgeancal.urlshortener.model.dtos.SimpleURLDto;
import com.jorgeancal.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class UrlShortenerControllerUnitTest {

    @Autowired
    UrlShortenerController urlShortenerController;

    @MockBean
    UrlShortenerService urlShortenerService;

    @Test
    void createShortUrlException() throws NoSuchAlgorithmException {

        when(urlShortenerService.createShortUrl("someUrl")).thenThrow(new NoSuchAlgorithmException());

        var response = urlShortenerController.createShortUrl(new SimpleURLDto("someUrl"));

        assertTrue(response.getStatusCode().is5xxServerError());

    }

}

