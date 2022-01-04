package com.shortening.url.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ShorteningServiceImplTest {

    @Autowired
    ShorteningServiceImpl shorteningService;

    @Test
    public void createShorteningUrlTest() {

        String correctUrl = "http://hello";
        String result = shorteningService.createShorteningKey(correctUrl);
        assertNotNull(result);
    }
    @Test
    public void getOriginUrlTest() {
        String originUrl = shorteningService.getOriginUrl("1");
        assertNotNull(originUrl);
        assertEquals("http://hello", originUrl);
    }

    @Test
    public void getRequestedCntTest() {
        long cnt = shorteningService.getRequestedCnt("1");
        assertEquals(0,cnt);
    }

    @Test
    public void isUrlValidTest() {
        String spaceExist = "https://www.naver.com";
        String invalidWord = "https://www.musinsa.com/!";
        String noHttps = "www.musinsa.com";
        String httpInvalid = "http://www.musinsa.com";
        String musinsaPass = "https://www.musinsa.com/";
        String gitPass = "https://github.com/jiyouchloe";

        String[] falseArr = {spaceExist,invalidWord,noHttps,httpInvalid};
        String[] trueArr = {musinsaPass, gitPass};
        for (int i = 0; i < falseArr.length; i++) {
            assertEquals(false, shorteningService.isUrlValid(falseArr[i]));
        }
        for (int i = 0; i < trueArr.length; i++) {
            assertEquals(true, shorteningService.isUrlValid(trueArr[i]));
        }
    }

    @Test
    public void isShortKeyValidTest() {
        String spaceExist = "hel lo";
        String invalidWord = "!1234";
        String overMaxLength = "123456789";
        String emptyWord = "";
        String numPass = "12345678";
        String wordNumPass = "12345jy";
        String shortPass = "pjy";

        String[] shortKeyFalseArr = {spaceExist,invalidWord,overMaxLength,emptyWord};
        String[] shortKeyTrueArr = {numPass,wordNumPass,shortPass};
        for (int i = 0; i < shortKeyFalseArr.length; i++) {
            assertEquals(false, shorteningService.isShortKeyValid(shortKeyFalseArr[i]));
        }
        for (int i = 0; i < shortKeyTrueArr.length; i++) {
            assertEquals(true, shorteningService.isShortKeyValid(shortKeyTrueArr[i]));
        }
    }
}
