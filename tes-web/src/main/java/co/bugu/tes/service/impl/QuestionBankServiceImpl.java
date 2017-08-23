package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.tes.model.QuestionBank;
import co.bugu.tes.service.IQuestionBankService;
import org.springframework.stereotype.Service;

@Service
public class QuestionBankServiceImpl extends BaseServiceImpl<QuestionBank> implements IQuestionBankService {
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(QuestionBank questionbank) {
//        return baseDao.insert("tes.questionbank.insert", questionbank);
//    }
//
//    @Override
//    public int updateById(QuestionBank questionbank) {
//        return baseDao.update("tes.questionbank.updateById", questionbank);
//    }
//
//    @Override
//    public int saveOrUpdate(QuestionBank questionbank) {
//        if(questionbank.getId() == null){
//            return baseDao.insert("tes.questionbank.insert", questionbank);
//        }else{
//            return baseDao.update("tes.questionbank.updateById", questionbank);
//        }
//    }
//
//    @Override
//    public int delete(QuestionBank questionbank) {
//        return baseDao.delete("tes.questionbank.deleteById", questionbank);
//    }
//
//    @Override
//    public QuestionBank findById(Integer id) {
//        return baseDao.selectOne("tes.questionbank.selectById", id);
//    }
//
//    @Override
//    public List<QuestionBank> findAllByObject(QuestionBank questionbank) {
//        return baseDao.selectList("tes.questionbank.listByObject", questionbank);
//    }
//
//    @Override
//    public PageInfo listByObject(QuestionBank questionbank, PageInfo<QuestionBank> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.questionbank.listByObject", questionbank, pageInfo);
//    }
}
