package com.gls.gemini.common.core.base;

import org.mapstruct.InheritConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 转换器
 *
 * @param <Source> 源
 * @param <Target> 目标
 */
public interface BaseConverter<Source, Target> {
    /**
     * 转换 源 -> 目标
     *
     * @param source 源
     * @return 目标
     */
    Target convert(Source source);

    /**
     * 拷贝转换 源 -> 目标
     *
     * @param source 源
     * @param target 目标
     * @return 目标
     */
    @InheritConfiguration(name = "convert")
    Target convertCopy(Source source, Target target);

    /**
     * 转换列表 源列表 -> 目标列表
     *
     * @param sources 源列表
     * @return 目标列表
     */
    default List<Target> convertList(Collection<Source> sources) {
        return sources.stream().map(this::convert).collect(Collectors.toList());
    }

    /**
     * 转换列表 源列表 -> 目标列表
     *
     * @param sources 源列表
     * @return 目标列表
     */
    default Set<Target> convertSet(Collection<Source> sources) {
        return sources.stream().map(this::convert).collect(Collectors.toSet());
    }

    /**
     * 转换 目标 -> 源
     *
     * @param target 目标
     * @return 源
     */
    Source reverse(Target target);

    /**
     * 拷贝转换 目标 -> 源
     *
     * @param target 目标
     * @param source 源
     * @return 源
     */
    @InheritConfiguration(name = "reverse")
    Source reverseCopy(Target target, Source source);

    /**
     * 转换列表 目标列表 -> 源列表
     *
     * @param targets 目标列表
     * @return 源列表
     */
    default List<Source> reverseList(Collection<Target> targets) {
        return targets.stream().map(this::reverse).collect(Collectors.toList());
    }

    /**
     * 转换列表 目标列表 -> 源列表
     *
     * @param targets 目标列表
     * @return 源列表
     */
    default Set<Source> reverseSet(Collection<Target> targets) {
        return targets.stream().map(this::reverse).collect(Collectors.toSet());
    }

}
