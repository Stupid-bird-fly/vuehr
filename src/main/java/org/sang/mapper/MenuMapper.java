package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.sang.bean.Menu;

import java.util.List;

@Mapper
public interface MenuMapper {
    public List<Menu> getAllMenu();
    List<Menu> getMenusByHrId(Long hrId);
}
