package com.huawei.c4demo.repository;

import com.huawei.c4demo.pojo.Aptab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApRepository extends JpaRepository<Aptab,String> {
}
