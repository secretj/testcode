package com.shortening.url.service;

public interface ShorteningService {

    /**
     * 단축 url key를 생성해준다.
     * @param originUrl
     * @return
     */
    String createShorteningKey (String originUrl);

    /**
     * 단축키를 입력받으면 원래 url을 반환해준다.
     * @param shortKey
     * @return
     */
    String getOriginUrl (String shortKey);

    /**
     * 단축키를 입력받으면 요청된 수를 반환해준다.
     * @param shortKey
     * @return
     */
    long getRequestedCnt (String shortKey);

    /**
     * url 존재 여부를 판단해준다.
     * @param originUrl
     * @return
     */
    boolean isUrlValid (String originUrl);


    boolean isShortKeyValid(String shortKey);
}
