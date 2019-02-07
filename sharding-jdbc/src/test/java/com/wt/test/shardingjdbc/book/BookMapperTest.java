package com.wt.test.shardingjdbc.book;

import com.wt.test.shardingjdbc.book.entity.Book;
import com.wt.test.shardingjdbc.book.mapper.BookMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Xljnc
 * @date 2019/2/7 23:02
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    public void testInsertBook() {
        Book book = new Book();
        book.setName("Shit Book");
        book.setAuthor("Xljnc");
        book.setIsbn("qiyu-shit-test-002");
        book.setPrice(Double.MAX_VALUE);
        book.setCategory("tb");
        System.out.println("hashcode of tb:" + "tb".hashCode());
        bookMapper.insert(book);
    }
}
