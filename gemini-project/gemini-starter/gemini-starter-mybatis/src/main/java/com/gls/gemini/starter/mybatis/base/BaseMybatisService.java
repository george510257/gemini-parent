package com.gls.gemini.starter.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gls.gemini.common.core.base.BaseConverter;
import com.gls.gemini.common.core.base.BaseService;
import com.gls.gemini.common.core.base.BaseVo;
import com.gls.gemini.common.core.domain.PageQuery;
import com.gls.gemini.common.core.domain.PageResult;
import com.gls.gemini.starter.mybatis.util.PageUtil;

import java.util.List;

public interface BaseMybatisService<Converter extends BaseConverter<Vo, Entity>,
        Mapper extends BaseMapper<Entity>,
        Vo extends BaseVo,
        Entity extends BaseEntity> extends BaseService<Vo> {

    Converter getConverter();

    Mapper getMapper();

    @Override
    default Vo insert(Vo vo) {
        Entity entity = getConverter().convert(vo);
        getMapper().insert(entity);
        return getConverter().reverse(entity);
    }

    @Override
    default Vo update(Long id, Vo vo) {
        vo.setId(id);
        Entity entity = getConverter().convert(vo);
        getMapper().updateById(entity);
        return getConverter().reverse(entity);

    }

    @Override
    default Boolean delete(Long id) {
        int count = getMapper().deleteById(id);
        return count > 0;
    }

    @Override
    default Vo get(Long id) {
        Entity entity = getMapper().selectById(id);
        return getConverter().reverse(entity);
    }

    @Override
    default PageResult<Vo> page(PageQuery<Vo> pageQuery) {
        Vo vo = pageQuery.getParams();
        Entity entity = getConverter().convert(vo);
        IPage<Entity> pages = getMapper().selectPage(PageUtil.getPage(pageQuery), new QueryWrapper<>(entity));
        PageResult<Entity> pageResult = PageUtil.getPageResult(pages);
        return getConverter().reversePage(pageResult);
    }

    @Override
    default List<Vo> list(Vo vo) {
        Entity entity = getConverter().convert(vo);
        List<Entity> entities = getMapper().selectList(new QueryWrapper<>(entity));
        return getConverter().reverseList(entities);
    }
}
