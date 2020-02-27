package com.shortening.url.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Url {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String originUrl;

    @Column(unique = true)
    private String shortKey;

    private long requestedCnt;

    public Url(String originUrl, String shortKey) {
        this.originUrl = originUrl;
        this.shortKey = shortKey;
        this.requestedCnt = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public long getRequestedCnt() {
        return requestedCnt;
    }

    public void setRequestedCnt(long requestedCnt) {
        this.requestedCnt = requestedCnt;
    }
}
