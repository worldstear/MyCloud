package com.example.bootstrap.test;

import org.junit.Test;

public class Test1 {
    @Test
    public void test1(){
        String originalFilename = "abc.txt";
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println(suffix);
    }
}
