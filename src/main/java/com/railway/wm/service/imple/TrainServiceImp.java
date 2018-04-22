package com.railway.wm.service.imple;

import com.mysql.jdbc.StringUtils;
import com.railway.wm.dao.AnalyseRepository;
import com.railway.wm.dao.TrainInfoRepository;
import com.railway.wm.domain.AnalyseResult;
import com.railway.wm.domain.TrainInfoDao;
import com.railway.wm.request.TrainInfoReq;
import com.railway.wm.response.TrainDetailInfo;
import com.railway.wm.response.TrainInfo;
import com.railway.wm.response.TrainInfo4ScreenRep;
import com.railway.wm.response.TrainInfoRps;
import com.railway.wm.service.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Service("trainService")
public class TrainServiceImp implements TrainService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    TrainInfoRepository trainInfoRepository;

    @Autowired
    AnalyseRepository analyseRepository;

    @Override
    public TrainInfo4ScreenRep findTrainInfoByStation(String railStation) {
        TrainInfo4ScreenRep trainInfo4ScreenRep=new TrainInfo4ScreenRep();

        List<TrainInfoDao> list=trainInfoRepository.findTop5ByRailStationOrderByCheckDateDesc(railStation);
        if(CollectionUtils.isEmpty(list)){
            trainInfo4ScreenRep.setRet("1");
            trainInfo4ScreenRep.setMessage("站点没有机车检测信息");
            return trainInfo4ScreenRep;
        }
        List<TrainInfo> trainInfos=new ArrayList<>();
        for (TrainInfoDao dao:list
             ) {
            TrainInfo trainInfo=new TrainInfo();
            BeanUtils.copyProperties(dao,trainInfo);
            trainInfos.add(trainInfo);
        }
        trainInfo4ScreenRep.getTrainInfos().addAll(trainInfos);

        // 详情
        TrainInfoDao infoDao=list.get(0);
        List<AnalyseResult> analyseResults=analyseRepository.findAnalyseResultByTrainInfoIdAndCheckDate(infoDao.getId(),infoDao.getCheckDate());
        if (CollectionUtils.isEmpty(analyseResults)){
            trainInfo4ScreenRep.setRet("0");
            return trainInfo4ScreenRep;
        }

        List<TrainDetailInfo> trainDetailInfoList=new ArrayList<>();
        for (AnalyseResult result:
                analyseResults) {
            TrainDetailInfo trainDetailInfo=new TrainDetailInfo();
            BeanUtils.copyProperties(result,trainDetailInfo);
            trainDetailInfoList.add(trainDetailInfo);
        }
        trainInfo4ScreenRep.getTrainDetailInfos().addAll(trainDetailInfoList);
        trainInfo4ScreenRep.setRet("0");
        return trainInfo4ScreenRep;
    }

    @Override
    public TrainInfoRps findTrainInfoByCondition(TrainInfoReq trainInfo) {

        TrainInfoRps req=new TrainInfoRps();
        // 创建查询
        Specification querySpecifi = new Specification<TrainInfoDao>() {
            @Override
            public Predicate toPredicate(Root<TrainInfoDao> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(!StringUtils.isNullOrEmpty(trainInfo.getRailNo())){
                    predicates.add(criteriaBuilder.equal(root.get("railNo"), trainInfo.getRailNo()));
                }
                if(!StringUtils.isNullOrEmpty(trainInfo.getRailStation())){
                    predicates.add(criteriaBuilder.equal(root.get("railStation"), trainInfo.getRailStation()));
                }
                if(!StringUtils.isNullOrEmpty(trainInfo.getBeginCheckDate())){
                    predicates.add(criteriaBuilder.greaterThan(root.get("checkDate"), trainInfo.getBeginCheckDate()));
                }
                if(!StringUtils.isNullOrEmpty(trainInfo.getEndCheckDate())){
                    predicates.add(criteriaBuilder.lessThan(root.get("checkDate"), trainInfo.getEndCheckDate()));
                }

                if(!StringUtils.isNullOrEmpty(trainInfo.getIsNormal())){
                    predicates.add(criteriaBuilder.equal(root.get("isNormal"), trainInfo.getIsNormal()));
                }
                return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
            }
        };



        Pageable pageable = new PageRequest(trainInfo.getPage(),trainInfo.getPageSize(),
                Sort.Direction.DESC,"id");
        Page<TrainInfoDao> re=  trainInfoRepository.findAll(querySpecifi,pageable);
        if(re!=null&&
                !CollectionUtils.isEmpty(re.getContent())){
            List<TrainInfo> resultList = new ArrayList<>();
            for (TrainInfoDao dao:
                    re.getContent()
                 ) {

                TrainInfo trainInfo1=new TrainInfo();
                BeanUtils.copyProperties(dao,trainInfo1);
                resultList.add(trainInfo1);
            }
            req.getTrainInfoList().addAll(resultList);
            req.setTotalNum(re.getTotalElements());
            req.setTotalPage(re.getTotalPages());
            req.setRet("0");
            req.setMessage("success");
        }

        return req;
    }
}
