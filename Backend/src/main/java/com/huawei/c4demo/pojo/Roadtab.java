package com.huawei.c4demo.pojo;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Roadtab {
    @Id
    private Integer id;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    @CreatedDate
    private Timestamp create_time;
    private float power;
}
