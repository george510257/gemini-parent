package com.gls.gemini.common.core.base;

import com.gls.gemini.common.core.domain.PageQuery;
import com.gls.gemini.common.core.domain.PageResult;
import com.gls.gemini.common.core.interfaces.IDomain;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 基础服务
 *
 * @param <V> 基础视图
 */
@CacheConfig(cacheResolver = "defaultCacheResolver")
public interface BaseService<V extends IDomain> {

    /**
     * 新增
     *
     * @param vo 视图
     * @return 返回新增后的视图
     */
    @CachePut(key = "#result.id", value = "vo")
    V insert(V vo);

    /**
     * 修改
     *
     * @param id 主键
     * @param vo 视图
     * @return 返回修改后的视图
     */
    @CachePut(key = "#result.id", value = "vo")
    V update(Long id, V vo);

    /**
     * 删除
     *
     * @param id 主键
     * @return 删除结果
     */
    @CacheEvict(key = "#id", value = "vo")
    Boolean delete(Long id);

    /**
     * 查询
     *
     * @param id 主键
     * @return 查询结果
     */
    @Cacheable(key = "#id", value = "vo")
    V get(Long id);

    /**
     * 分页查询
     *
     * @param pageQuery 分页查询参数
     * @return 分页查询结果
     */
    PageResult<V> page(PageQuery<V> pageQuery);

    /**
     * 查询所有
     *
     * @param vo 查询参数
     * @return 查询结果
     */
    List<V> list(V vo);

    /**
     * 导入数据
     *
     * @param vos 导入对象
     * @return 导入结果
     */
    Boolean importData(List<V> vos);

    /**
     * 导出数据
     *
     * @param vo 查询参数
     * @return 查询结果
     */
    List<V> exportData(V vo);
}
