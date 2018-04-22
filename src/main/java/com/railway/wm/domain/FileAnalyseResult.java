package com.railway.wm.domain;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class FileAnalyseResult implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private String railNo;  // 车牌号码
    private String partNo; // 部位标号
    private String url;// 分析照片存储的位置
    private Integer analyResult;//分析结果
    private String checkDate; // 机车检测时间
    private String railStation;//检测车站
    private String errorReason; //异常的原因
    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getRailStation() {
        return railStation;
    }

    public void setRailStation(String railStation) {
        this.railStation = railStation;
    }



    public String getRailNo() {
        return railNo;
    }

    public void setRailNo(String railNo) {
        this.railNo = railNo;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public Integer getAnalyResult() {
        return analyResult;
    }

    public void setAnalyResult(Integer analyResult) {
        this.analyResult = analyResult;
    }
    @Override
    public boolean  equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(obj instanceof FileAnalyseResult){
            FileAnalyseResult analyseResult =(FileAnalyseResult)obj;
            if(analyseResult.railNo.equals(this.railNo)
                    && analyseResult.partNo.equals(this.partNo)
                    && analyseResult.checkDate.equals(this.checkDate)) return true;
        }
        return false;

    }

    @Override
    public int hashCode() {

        return railNo.hashCode() * checkDate.hashCode()*partNo.hashCode();
    }


    public static void main(String[] args) {
        FileAnalyseResult result=new FileAnalyseResult();
        result.setRailNo("mu123");
        result.setPartNo("1");
       // result.setCheckDate("1231");
        FileAnalyseResult result1=new FileAnalyseResult();
        result1.setRailNo("mu123");
        result1.setPartNo("1");
        //result1.setCheckDate("1231");
        Set set=new HashSet();
        set.add(result);
        set.add(result1);
        System.out.printf("set:"+set.toArray().length);
    }
}

