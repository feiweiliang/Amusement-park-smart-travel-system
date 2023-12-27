package com.huawei.c4demo.pojo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Aptab {
    @Id
    private String id;
    @CreatedDate
    private Timestamp create_time;
    @LastModifiedDate
    private Timestamp last_update_time;
    private float x;
    private float y;
    private String mac;
    private String description;
}
