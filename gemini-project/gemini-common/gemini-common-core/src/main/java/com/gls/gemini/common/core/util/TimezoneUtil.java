package com.gls.gemini.common.core.util;

import lombok.experimental.UtilityClass;

import java.util.TimeZone;

/**
 * 时区工具类
 */
@UtilityClass
public class TimezoneUtil {
    /**
     * 获取时区
     *
     * @param timezone 时区
     * @return 时区
     */
    public TimeZone getTimezone(String timezone) {
        return TimeZone.getTimeZone(timezone);
    }
}
