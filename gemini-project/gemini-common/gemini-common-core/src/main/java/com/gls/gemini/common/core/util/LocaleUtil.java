package com.gls.gemini.common.core.util;

import lombok.experimental.UtilityClass;

import java.util.Locale;

/**
 * 语言工具类
 */
@UtilityClass
public class LocaleUtil {
    /**
     * 获取Locale
     *
     * @param locale 语言
     * @return Locale
     */
    public Locale getLocale(String locale) {
        return Locale.forLanguageTag(locale);
    }
}
