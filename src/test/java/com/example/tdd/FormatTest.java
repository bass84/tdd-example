package com.example.tdd;

import org.junit.jupiter.api.Test;

public class FormatTest {

    @Test
    public void test() {
        String responseJsonString = Format.FULL.getResponseJsonString();
        System.out.println(responseJsonString);
    }
}
