package com.gls.gemini.common.core.base;

import com.gls.gemini.common.core.constant.HeaderConstants;
import com.gls.gemini.common.core.domain.PageQuery;
import com.gls.gemini.common.core.domain.PageResult;
import com.gls.gemini.common.core.domain.Result;
import com.gls.gemini.common.core.enums.ResultEnums;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import java.util.List;

/**
 * 基础控制器
 *
 * @param <Service> 服务
 * @param <Vo>      视图
 */
public interface BaseController<Service extends BaseService<Vo>, Vo extends BaseVo>
        extends BaseFeign<Vo> {
    /**
     * 获取服务
     *
     * @return 服务
     */
    Service getService();

    /**
     * 新增
     *
     * @param vo 新增对象
     * @return 新增结果
     */
    @Override
    @Operation(summary = "新增", description = "新增")
    @Parameter(name = HeaderConstants.CLIENT_TYPE, in = ParameterIn.HEADER, example = "PC", description = "客户端类型(PC：统一返回Result和PageResult对象)")
    default Result<Vo> insert(Vo vo) {
        return ResultEnums.SUCCESS.getResult(this.getService().insert(vo));
    }

    /**
     * 更新
     *
     * @param id 主键
     * @param vo 修改对象
     * @return 修改结果
     */
    @Override
    @Operation(summary = "更新", description = "更新")
    @Parameter(name = HeaderConstants.CLIENT_TYPE, in = ParameterIn.HEADER, example = "PC", description = "客户端类型(PC：统一返回Result和PageResult对象)")
    default Result<Vo> update(Long id, Vo vo) {
        return ResultEnums.SUCCESS.getResult(this.getService().update(id, vo));
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return 删除结果
     */
    @Override
    @Operation(summary = "删除", description = "删除")
    @Parameter(name = HeaderConstants.CLIENT_TYPE, in = ParameterIn.HEADER, example = "PC", description = "客户端类型(PC：统一返回Result和PageResult对象)")
    default Result<Boolean> delete(Long id) {
        return ResultEnums.SUCCESS.getResult(this.getService().delete(id));
    }

    /**
     * 查询
     *
     * @param id 主键
     * @return 查询结果
     */
    @Override
    @Operation(summary = "查询", description = "查询")
    @Parameter(name = HeaderConstants.CLIENT_TYPE, in = ParameterIn.HEADER, example = "PC", description = "客户端类型(PC：统一返回Result和PageResult对象)")
    default Result<Vo> get(Long id) {
        return ResultEnums.SUCCESS.getResult(this.getService().get(id));
    }

    /**
     * 分页查询
     *
     * @param pageQuery 分页查询对象
     * @return 分页查询结果
     */
    @Override
    @Operation(summary = "分页查询", description = "分页查询")
    @Parameter(name = HeaderConstants.CLIENT_TYPE, in = ParameterIn.HEADER, example = "PC", description = "客户端类型(PC：统一返回Result和PageResult对象)")
    default Result<PageResult<Vo>> page(PageQuery<Vo> pageQuery) {
        return ResultEnums.SUCCESS.getResult(this.getService().page(pageQuery));
    }

    /**
     * 查询所有
     *
     * @param vo 查询对象
     * @return 查询结果
     */
    @Override
    @Operation(summary = "查询所有", description = "查询所有")
    @Parameter(name = HeaderConstants.CLIENT_TYPE, in = ParameterIn.HEADER, example = "PC", description = "客户端类型(PC：统一返回Result和PageResult对象)")
    default Result<List<Vo>> list(Vo vo) {
        return ResultEnums.SUCCESS.getResult(this.getService().list(vo));
    }
}
