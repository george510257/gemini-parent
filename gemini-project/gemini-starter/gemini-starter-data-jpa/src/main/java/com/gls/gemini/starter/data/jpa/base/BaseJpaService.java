package com.gls.gemini.starter.data.jpa.base;

import com.gls.gemini.common.core.base.BaseConverter;
import com.gls.gemini.common.core.base.BaseService;
import com.gls.gemini.common.core.base.BaseVo;
import com.gls.gemini.common.core.domain.PageQuery;
import com.gls.gemini.common.core.domain.PageResult;
import com.gls.gemini.starter.data.jpa.util.PageUtil;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * jpa基础服务
 *
 * @param <Converter>  转换器
 * @param <Repository> 仓库
 * @param <Vo>         视图
 * @param <Entity>     实体
 */
public interface BaseJpaService<Converter extends BaseConverter<Vo, Entity>,
        Repository extends BaseRepository<Entity>,
        Vo extends BaseVo,
        Entity extends BaseEntity>
        extends BaseService<Vo> {

    /**
     * 获取转换器
     *
     * @return 转换器
     */
    Converter getConverter();

    /**
     * 获取仓库
     *
     * @return 仓库
     */
    Repository getRepository();

    /**
     * 新增
     *
     * @param vo 视图
     * @return 返回新增后的视图
     */
    @Override
    default Vo insert(Vo vo) {
        Entity entity = getConverter().convert(vo);
        Entity save = getRepository().save(entity);
        return getConverter().reverse(save);
    }

    /**
     * 修改
     *
     * @param id 主键
     * @param vo 视图
     * @return 返回修改后的视图
     */
    @Override
    default Vo update(Long id, Vo vo) {
        Entity entity = getRepository().getReferenceById(id);
        getConverter().convertCopy(vo, entity);
        Entity save = getRepository().save(entity);
        return getConverter().reverse(save);
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return 删除结果
     */
    @Override
    default Boolean delete(Long id) {
        getRepository().deleteById(id);
        return true;
    }

    /**
     * 查询
     *
     * @param id 主键
     * @return 查询结果
     */
    @Override
    default Vo get(Long id) {
        Entity entity = getRepository().getReferenceById(id);
        return getConverter().reverse(entity);
    }

    /**
     * 分页查询
     *
     * @param pageQuery 分页查询参数
     * @return 分页查询结果
     */
    @Override
    default PageResult<Vo> page(PageQuery<Vo> pageQuery) {
        Vo vo = pageQuery.getParams();
        Entity entity = getConverter().convert(vo);
        Page<Entity> entityPage = getRepository().findAll(entity, PageUtil.getPageable(pageQuery));
        PageResult<Entity> pageResult = PageUtil.getPageResult(entityPage);
        return getConverter().reversePage(pageResult);
    }

    /**
     * 查询所有
     *
     * @param vo 查询参数
     * @return 查询结果
     */
    @Override
    default List<Vo> list(Vo vo) {
        Entity entity = getConverter().convert(vo);
        List<Entity> entityList = getRepository().findAll(entity);
        return getConverter().reverseList(entityList);
    }
}
