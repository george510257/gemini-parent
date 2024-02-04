package com.gls.gemini.starter.mybatis.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gls.gemini.common.core.domain.PageQuery;
import com.gls.gemini.common.core.domain.PageResult;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PageUtil {
    public <T> IPage<T> getPage(PageQuery<?> pageQuery) {
        Page<T> page = Page.of(pageQuery.getPage(), pageQuery.getSize());
        String order = pageQuery.getOrder();
        if ("desc".equalsIgnoreCase(order)) {
            page.addOrder(OrderItem.desc(pageQuery.getSort()));
        } else if ("asc".equalsIgnoreCase(order)) {
            page.addOrder(OrderItem.asc(pageQuery.getSort()));
        }
        return page;
    }

    public <T> PageResult<T> getPageResult(IPage<T> pages) {
        return new PageResult<T>()
                .setPage((int) pages.getCurrent())
                .setSize((int) pages.getSize())
                .setTotal(pages.getTotal())
                .setTotalPage((int) pages.getPages())
                .setRecords(pages.getRecords());
    }
}
