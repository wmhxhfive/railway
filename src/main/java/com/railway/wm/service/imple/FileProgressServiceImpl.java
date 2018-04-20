package com.railway.wm.service.imple;

import com.mysql.jdbc.StringUtils;
import com.railway.wm.dao.AnalyseRepository;
import com.railway.wm.domain.AnalyseResult;
import com.railway.wm.service.FileProgressService;
import com.railway.wm.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("fileProgressService")
public class FileProgressServiceImpl implements FileProgressService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Value("${file.url}")
    private String fileUrl;

    @Autowired
    private AnalyseRepository analyseRepository;

    @Override
    public void scanFileDirectAndResultIntoDB() throws Exception {
        long start=System.currentTimeMillis();
        if (StringUtils.isNullOrEmpty(fileUrl)){
            log.error("文件目录为空");
        }else {
            String filename = new StringBuffer().append(fileUrl).append(DateUtil.getCurrentDateString()).append(".txt").toString();
            File file = new File(filename);
            List<AnalyseResult> resultList = new ArrayList<AnalyseResult>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
                String s;
                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    if (!StringUtils.isNullOrEmpty(s)) {
                        log.info("文件名:" + filename);
                        String[] resultArray = s.split("\\|");
                        AnalyseResult analyseResult = new AnalyseResult();
                        analyseResult.setRailNo(resultArray[0]);
                        analyseResult.setPartNo(resultArray[1]);
                        analyseResult.setUrl(resultArray[2]);
                        analyseResult.setAnalyResult(Integer.parseInt(resultArray[3]));
                        analyseResult.setCheckDate(resultArray[4]);
                        analyseResult.setRailStation(resultArray[5]);
                        resultList.add(analyseResult);
                    }
                }
                br.close();
            } catch (Exception e) {
                log.error("文件处理错误", e);
                return;
            }
            if (!CollectionUtils.isEmpty(resultList)){
                //查询当天的分析数据 TODO 时间格式为当天时间
                List<AnalyseResult> analyseResults = analyseRepository.findAnalyseResultsByCheckDateBetween(DateUtil.getCurrentDateString(),
                       DateUtil.dateIncreaseByDay(DateUtil.getCurrentDateString(),1));
                if (!CollectionUtils.isEmpty(resultList) && !CollectionUtils.isEmpty(analyseResults))
                { for (AnalyseResult fi : resultList) {
                    for (AnalyseResult db : analyseResults) {
                        if (!fi.equals(db)) {
                            //不存在数据库中的入库操作
                            analyseRepository.save(fi);
                            log.info(fi.toString(), "save success");
                        }
                    }

                }
                }
                else if (CollectionUtils.isEmpty(analyseResults)){
                    analyseRepository.saveAll(resultList);
                }
                log.info("end wast time{0},ms",System.currentTimeMillis()-start/1000);
            }else{
                log.error("文件名{0},内容null ",filename);
            }


        }
    }
}
