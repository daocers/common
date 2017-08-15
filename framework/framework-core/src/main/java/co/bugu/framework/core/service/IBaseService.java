package co.bugu.framework.core.service;


import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.exception.TesJedisException;

import java.io.IOException;
import java.util.List;

/**
 * Created by record on 2017/1/5.
 */
public interface IBaseService<T> {
    int save(T record) throws IOException, TesJedisException;

    int updateById(T record) throws TesJedisException, IOException;

    int delete(T record) throws TesJedisException;

    T findById(Integer id) throws Exception;

    List<T> findByObject(T record);

    PageInfo findByObject(T record, PageInfo<T> pageInfo) throws Exception;

    PageInfo findByObject(T record, Integer showCount, Integer curPage) throws Exception;
}
