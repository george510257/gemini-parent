package com.gls.gemini.common.core.base;

import com.gls.gemini.common.core.domain.PageQuery;
import com.gls.gemini.common.core.domain.PageResult;
import com.gls.gemini.common.core.domain.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基础feign
 *
 * @param <V> vo
 */
public interface BaseFeign<V extends BaseVo> {

    /**
     * 新增
     *
     * @param vo 新增对象
     * @return 新增结果
     */
    @PostMapping("")
    Result<V> insert(@RequestBody @Validated V vo);

    /**
     * 修改
     *
     * @param id 主键
     * @param vo 修改对象
     * @return 修改结果
     */
    @PutMapping("{id}")
    Result<V> update(@PathVariable Long id, @RequestBody @Validated V vo);

    /**
     * 删除
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping("{id}")
    Result<Boolean> delete(@PathVariable Long id);

    /**
     * 查询
     *
     * @param id 主键
     * @return 查询结果
     */
    @GetMapping("{id}")
    Result<V> get(@PathVariable Long id);

    /**
     * 分页查询
     *
     * @param pageQuery 分页查询参数
     * @return 分页查询结果
     */
    @PostMapping("page")
    Result<PageResult<V>> page(@RequestBody @Validated PageQuery<V> pageQuery);

    /**
     * 列表查询
     *
     * @param vo 查询参数
     * @return 查询结果
     */
    @PostMapping("list")
    Result<List<V>> list(@RequestBody @Validated V vo);
}
