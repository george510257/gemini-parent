package com.gls.gemini.starter.data.jpa.base;

import com.gls.gemini.common.core.base.BaseConverter;
import com.gls.gemini.common.core.base.BaseService;
import com.gls.gemini.common.core.domain.PageQuery;
import com.gls.gemini.common.core.domain.PageResult;
import com.gls.gemini.common.core.interfaces.IDoMain;
import com.gls.gemini.starter.data.jpa.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public abstract class BaseServiceImpl<Converter extends BaseConverter<Vo, Entity>,
        Repository extends BaseRepository<Entity>,
        Vo extends IDoMain,
        Entity extends BaseEntity>
        implements BaseService<Vo> {

    @Autowired
    protected Converter converter;

    @Autowired
    protected Repository repository;

    /**
     * 新增
     *
     * @param vo 视图
     * @return 返回新增后的视图
     */
    @Override
    public Vo insert(Vo vo) {
        Entity entity = converter.convert(vo);
        Entity save = repository.save(entity);
        return converter.reverse(save);
    }

    /**
     * 修改
     *
     * @param id 主键
     * @param vo 视图
     * @return 返回修改后的视图
     */
    @Override
    public Vo update(Long id, Vo vo) {
        Entity entity = repository.getReferenceById(id);
        converter.convertCopy(vo, entity);
        Entity save = repository.save(entity);
        return converter.reverse(save);
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return 删除结果
     */
    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

    /**
     * 查询
     *
     * @param id 主键
     * @return 查询结果
     */
    @Override
    public Vo get(Long id) {
        Entity entity = repository.getReferenceById(id);
        return converter.reverse(entity);
    }

    /**
     * 分页查询
     *
     * @param pageQuery 分页查询参数
     * @return 分页查询结果
     */
    @Override
    public PageResult<Vo> page(PageQuery<Vo> pageQuery) {
        Vo vo = pageQuery.getParams();
        Entity entity = converter.convert(vo);
        Page<Entity> entityPage = repository.findAll(entity, PageUtil.getPageable(pageQuery));
        PageResult<Entity> pageResult = PageUtil.getPageResult(entityPage);
        return converter.reversePage(pageResult);
    }

    /**
     * 查询所有
     *
     * @param vo 查询参数
     * @return 查询结果
     */
    @Override
    public List<Vo> list(Vo vo) {
        Entity entity = converter.convert(vo);
        List<Entity> entityList = repository.findAll(entity);
        return converter.reverseList(entityList);
    }

    /**
     * 导入数据
     *
     * @param vos 导入对象
     * @return 导入结果
     */
    @Override
    public Boolean importData(List<Vo> vos) {
        List<Entity> entities = converter.convertList(vos);
        repository.saveAll(entities);
        return true;
    }

    /**
     * 导出数据
     *
     * @param vo 查询参数
     * @return 查询结果
     */
    @Override
    public List<Vo> exportData(Vo vo) {
        return list(vo);
    }
}
