package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.sang.bean.JobLevel;

import java.util.List;

@Mapper
public interface JobLevelMapper {
    public List<JobLevel> getAllJobLevels();
}
