package com.gls.gemini.starter.data.jpa.util;

import com.gls.gemini.common.core.domain.PageQuery;
import com.gls.gemini.common.core.domain.PageResult;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 分页工具类
 */
@UtilityClass
public class PageUtil {

    /**
     * 获取分页参数
     *
     * @param pageQuery 分页查询
     * @return 分页参数
     */
    public Pageable getPageable(PageQuery<?> pageQuery) {
        // 排序
        String order = pageQuery.getOrder();
        if ("desc".equalsIgnoreCase(order)) {
            return PageRequest.of(pageQuery.getPage(), pageQuery.getSize(), Sort.Direction.DESC, pageQuery.getSort());
        }
        if ("asc".equalsIgnoreCase(order)) {
            return PageRequest.of(pageQuery.getPage(), pageQuery.getSize(), Sort.Direction.ASC, pageQuery.getSort());
        }
        return PageRequest.of(pageQuery.getPage(), pageQuery.getSize());
    }

    /**
     * 获取分页结果
     *
     * @param entityPage 分页数据
     * @param <T>        实体类型
     * @return 分页结果
     */
    public <T> PageResult<T> getPageResult(Page<T> entityPage) {
        return new PageResult<T>()
                .setPage(entityPage.getNumber())
                .setSize(entityPage.getSize())
                .setTotal(entityPage.getTotalElements())
                .setTotalPage(entityPage.getTotalPages())
                .setRecords(entityPage.getContent());
    }
}
