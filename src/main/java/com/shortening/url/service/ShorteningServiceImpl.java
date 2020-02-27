package com.shortening.url.service;

import com.shortening.url.model.Url;
import com.shortening.url.repository.UrlRepository;
import com.shortening.url.util.Base62;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@Transactional
public class ShorteningServiceImpl implements ShorteningService {

    @Autowired
    private UrlRepository urlRepository;

    private static final int SHORT_URL_MAX_LENGTH = 8;
    private static final AtomicLong SEQ = new AtomicLong(0);
    private static final double MAX_SEQ = Math.pow(Base62.BASE, SHORT_URL_MAX_LENGTH);

    @Override
    public String createShorteningKey(String originUrl) {
        if (SEQ.get() >= MAX_SEQ) {
            return null;
        }
        Url urlEntity = urlRepository.findUrlEntityByOriginUrl(originUrl);
        if (urlEntity != null) {
            return urlEntity.getShortKey();
        }
        SEQ.addAndGet(1);

        String encodeKey = Base62.encode(SEQ.get());
        Url entity = new Url(originUrl, encodeKey);
        urlRepository.save(entity);
        return encodeKey;
    }

    @Override
    public String getOriginUrl(String shortKey) {
        Url urlEntity = urlRepository.findUrlEntityByShortKey(shortKey);
        if (urlEntity == null) {
            return null;
        }
        urlEntity.setRequestedCnt(urlEntity.getRequestedCnt() + 1);
        urlRepository.save(urlEntity);
        return urlEntity.getOriginUrl();
    }

    @Override
    public long getRequestedCnt(String shortKey) {

        Url urlEntity = urlRepository.findUrlEntityByShortKey(shortKey);
        if (urlEntity == null) {
            return -1;
        }
        return urlEntity.getRequestedCnt();
    }

    @Override
    public boolean isUrlValid(String originUrl) {
        if (!StringUtils.isEmpty(originUrl)) {
            try {
                URL url = new URL(originUrl);
                URLConnection connection = url.openConnection();
                if (connection instanceof HttpURLConnection) {
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;

                    httpConnection.setRequestMethod("HEAD");
                    httpConnection.connect();
                    int response = httpConnection.getResponseCode();

                    if (response == 200) {
                        return true;
                    }
                }
            } catch (IOException e) {
                log.error("[ShorteningServiceImpl.isUrlValid] fail to connect originUrl", e);
            }
        }
        return false;
    }

    @Override
    public boolean isShortKeyValid(String shortKey) {
        if (StringUtils.isEmpty(shortKey)) {
            return false;
        }
        if (shortKey.length() > SHORT_URL_MAX_LENGTH) {
            return false;
        }
        return Base62.isBase62(shortKey);
    }
}
