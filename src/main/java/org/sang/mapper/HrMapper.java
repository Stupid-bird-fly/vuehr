package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.bean.Hr;
import org.sang.bean.Role;

import java.util.List;

@Mapper
public interface HrMapper {
    Hr loadUserByUsername(String username);
    List<Role> getRolesByHrId(Integer id);
    List<Hr> getAllHr(@Param("currentId") Long currentId);
}
