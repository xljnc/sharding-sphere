package com.wt.test.shardingjdbc.book.entity;

import lombok.Data;

@Data
public class Book {

    private Integer id;
    private String name;
    private String ISBN;
    private Double price;
    private String author;

}
