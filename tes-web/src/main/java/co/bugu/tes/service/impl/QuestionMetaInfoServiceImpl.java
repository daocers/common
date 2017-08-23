package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.Property;
import co.bugu.tes.model.PropertyItem;
import co.bugu.tes.model.QuestionMetaInfo;
import co.bugu.tes.service.IQuestionMetaInfoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionMetaInfoServiceImpl extends BaseServiceImpl<QuestionMetaInfo> implements IQuestionMetaInfoService {
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(QuestionMetaInfo questionmetainfo) {
//        return baseDao.insert("tes.questionmetainfo.insert", questionmetainfo);
//    }
//
//    @Override
//    public int updateById(QuestionMetaInfo questionmetainfo) {
//        return baseDao.update("tes.questionmetainfo.updateById", questionmetainfo);
//    }
//
//    @Override
//    public int saveOrUpdate(QuestionMetaInfo questionmetainfo) {
//        if(questionmetainfo.getId() == null){
//            return baseDao.insert("tes.questionmetainfo.insert", questionmetainfo);
//        }else{
//            return baseDao.update("tes.questionmetainfo.updateById", questionmetainfo);
//        }
//    }

    @Override
    public QuestionMetaInfo findById(Integer id){
        QuestionMetaInfo info = baseDao.selectOne("tes.questionMetaInfo.selectById", id);
        if(info != null){
            List<Property> properties = baseDao.selectList("tes.property.selectPropertyByQuestionMetaInfo", info.getId());
            if(properties != null && properties.size() > 0){
                for(Property property: properties){
                    List<PropertyItem> itemList = baseDao.selectList("tes.propertyItem.selectPropertyItemByProperty", property.getId());
                    if(itemList != null){
                        property.setPropertyItemList(itemList);
                    }
                }
                info.setPropertyList(properties);
            }
        }
        return info;
    }


    @Override
    public int saveOrUpdate(QuestionMetaInfo questionmetainfo, List<Map<String, Integer>> list) {
        if(questionmetainfo.getId() == null){
            baseDao.insert("tes.questionMetaInfo.insert", questionmetainfo);
        }else{
            baseDao.update("tes.questionMetaInfo.updateById", questionmetainfo);
        }
        Integer quesMetaInfo = questionmetainfo.getId();

        Map<String, Integer> data = new HashMap<>();
        data.put("metaInfoId", quesMetaInfo);
        baseDao.delete("tes.questionMetaInfo.deleteProperty", data);

        for(Map<String, Integer> map : list){
            map.put("metaInfoId", quesMetaInfo);
            baseDao.insert("tes.questionMetaInfo.addProperty", map);
        }

        return 0;
    }

//    @Override
//    public int delete(QuestionMetaInfo questionmetainfo) {
//        return baseDao.delete("tes.questionmetainfo.deleteById", questionmetainfo);
//    }
//


    @Override
    public void addProperty(Map<String, String> map) {
        baseDao.insert("tes.questionMetaInfo.addProperty", map);
    }

    @Override
    public void deleteProperty(Map<String, String> map) {
        baseDao.delete("tes.questionMetaInfo.deleteProperty", map);
    }
}
