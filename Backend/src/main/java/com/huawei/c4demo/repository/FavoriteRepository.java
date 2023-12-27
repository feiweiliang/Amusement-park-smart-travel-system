package com.huawei.c4demo.repository;

import com.huawei.c4demo.pojo.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite,Integer> {
    @Query(value = "SELECT ground_id FROM favorite WHERE user_id = :id",nativeQuery = true)
    List<Integer> getGroundIdByUserId(@Param("id") Integer id);
}
