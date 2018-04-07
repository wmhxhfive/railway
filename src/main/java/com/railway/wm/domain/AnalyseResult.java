package com.railway.wm.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="analyse_result")
public class AnalyseResult implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String railNo;  // 车牌号码
    @Column(nullable = false)
    private String partNo; // 部位标号
    @Column(nullable = false)
    private String url;// 分析照片存储的位置
    @Column(columnDefinition="timestamp default now()",nullable=false)
    private Date createDate; // 创建时间
    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer analyResult;



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
}

