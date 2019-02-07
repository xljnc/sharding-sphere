package com.wt.test.shardingjdbc.book.mapper;


import com.wt.test.shardingjdbc.book.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    Book select(Integer id);

    List<Book> selectAll();

    int insert(Book book);
}
