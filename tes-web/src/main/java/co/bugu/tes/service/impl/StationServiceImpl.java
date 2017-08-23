package co.bugu.tes.service.impl;


import co.bugu.framework.core.service.impl.BaseServiceImpl;
import co.bugu.framework.util.JedisUtil;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.Station;
import co.bugu.tes.service.IStationService;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StationServiceImpl extends BaseServiceImpl<Station> implements IStationService {
    @Override
    public Map<String, String> getStationMap() {
        Map<String, String> map = JedisUtil.hgetall(Constant.STATION_INFO);
        if(MapUtils.isNotEmpty(map)){
            return map;
        }
        map = new HashMap<>();
        List<Station> stations = baseDao.selectList("tes.station.findByObject", null);
        for(Station station: stations){
            map.put(station.getName(), station.getId() + "");
        }
        return map;
    }
//    @Autowired
//    BaseDao baseDao;
//
//    @Override
//    public int save(Station station) {
//        return baseDao.insert("tes.station.insert", station);
//    }
//
//    @Override
//    public int updateById(Station station) {
//        return baseDao.update("tes.station.updateById", station);
//    }
//
//    @Override
//    public int saveOrUpdate(Station station) {
//        if(station.getId() == null){
//            return baseDao.insert("tes.station.insert", station);
//        }else{
//            return baseDao.update("tes.station.updateById", station);
//        }
//    }
//
//    @Override
//    public int delete(Station station) {
//        return baseDao.delete("tes.station.deleteById", station);
//    }
//
//    @Override
//    public Station findById(Integer id) {
//        return baseDao.selectOne("tes.station.selectById", id);
//    }
//
//    @Override
//    public List<Station> findAllByObject(Station station) {
//        return baseDao.selectList("tes.station.listByObject", station);
//    }
//
//    @Override
//    public PageInfo listByObject(Station station, PageInfo<Station> pageInfo) throws Exception {
//        return baseDao.listByObject("tes.station.listByObject", station, pageInfo);
//    }
}
