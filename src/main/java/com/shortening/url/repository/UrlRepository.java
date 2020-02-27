package com.shortening.url.repository;

import com.shortening.url.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

    /**
     * 원래 URL로 찾기
     * @return
     */
    Url findUrlEntityByOriginUrl(String originUrl);

    /**
     * 단축 URL로 찾기
     * @return
     */
    Url findUrlEntityByShortKey(String shortKey);
}
