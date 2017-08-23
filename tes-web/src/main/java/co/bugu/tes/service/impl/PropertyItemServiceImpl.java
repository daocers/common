package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.PropertyItem;
import co.bugu.tes.service.IPropertyItemService;
import org.springframework.stereotype.Service;

@Service
public class PropertyItemServiceImpl extends BaseServiceImpl<PropertyItem> implements IPropertyItemService {
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(PropertyItem propertyitem) {
//        return baseDao.insert("tes.propertyitem.insert", propertyitem);
//    }
//
//    @Override
//    public int updateById(PropertyItem propertyitem) {
//        return baseDao.update("tes.propertyitem.updateById", propertyitem);
//    }
//
//    @Override
//    public int saveOrUpdate(PropertyItem propertyitem) {
//        if(propertyitem.getId() == null){
//            return baseDao.insert("tes.propertyitem.insert", propertyitem);
//        }else{
//            return baseDao.update("tes.propertyitem.updateById", propertyitem);
//        }
//    }
//
//    @Override
//    public int delete(PropertyItem propertyitem) {
//        return baseDao.delete("tes.propertyitem.deleteById", propertyitem);
//    }
//
//    @Override
//    public PropertyItem findById(Integer id) {
//        return baseDao.selectOne("tes.propertyitem.selectById", id);
//    }
//
//    @Override
//    public List<PropertyItem> findAllByObject(PropertyItem propertyitem) {
//        return baseDao.selectList("tes.propertyitem.listByObject", propertyitem);
//    }
//
//    @Override
//    public PageInfo listByObject(PropertyItem propertyitem, PageInfo<PropertyItem> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.propertyitem.listByObject", propertyitem, pageInfo);
//    }
}
