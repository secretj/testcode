package com.shortening.url.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;

public class Base62 {
    private static final char[] BASE_WORD_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static final int BASE = 62;

    /**
     * 10진수를 62진수으로 변환 후 글자로 변환하여 반환
     * @param quotient
     * @return String
     */
    public static String encode(long quotient) {
        StringBuilder encodingSb = new StringBuilder(8);
        ArrayList<Integer> baseList = new ArrayList<>(8);

        while (quotient >= BASE) {
            baseList.add((int) (quotient % BASE));
            quotient /= BASE;
        }
        baseList.add((int) quotient);

        int i = baseList.size() - 1;
        for (; i >= 0; i--) {
            encodingSb.append(BASE_WORD_ARR[baseList.get(i)]);
        }
        return encodingSb.toString();
    }

    public static boolean isBase62(String text) {
        if (StringUtils.isEmpty(text)) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return false;
            }
        }
        return true;
    }
}
