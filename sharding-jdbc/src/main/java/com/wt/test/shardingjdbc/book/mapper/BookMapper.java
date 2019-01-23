package com.wt.test.shardingjdbc.book.mapper;


import com.wt.test.shardingjdbc.book.entity.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {

    Book select(Integer id);
}
