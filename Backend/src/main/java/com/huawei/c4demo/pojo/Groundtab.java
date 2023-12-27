package com.huawei.c4demo.pojo;
import lombok.Data;
import org.hibernate.annotations.Columns;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Groundtab {
    @Id
    private Integer id;
    private String points;
    private float attrDataX;
    private float attrDataY;
    @Column(name = "attr_Title")
    private String attrTitle;
    private String title;
    private String fill;
    private String stroke;
    private float x;
    private float y;
    private String connectBuildings;
    @CreatedDate
    private Timestamp create_time;
    @LastModifiedDate
    private Timestamp last_update_time;
    private Integer run_time;
    private Integer run_user_num;
    private String description;

}
