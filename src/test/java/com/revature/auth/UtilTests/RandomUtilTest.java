package com.revature.auth.UtilTests;

import com.revature.auth.utils.RandomUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomUtilTest {


    @Test
    void generate() {
        String testPassword = RandomUtil.generate(30);

        System.out.println(testPassword);
        Assertions.assertNotNull(testPassword);
        Assertions.assertEquals(30, testPassword.length());
    }
}
