package com.zl.blog.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实现 Mybatis-plus Page对象内泛型列表的复制
 */
public class BeanCopyUtils<S, T> {

    /**
     * 泛型列表复制
     *
     * @param sCollection 源对象集合
     * @param tCollection 目标对象集合
     * @param tClass      目标类型.class
     */
    public static <S, T> void copyList(Collection<S> sCollection, Collection<T> tCollection, Class<T> tClass) {
        if (sCollection == null || tCollection == null || sCollection.size() == 0) {
            return;
        }
        try {
            for (S s : sCollection) {
                T t = tClass.newInstance();
                BeanUtils.copyProperties(s, t);
                tCollection.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mybatis-plus IPage对象 复制
     *
     * @param siPage 源分页对象
     * @param tiPage 目标分页对象
     * @param tClass 目标类
     */
    public static <S, T> void copyIPage(IPage<S> siPage, IPage<T> tiPage, Class<T> tClass) {
        if (siPage == null || tiPage == null) {
            return;
        }
        tiPage.setCurrent(siPage.getCurrent());
        tiPage.setPages(siPage.getPages());
        tiPage.setSize(siPage.getSize());
        tiPage.setTotal(siPage.getTotal());
        List<S> sList = siPage.getRecords();
        List<T> tList = new ArrayList<>();
        copyList(sList, tList, tClass);
        tiPage.setRecords(tList);
    }


    /**
     * 复制对象
     *
     * @param source 源
     * @param target 目标
     * @return {@link T}
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T temp = null;
        try {
            temp = target.newInstance();
            if (null != source) {
                BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 拷贝集合
     *
     * @param source 源
     * @param target 目标
     * @return {@link List<T>} 集合
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(BeanCopyUtils.copyObject(obj, target));
            }
        }
        return list;
    }

}
