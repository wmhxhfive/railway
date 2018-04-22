package com.railway.wm.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="analyse_result",uniqueConstraints={ @UniqueConstraint(columnNames={"trainInfoId","partNo","checkDate"})})
public class AnalyseResult implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
<<<<<<< HEAD
    @Column(nullable = false)
    private Long trainInfoId; //机车编号

    @Column(nullable = false,length = 3)
    private String partNo; // 部位标号

    public Long getTrainInfoId() {
        return trainInfoId;
    }

    public void setTrainInfoId(Long trainInfoId) {
        this.trainInfoId = trainInfoId;
    }

=======
    @Column(nullable = false,length=30)
    private String railNo;  // 车牌号码
    @Column(nullable = false,length = 20)
    private String partNo; // 部位标号
>>>>>>> 691227df9b25b04447d91b5af5c0394ee83c8de0
    @Column(nullable = false,length = 50)
    private String url;// 分析照片存储的位置

    @Column(columnDefinition="timestamp default now()",nullable=false)
    private Date createDate; // 创建时间

    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer analyResult;
<<<<<<< HEAD

=======
>>>>>>> 691227df9b25b04447d91b5af5c0394ee83c8de0
    @Column(nullable = false,length = 30)
    private String checkDate; // 机车检测时间



    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        if(obj instanceof AnalyseResult){
            AnalyseResult analyseResult =(AnalyseResult)obj;
//			if(user.id = this.id) return true; // 只比较id
            // 比较id和username 一致时才返回true 之后再去比较 hashCode
            if(analyseResult.trainInfoId.equals(this.trainInfoId)
                    && analyseResult.partNo.equals(this.partNo)
                    && analyseResult.checkDate.equals(this.checkDate)) return true;
        }
        return false;

    }

    @Override
    public int hashCode() {

        return trainInfoId.hashCode() * checkDate.hashCode()*partNo.hashCode();
    }

}

