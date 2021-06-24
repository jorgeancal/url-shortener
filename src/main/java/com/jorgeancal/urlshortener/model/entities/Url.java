package com.jorgeancal.urlshortener.model.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "urls",indexes = {
        @Index(columnList = "original", unique = true),
        @Index(columnList = "shorter",unique = true)
})
public class Url {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private BigDecimal id;
    @Column(name = "original", nullable = false)
    private String original;
    @Column(name = "shorter", nullable = false)
    private String shorter;
    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private Timestamp created;
}


