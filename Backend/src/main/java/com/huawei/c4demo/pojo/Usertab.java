package com.huawei.c4demo.pojo;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "usertab")
@EntityListeners(AuditingEntityListener.class)
public class Usertab {
    @Id
    private Integer id;
    private String mac;
    private Integer right;
    @Column(name = "create_time")
    @CreatedDate
    private Timestamp create_time;
    @Column(name = "last_update_time")
    @LastModifiedDate
    private Timestamp last_update_time;
    private String password;
    private String mobile_phone;
}
