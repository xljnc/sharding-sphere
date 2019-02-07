package com.wt.test.shardingjdbc.mapper;

import com.alibaba.fastjson.JSON;
import com.wt.test.shardingjdbc.book.entity.Book;
import com.wt.test.shardingjdbc.book.mapper.BookMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Xljnc
 * @date 2019/2/5 23:03
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

//    @Autowired
//    private BookMapper bookMapper;

    @Autowired
    @Qualifier("ds0")
    private DataSource dataSource;

//    @Test
//    public void bookTest() {
//        List<Book> bookList = bookMapper.selectAll();
//        System.out.println(JSON.toJSONString(bookList));
//    }

    @Test
    public void dataSourceTest() {
        System.out.println(dataSource);
    }
}
