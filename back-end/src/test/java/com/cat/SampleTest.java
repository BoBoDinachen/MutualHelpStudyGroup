package com.cat;

import com.cat.mapper.StudentMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * date: 2021/12/21 16:13
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@SpringBootTest
public class SampleTest {
    @Autowired
    private StudentMapper studentMapper;
    @Test
    public void contextLoads() {
    }
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
    }
}
