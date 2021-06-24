package com.jorgeancal.urlshortener.service;

import com.jorgeancal.urlshortener.model.dtos.UrlDto;

import java.security.NoSuchAlgorithmException;

public interface UrlShortenerService {
    UrlDto createShortUrl(String url) throws NoSuchAlgorithmException;

    UrlDto retrieveOriginalUrl(String url);
}
