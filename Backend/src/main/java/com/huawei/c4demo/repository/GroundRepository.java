package com.huawei.c4demo.repository;

import com.huawei.c4demo.pojo.Groundtab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface GroundRepository extends JpaRepository<Groundtab,Integer> {
    Optional<Groundtab> findById(Integer id);

    @Query(value = "select attr_Title from groundtab where id = ?",nativeQuery = true)
    String findAttrTitleById(Integer id);


}
