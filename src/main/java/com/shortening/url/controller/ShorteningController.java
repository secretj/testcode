package com.shortening.url.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 단축 url 변환 controller
 * @author jiyou.park
 */
@Controller
public class ShorteningController {

    @GetMapping(value = "/home")
    public String home () {
        return "requestForm";
    }

}
