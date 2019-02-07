package com.wt.test.shardingjdbc.book.entity;

import lombok.Data;

@Data
public class Book {

    private Long id;
    private String name;
    private String isbn;
    private Double price;
    private String author;
    private String category;
}
