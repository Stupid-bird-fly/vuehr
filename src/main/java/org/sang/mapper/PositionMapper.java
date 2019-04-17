package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.sang.bean.Position;

import java.util.List;

@Mapper
public interface PositionMapper {
    public List<Position> getAllPos();
}
