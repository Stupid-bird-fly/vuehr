package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.sang.bean.Department;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    List<Department> getDepByPid(Long pid);
    List<Department> getAllDeps();
}
