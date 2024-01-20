package com.gls.gemini.starter.json.support;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DefaultDateFormat extends SimpleDateFormat {

    public DefaultDateFormat() {
        super(DatePattern.NORM_DATETIME_PATTERN);
    }

    public DefaultDateFormat(String pattern) {
        super(pattern);
    }

    public DefaultDateFormat(String pattern, Locale locale) {
        super(pattern, locale);
    }

    public DefaultDateFormat(String pattern, DateFormatSymbols formatSymbols) {
        super(pattern, formatSymbols);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        try {
            return super.parse(source, pos);
        } catch (Exception e) {
            return DateUtil.parse(source).toJdkDate();
        }
    }
}
