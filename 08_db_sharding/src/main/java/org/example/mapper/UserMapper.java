package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.example.model.UserEntity;

import java.util.List;

public interface UserMapper {
    @Select("select * from t_user")
    List<UserEntity> getAll();

    @Select("select * from t_user where id = #{id}")
    UserEntity getOne(long id);

    @Insert("insert into t_user(id, username, passwd, telephone) values(#{id}, #{username}, #{passwd}, #{telephone})")
    void insert(UserEntity user);
}
