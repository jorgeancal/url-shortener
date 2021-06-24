package com.jorgeancal.urlshortener.repository;

import com.jorgeancal.urlshortener.model.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UrlRepository extends JpaRepository<Url, BigDecimal> {
}
