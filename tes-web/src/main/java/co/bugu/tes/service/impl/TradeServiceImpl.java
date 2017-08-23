package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.Page;
import co.bugu.tes.model.Trade;
import co.bugu.tes.service.ITradeService;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl extends BaseServiceImpl<Trade> implements ITradeService {
    @Override
    public int saveTradeAndPage(Trade trade, Page page) {
        if(trade.getId() == null){
            baseDao.insert("tes.trade.insert", trade);
            page.setTradeId(trade.getId());
        }else{
            baseDao.update("tes.trade.updateById", trade);
        }
        if(page.getId() == null){
            baseDao.insert("tes.page.insert", page);
        }else{
            baseDao.update("tes.page.updateById", page);
        }
        return 1;
    }
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(Trade trade) {
//        return baseDao.insert("tes.trade.insert", trade);
//    }
//
//    @Override
//    public int updateById(Trade trade) {
//        return baseDao.update("tes.trade.updateById", trade);
//    }
//
//    @Override
//    public int saveOrUpdate(Trade trade) {
//        if(trade.getId() == null){
//            return baseDao.insert("tes.trade.insert", trade);
//        }else{
//            return baseDao.update("tes.trade.updateById", trade);
//        }
//    }
//
//    @Override
//    public int delete(Trade trade) {
//        return baseDao.delete("tes.trade.deleteById", trade);
//    }
//
//    @Override
//    public Trade findById(Integer id) {
//        return baseDao.selectOne("tes.trade.selectById", id);
//    }
//
//    @Override
//    public List<Trade> findAllByObject(Trade trade) {
//        return baseDao.selectList("tes.trade.listByObject", trade);
//    }
//
//    @Override
//    public PageInfo listByObject(Trade trade, PageInfo<Trade> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.trade.listByObject", trade, pageInfo);
//    }
}
