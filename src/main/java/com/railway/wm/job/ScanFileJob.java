package com.railway.wm.job;

import com.railway.wm.dao.UserRepository;
import com.railway.wm.service.FileProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class ScanFileJob {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());


    @Autowired
    FileProgressService fileProgressService;
    @Scheduled(cron = "*/10 * * * * ? ")
    public void scanjob(){
        log.info("_________begin_________");
        fileProgressService.scanFileDirectAndResultIntoDB();
        log.info("__________end___________");
    }
}
