package com.bryant.traffic.mapper;

import com.bryant.traffic.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {

    void create(@Param("record") User record);

    void deleteById(@Param("id") Long id);
}
