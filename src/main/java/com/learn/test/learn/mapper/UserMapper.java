package com.learn.test.learn.mapper;
import com.learn.test.learn.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    @Insert("insert into public.user(name,account_id,token,gmt_create,gmt_modified)values(#{name},#{account_Id},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
