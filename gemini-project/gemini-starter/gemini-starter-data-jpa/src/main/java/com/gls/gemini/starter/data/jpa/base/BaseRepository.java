package com.gls.gemini.starter.data.jpa.base;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * 基础仓库
 *
 * @param <Entity> 实体
 */
@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity> extends JpaRepositoryImplementation<Entity, Long> {
    /**
     * 获取翻页数据
     *
     * @param entity   查询条件
     * @param pageable 分页参数
     * @return 翻页数据
     */
    default Page<Entity> findAll(Entity entity, Pageable pageable) {
        return findAll(Example.of(entity), pageable);
    }

    /**
     * 获取数据列表
     *
     * @param entity 查询条件
     * @return 数据列表
     */
    default List<Entity> findAll(Entity entity) {
        return findAll(Example.of(entity));
    }
}
