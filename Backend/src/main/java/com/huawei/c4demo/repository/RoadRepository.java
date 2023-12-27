package com.huawei.c4demo.repository;

import com.huawei.c4demo.pojo.Roadtab;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadRepository extends JpaRepository<Roadtab,Integer> {
}
