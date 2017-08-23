package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Page;
import co.bugu.tes.model.Trade;

public interface ITradeService extends IBaseService<Trade> {
//    int save(Trade trade);
//
//    int updateById(Trade trade);
//
//    int saveOrUpdate(Trade trade);
//
//    int delete(Trade trade);
//
//    Trade findById(Integer id);
//
//    List<Trade> findAllByObject(Trade trade);
//
//    PageInfo listByObject(Trade trade, PageInfo<Trade> pageInfo) throws Exception;

    int saveTradeAndPage(Trade trade, Page page);
}
