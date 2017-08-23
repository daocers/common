package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.PaperPolicy;
import co.bugu.tes.service.IPaperPolicyService;
import org.springframework.stereotype.Service;

@Service
public class PaperPolicyServiceImpl extends BaseServiceImpl<PaperPolicy> implements IPaperPolicyService {
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(PaperPolicy paperpolicy) {
//        return baseDao.insert("tes.paperpolicy.insert", paperpolicy);
//    }
//
//    @Override
//    public int updateById(PaperPolicy paperpolicy) {
//        return baseDao.update("tes.paperpolicy.updateById", paperpolicy);
//    }
//
//    @Override
//    public int saveOrUpdate(PaperPolicy paperpolicy) {
//        if(paperpolicy.getId() == null){
//            return baseDao.insert("tes.paperpolicy.insert", paperpolicy);
//        }else{
//            return baseDao.update("tes.paperpolicy.updateById", paperpolicy);
//        }
//    }
//
//    @Override
//    public int delete(PaperPolicy paperpolicy) {
//        return baseDao.delete("tes.paperpolicy.deleteById", paperpolicy);
//    }
//
//    @Override
//    public PaperPolicy findById(Integer id) {
//        return baseDao.selectOne("tes.paperpolicy.selectById", id);
//    }
//
//    @Override
//    public List<PaperPolicy> findAllByObject(PaperPolicy paperpolicy) {
//        return baseDao.selectList("tes.paperpolicy.listByObject", paperpolicy);
//    }
//
//    @Override
//    public PageInfo listByObject(PaperPolicy paperpolicy, PageInfo<PaperPolicy> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.paperpolicy.listByObject", paperpolicy, pageInfo);
//    }
}
