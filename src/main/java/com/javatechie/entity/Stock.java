package com.javatechie.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "stocks")
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String symbol;
    private String value;
    private String sector;
    private String industry;

    }
