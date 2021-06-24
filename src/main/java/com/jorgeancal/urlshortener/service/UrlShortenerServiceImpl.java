package com.jorgeancal.urlshortener.service;

import com.jorgeancal.urlshortener.model.dtos.UrlDto;
import com.jorgeancal.urlshortener.model.entities.Url;
import com.jorgeancal.urlshortener.repository.UrlRepository;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Optional;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    final UrlRepository urlRepository;

    public UrlShortenerServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public UrlDto createShortUrl(String url) throws NoSuchAlgorithmException {
        var savedUrl = new UrlDto();
        var newUrl = new Url();

        newUrl.setOriginal(url);
        // checking if the URL does exits
        var checkingurl = urlRepository.findOne(Example.of(newUrl));

        if (checkingurl.isPresent()){
            BeanUtils.copyProperties(checkingurl.get(), savedUrl);
            return savedUrl;
        }
        newUrl.setShorter(encode(url));
        newUrl = urlRepository.save(newUrl);

        BeanUtils.copyProperties(newUrl, savedUrl);

        return savedUrl;
    }

    @Override
    public UrlDto retrieveOriginalUrl(String url) {
        var exampleUrl = new Url();
        exampleUrl.setShorter(url);
        Optional<Url> urlFromRepo = urlRepository.findOne(Example.of(exampleUrl));
        if (urlFromRepo.isEmpty()) {
            return null;
        }

        var theUrl = new UrlDto();
        BeanUtils.copyProperties(urlFromRepo.get(),theUrl);
        return theUrl;
    }

    /**
     *
     * @param url -> This is the string that we are going to encode
     * @return it is going to return the url enconde
     */
    private String encode(String url) throws NoSuchAlgorithmException {
        var md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(url.getBytes(StandardCharsets.UTF_8));
        return HexUtils.toHexString(md.digest()).toUpperCase(Locale.ROOT);
    }
}
