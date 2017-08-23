package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.Property;
import co.bugu.tes.model.PropertyItem;
import co.bugu.tes.service.IPropertyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl extends BaseServiceImpl<Property> implements IPropertyService {
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(Property property) {
//        return baseDao.insert("tes.property.insert", property);
//    }
//
//    @Override
//    public int updateById(Property property) {
//        return baseDao.update("tes.property.updateById", property);
//    }
//
//    @Override
//    public int saveOrUpdate(Property property) {
//        if(property.getId() == null){
//            return baseDao.insert("tes.property.insert", property);
//        }else{
//            return baseDao.update("tes.property.updateById", property);
//        }
//    }

    @Override
    public int saveOrUpdate(Property property, List<PropertyItem> itemList) {
        if(property.getId() == null){
            baseDao.insert("tes.property.insert", property);
        }else{
            baseDao.update("tes.property.updateById", property);
        }
        for(PropertyItem item: itemList){
            item.setPropertyId(property.getId());
            if(item.getId() == null){
                baseDao.insert("tes.propertyItem.insert", item);
            }else{
                baseDao.update("tes.propertyItem.updateById", item);
            }
        }
        return 0;
    }

//    @Override
//    public int delete(Property property) {
//        return baseDao.delete("tes.property.deleteById", property);
//    }
//
//    @Override
//    public Property findById(Integer id) {
//        return baseDao.selectOne("tes.property.selectById", id);
//    }
//
//    @Override
//    public List<Property> findAllByObject(Property property) {
//        return baseDao.selectList("tes.property.listByObject", property);
//    }
//
//    @Override
//    public PageInfo listByObject(Property property, PageInfo<Property> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.property.listByObject", property, pageInfo);
//    }
}
