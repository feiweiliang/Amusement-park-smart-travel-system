package com.huawei.c4demo.repository;

import com.huawei.c4demo.pojo.Groundusertab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroundusertabRepository extends JpaRepository<Groundusertab, Integer> {

    @Query(value = "SELECT id,attr_title_user_num,create_time FROM groundusertab where create_time >= ?1 and create_time < ?2",nativeQuery = true)
     List<Groundusertab> getPastPlacesUsers(String startTime, String endTime) ;

}
