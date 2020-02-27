package com.shortening.url.controller;

import com.shortening.url.service.ShorteningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 단축 url 변환 rest controller
 * @author jiyou.park
 */
@RestController
public class ShorteningRestController {

    @Autowired
    private ShorteningService shorteningService;

    @Autowired
    private MessageSource messageSource;

    private static final String STATUS = "status";
    private static final String MESSAGE = "message";

    /**
     * 화면에서 url을 입력 받은 후 단축 url key를 반환해준다.
     * @param originUrl
     * @return
     */
    @PostMapping(value = "/shortening/url")
    public ResponseEntity<?> shorteningUrl (String originUrl) {
        Map<String, String> retMap = new HashMap<>();
        if (StringUtils.isEmpty(originUrl)) {
            retMap.put("message",  messageSource.getMessage("error.URL_IS_EMPTY", null, Locale.KOREA));
            return ResponseEntity.badRequest().body(retMap);
        }

        if (!shorteningService.isUrlValid(originUrl)) {
            retMap.put("message",  messageSource.getMessage("error.URL_IS_NOT_EXIST", null, Locale.KOREA));
            return ResponseEntity.badRequest().body(retMap);
        }

        String shortKey = shorteningService.createShorteningKey(originUrl);
        if (shortKey == null) {
            retMap.put("message",  messageSource.getMessage("error.URL_BUCKET_IS_FULL", null, Locale.KOREA));
            return ResponseEntity.badRequest().body(retMap);
        }

        retMap.put("shortKey", shortKey);
        return ResponseEntity.ok(retMap);
    }

    /**
     * 단축 url을 입력 받으면 redirect해준다.
     * @param shortKey
     * @return
     */
    @GetMapping(value = "/{shortKey}")
    public ResponseEntity<?> redirectOriginPage (@PathVariable String shortKey) throws URISyntaxException {
        Map<String, String> retMap = new HashMap<>();

        if (!shorteningService.isShortKeyValid(shortKey)) {
            retMap.put("message",  messageSource.getMessage("error.SHORT_URL_IS__NOT_VALID", null, Locale.KOREA));
            return ResponseEntity.badRequest().body(retMap);
        }
        String originUrl = shorteningService.getOriginUrl(shortKey);

        if (originUrl == null) {
            retMap.put("message",  messageSource.getMessage("error.SHORT_URL_IS_NOT_EXIST", null, Locale.KOREA));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retMap);
        }

        URI redirectUri = new URI(originUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    /**
     * 단축키를 입력받으면 해당 단축url의 총 요청수를 반환해준다.
     * @param shortKey
     * @return
     */
    @GetMapping(value = "/info/request/cnt/{shortKey}")
    public ResponseEntity<Object> getRequestedCnt (@PathVariable String shortKey) {
        Map<String, String> retMap = new HashMap<>();

        if (!shorteningService.isShortKeyValid(shortKey)) {
            retMap.put("message",  messageSource.getMessage("error.SHORT_URL_IS__NOT_VALID", null, Locale.KOREA));
            return ResponseEntity.badRequest().body(retMap);
        }
        
        long requestedCnt = shorteningService.getRequestedCnt(shortKey);
        if (requestedCnt == -1) {
            retMap.put("message",  messageSource.getMessage("error.SHORT_URL_IS_NOT_EXIST", null, Locale.KOREA));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retMap);
        }

        retMap.put("requestedCnt", String.valueOf(requestedCnt));
        return ResponseEntity.ok(retMap);
    }

}
