package com.gls.gemini.starter.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gls.gemini.common.core.base.BaseConverter;
import com.gls.gemini.common.core.base.BaseService;
import com.gls.gemini.common.core.domain.PageQuery;
import com.gls.gemini.common.core.domain.PageResult;
import com.gls.gemini.common.core.interfaces.IDoMain;
import com.gls.gemini.starter.mybatis.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseServiceImpl<Converter extends BaseConverter<Vo, Entity>,
        Mapper extends BaseMapper<Entity>,
        Vo extends IDoMain,
        Entity extends BaseEntity> extends ServiceImpl<Mapper, Entity>
        implements BaseService<Vo> {

    @Autowired
    protected Converter converter;

    @Override
    public Vo insert(Vo vo) {
        Entity entity = converter.convert(vo);
        baseMapper.insert(entity);
        return converter.reverse(entity);
    }

    @Override
    public Vo update(Long id, Vo vo) {
        vo.setId(id);
        Entity entity = converter.convert(vo);
        baseMapper.updateById(entity);
        return converter.reverse(entity);

    }

    @Override
    public Boolean delete(Long id) {
        int count = baseMapper.deleteById(id);
        return count > 0;
    }

    @Override
    public Vo get(Long id) {
        Entity entity = baseMapper.selectById(id);
        return converter.reverse(entity);
    }

    @Override
    public PageResult<Vo> page(PageQuery<Vo> pageQuery) {
        Vo vo = pageQuery.getParams();
        Entity entity = converter.convert(vo);
        IPage<Entity> pages = baseMapper.selectPage(PageUtil.getPage(pageQuery), new QueryWrapper<>(entity));
        PageResult<Entity> pageResult = PageUtil.getPageResult(pages);
        return converter.reversePage(pageResult);
    }

    @Override
    public List<Vo> list(Vo vo) {
        Entity entity = converter.convert(vo);
        List<Entity> entities = baseMapper.selectList(new QueryWrapper<>(entity));
        return converter.reverseList(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean importData(List<Vo> vos) {
        List<Entity> entities = converter.convertList(vos);
        return this.saveOrUpdateBatch(entities);
    }

    @Override
    public List<Vo> exportData(Vo vo) {
        return this.list(vo);
    }
}
