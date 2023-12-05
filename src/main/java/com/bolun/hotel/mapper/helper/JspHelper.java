package com.bolun.hotel.mapper.helper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {

    private static final String PREFIX = "/WEB-INF/jsp";
    private static final String SUFFIX = ".jsp";

    public String getPath(String value) {
        return PREFIX + value + SUFFIX;
    }
}
