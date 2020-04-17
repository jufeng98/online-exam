package org.javamaster.b2c.core;

import org.javamaster.mybatis.generator.MySqlMybatisGenerator;
import org.junit.Test;

/**
 * @author yudong
 * @date 2020/4/17
 */
public class MybatisGeneratorTest {

    @Test
    public void generate() throws Exception {
        MySqlMybatisGenerator.generator();
    }

}
