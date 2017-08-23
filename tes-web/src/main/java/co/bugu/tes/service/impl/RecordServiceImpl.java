package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.Record;
import co.bugu.tes.service.IRecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends BaseServiceImpl<Record> implements IRecordService {
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(Record record) {
//        return baseDao.insert("tes.record.insert", record);
//    }
//
//    @Override
//    public int updateById(Record record) {
//        return baseDao.update("tes.record.updateById", record);
//    }
//
//    @Override
//    public int saveOrUpdate(Record record) {
//        if(record.getId() == null){
//            return baseDao.insert("tes.record.insert", record);
//        }else{
//            return baseDao.update("tes.record.updateById", record);
//        }
//    }

//    @Override
//    public int delete(Record record) {
//        return baseDao.delete("tes.record.deleteById", record);
//    }
//
//    @Override
//    public Record findById(Integer id) {
//        return baseDao.selectOne("tes.record.selectById", id);
//    }
//
//    @Override
//    public List<Record> findAllByObject(Record record) {
//        return baseDao.selectList("tes.record.listByObject", record);
//    }
//
//    @Override
//    public PageInfo listByObject(Record record, PageInfo<Record> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.record.listByObject", record, pageInfo);
//    }
}
