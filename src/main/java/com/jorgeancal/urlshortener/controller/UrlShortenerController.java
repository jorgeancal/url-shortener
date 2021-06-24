package com.jorgeancal.urlshortener.controller;

import com.jorgeancal.urlshortener.model.dtos.SimpleURLDto;
import com.jorgeancal.urlshortener.model.dtos.UrlDto;
import com.jorgeancal.urlshortener.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.NoSuchAlgorithmException;

@RestController
public class UrlShortenerController {
    private static final Logger LOG = LoggerFactory.getLogger(UrlShortenerController.class);

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("v1/create")
    public ResponseEntity<UrlDto> createShortUrl(@RequestBody SimpleURLDto urlSentByClient){
        try {
            return ResponseEntity.ok(urlShortenerService.createShortUrl(urlSentByClient.url()));
        } catch (NoSuchAlgorithmException e) {
            LOG.error("NoSuchAlgorithmException trying to encode the URL");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("v1/{short}")
    public ResponseEntity<UrlDto>  redirectToOriginal(@PathVariable("short") String shorter){
        if (shorter.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UrlDto result = urlShortenerService.retrieveOriginalUrl(shorter);

        try {
            URI original = new URI(result.getOriginal());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(original);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
