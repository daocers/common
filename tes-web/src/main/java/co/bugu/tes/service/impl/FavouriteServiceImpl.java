package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.Favourite;
import co.bugu.tes.service.IFavouriteService;
import org.springframework.stereotype.Service;

@Service
public class FavouriteServiceImpl extends BaseServiceImpl<Favourite> implements IFavouriteService {
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(Favourite favourite) {
//        return baseDao.insert("tes.favourite.insert", favourite);
//    }
//
//    @Override
//    public int updateById(Favourite favourite) {
//        return baseDao.update("tes.favourite.updateById", favourite);
//    }
//
//    @Override
//    public int saveOrUpdate(Favourite favourite) {
//        if(favourite.getId() == null){
//            return baseDao.insert("tes.favourite.insert", favourite);
//        }else{
//            return baseDao.update("tes.favourite.updateById", favourite);
//        }
//    }
//
//    @Override
//    public int delete(Favourite favourite) {
//        return baseDao.delete("tes.favourite.deleteById", favourite);
//    }
//
//    @Override
//    public Favourite findById(Integer id) {
//        return baseDao.selectOne("tes.favourite.selectById", id);
//    }
//
//    @Override
//    public List<Favourite> findAllByObject(Favourite favourite) {
//        return baseDao.selectList("tes.favourite.listByObject", favourite);
//    }
//
//    @Override
//    public PageInfo listByObject(Favourite favourite, PageInfo<Favourite> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.favourite.listByObject", favourite, pageInfo);
//    }
}
