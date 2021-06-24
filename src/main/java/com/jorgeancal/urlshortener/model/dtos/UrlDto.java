package com.jorgeancal.urlshortener.model.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class UrlDto {
    private BigDecimal id;
    private String original;
    private String shorter;
    private Timestamp created;
}
