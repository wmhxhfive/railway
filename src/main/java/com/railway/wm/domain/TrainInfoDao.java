package com.railway.wm.domain;
/**
 *
 * 机车的概要信息
 *
 */


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="train_info",uniqueConstraints={
        @UniqueConstraint(columnNames={"railNo","railStation","checkDate"})})
public class TrainInfoDao implements Serializable {
    private static final long serialVersionUID = -3258839839160856673L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,length=30)
    private String railNo;  //  机车编号

    @Column(length = 3)
    private String isNormal; // 是否正常

    @Column(columnDefinition="timestamp default now()",nullable=false)
    private Date createDate; // 创建时间

    @Column(nullable = false,length = 30)
    private String checkDate; // 机车检测时间

    @Column(nullable = false,length = 20)
    private String railStation;//检测车站
    @Column
    private String errorReason;

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRailNo() {
        return railNo;
    }

    public void setRailNo(String railNo) {
        this.railNo = railNo;
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    @Override
    public boolean  equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(obj instanceof TrainInfoDao){
            TrainInfoDao analyseResult =(TrainInfoDao)obj;
            if(analyseResult.railNo.equals(this.railNo)
                    && analyseResult.getRailStation().equals(this.railStation)
                    && analyseResult.checkDate.equals(this.checkDate)) return true;
        }
        return false;

    }

    @Override
    public int hashCode() {

        return railNo.hashCode() * checkDate.hashCode()*railStation.hashCode();
    }

    @Override
    public String toString() {
        return "TrainInfoDao{" +
                "id=" + id +
                ", railNo='" + railNo + '\'' +
                ", isNormal='" + isNormal + '\'' +
                ", createDate=" + createDate +
                ", checkDate='" + checkDate + '\'' +
                ", railStation='" + railStation + '\'' +
                '}';
    }


}

