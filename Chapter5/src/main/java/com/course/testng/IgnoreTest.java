package com.course.testng;

import org.testng.annotations.Test;

public class IgnoreTest {
    @Test
    public void Ingnore1(){
        System.out.println("ignore1 执行");
    }

    @Test(enabled = false)
    public void Ingnore2(){
        System.out.println("ignore2 执行");
    }
    @Test(enabled = true)
    public void Ignore3(){
        System.out.println("ignore3 执行");
    }
}
