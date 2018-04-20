package com.railway.wm.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="analyse_result",uniqueConstraints={ @UniqueConstraint(columnNames={"railNo","partNo","checkDate"})})
public class AnalyseResult implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,length=30)
    private String railNo;  // 车牌号码
    @Column(nullable = false,length = 20)
    private String partNo; // 部位标号
    @Column(nullable = false,length = 50)
    private String url;// 分析照片存储的位置
    @Column(columnDefinition="timestamp default now()",nullable=false)
    private Date createDate; // 创建时间
    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer analyResult;
    @Column(nullable = false,length = 30)
    private String checkDate; // 机车检测时间
    private String railStation;//检测车站

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

    @Override
    public String toString() {
        return "AnalyseResult{" +
                "id=" + id +
                ", railNo='" + railNo + '\'' +
                ", partNo='" + partNo + '\'' +
                ", url='" + url + '\'' +
                ", createDate=" + createDate +
                ", analyResult=" + analyResult +
                ", checkDate='" + checkDate + '\'' +
                ", railStation='" + railStation + '\'' +
                '}';
    }

    public static void main(String[] args) {
        AnalyseResult result=new AnalyseResult();
        result.setRailNo("mu123");
        result.setPartNo("1");
       // result.setCheckDate("1231");
        AnalyseResult result1=new AnalyseResult();
        result1.setRailNo("mu123");
        result1.setPartNo("1");
        //result1.setCheckDate("1231");
        Set set=new HashSet();
        set.add(result);
        set.add(result1);
        System.out.printf("set:"+set.toArray().length);
    }
}

