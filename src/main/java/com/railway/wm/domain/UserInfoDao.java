package com.railway.wm.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="t_user")
public class UserInfoDao implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String moblie;
    private String password;
    @Column(columnDefinition="timestamp default now()",nullable=false)
    private Date createDate; // 创建时间

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }
}

