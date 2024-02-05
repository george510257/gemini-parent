package com.gls.gemini.starter.json.support;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultDateFormat extends SimpleDateFormat {


    public DefaultDateFormat() {
        super(DatePattern.NORM_DATETIME_PATTERN);
    }

    @Override
    public Date parse(String text, ParsePosition pos) {
        return DateUtil.parse(text);
    }

    @Override
    public Date parse(String source) throws ParseException {
        return DateUtil.parse(source);
    }
}
