package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.bean.Employee;
import org.sang.bean.Nation;
import org.sang.bean.PoliticsStatus;
import org.sang.bean.Position;

import java.util.Date;
import java.util.List;

@Mapper
public interface EmpMapper {
    List<Employee> getEmployeeByPage(@Param("start") Integer start,
                                     @Param("size") Integer size,
                                     @Param("keywords") String keywords,
                                     @Param("politicId") Long politicId,
                                     @Param("nationId") Long nationId,
                                     @Param("posId") Long posId,
                                     @Param("jobLevelId") Long jobLevelId,
                                     @Param("engageForm") String engageForm,
                                     @Param("departmentId") Long departmentId,
                                     @Param("startBeginDate") Date startBeginDate,
                                     @Param("endBeginDate") Date endBeginDate);
    Long getCountByKeywords(@Param("keywords") String keywords,
                            @Param("politicId") Long politicId,
                            @Param("nationId") Long nationId,
                            @Param("posId") Long posId, @Param("jobLevelId") Long jobLevelId,
                            @Param("engageForm") String engageForm,
                            @Param("departmentId") Long departmentId,
                            @Param("startBeginDate") Date startBeginDate,
                            @Param("endBeginDate") Date endBeginDate);
    int addEmp(Employee employee);
    List<Nation> getAllNations();
    List<PoliticsStatus> getAllPolitics();
    Long getMaxWorkID();
    int addEmps(@Param("emps") List<Employee> emps);
}
