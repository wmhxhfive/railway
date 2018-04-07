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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service("fileProgressService")
public class FileProgressServiceImpl implements FileProgressService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Value("${file.url}")
    private String fileUrl;

    @Autowired
    private AnalyseRepository analyseRepository;

    @Override
    public void scanFileDirectAndResultIntoDB() {
        if (StringUtils.isNullOrEmpty(fileUrl)){
            log.error("文件目录为空");
        }else{
            StringBuilder result = new StringBuilder();
            String filename=new StringBuffer().append(fileUrl).append(DateUtil.getCurrentTimeStr(DateUtil.FORMAT_YYYY_MM_DD)).append(".txt").toString();
            File file = new File(filename);
            List<AnalyseResult>  resultList=new ArrayList<AnalyseResult>();
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
                String s = null;
                while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                   if (!StringUtils.isNullOrEmpty(s)){
                      String[] resultArray= s.split("\\|");
                       AnalyseResult analyseResult=new AnalyseResult();
                       analyseResult.setRailNo(resultArray[0]);
                       analyseResult.setPartNo(resultArray[1]);
                       analyseResult.setUrl(resultArray[2]);
                       analyseResult.setAnalyResult(Integer.parseInt(resultArray[3]));
                       analyseResult.setCheckDate(DateUtil.getCurrentDate());
                       analyseResult.setRailStation("hf");
                       resultList.add(analyseResult);
                   }
                }
                br.close();

                if (!CollectionUtils.isEmpty(resultList)){
                    analyseRepository.saveAll(resultList);
                }
                log.info("_____________"+result);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
