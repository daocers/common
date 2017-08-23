package co.bugu.tes.service;


import co.bugu.framework.core.service.IBaseService;
import co.bugu.tes.model.Station;

import java.util.Map;

public interface IStationService extends IBaseService<Station> {
//    int save(Station station);
//
//    int updateById(Station station);
//
//    int saveOrUpdate(Station station);
//
//    int delete(Station station);
//
//    Station findById(Integer id);
//
//    List<Station> findAllByObject(Station station);
//
//    PageInfo listByObject(Station station, PageInfo<Station> pageInfo) throws Exception;

    Map<String, String> getStationMap();
}
