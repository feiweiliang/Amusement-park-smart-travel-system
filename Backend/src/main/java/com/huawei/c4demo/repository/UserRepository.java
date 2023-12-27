package com.huawei.c4demo.repository;

import com.huawei.c4demo.pojo.Usercoortab;
import com.huawei.c4demo.pojo.Usertab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<Usertab, Integer> {

    /**
     * 查询startDate到endDate时间段内的用户信息，使用DISTINCT去重，因此最终返回的数据只有终端mac
     * @param startDate 开始时间
     * @param endDate  结束时间
     * @return 去重后的终端mac
     */
    @Query(value = "SELECT DISTINCT mac FROM (SELECT id,mac,create_time FROM usercoortab where create_time >= ?1 and create_time < ?2) uc",nativeQuery = true)
    ArrayList<String> getUserInLastTime(String startDate, String endDate);

    @Query(value = "SELECT * FROM usertab where mac = :mac",nativeQuery = true)
    Usertab getAllByMac(@Param("mac") String mac);

    @Query(value = "SELECT attr_Title FROM groundtab WHERE id in (SELECT ground_id  FROM favorite WHERE user_id = :id);",nativeQuery = true)
    ArrayList<String> getFavoriteByUserId(@Param("id")Integer id);

    @Query(value = "SELECT * FROM usertab where mobile_phone = :phone and password = :password",nativeQuery = true)
    Usertab findByMobilePhoneAndPassword(@Param("phone")String phone,@Param("password")String password);

    @Query(value = "SELECT * FROM usertab where mobile_phone = :phone",nativeQuery = true)
    Usertab findByMobilePhone(@Param("phone")String phone);

    @Query(value = "select * from usercoortab  where id = ?",nativeQuery = true)
    List<Usercoortab> findUUID(Integer uuid);


}
