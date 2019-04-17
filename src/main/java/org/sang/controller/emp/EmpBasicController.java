package org.sang.controller.emp;

import org.sang.bean.Employee;
import org.sang.bean.Position;
import org.sang.bean.RespBean;
import org.sang.common.EmailRunnable;
import org.sang.common.poi.PoiUtils;
import org.sang.service.DepartmentService;
import org.sang.service.EmpService;
import org.sang.service.JobLevelService;
import org.sang.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {
    @Autowired
    EmpService empService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;
    @Autowired
    ExecutorService executorService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @RequestMapping(value = "/emp",method = RequestMethod.GET)
    public Map<String,Object> getEmployeeByPage(
            @RequestParam(defaultValue = "1")Integer page,
            @RequestParam(defaultValue = "10")Integer size,
            @RequestParam(defaultValue = "")String keywords,
            Long politicId,Long nationId,Long posId,
            Long jobLevelId,String engageFrom,
            Long departmentId,String beginDateScope){
        Map<String,Object> map=new HashMap<>();
        List<Employee> employeeByPage=empService.getEmployeeByPage(page,size,keywords,politicId,nationId,posId,jobLevelId,engageFrom,departmentId,beginDateScope);
        Long count=empService.getCountByKeywords(keywords,politicId,nationId,posId,jobLevelId,engageFrom,departmentId,beginDateScope);
        map.put("emps",employeeByPage);
        map.put("count",count);
        return map;
    }
    @RequestMapping(value = "/basicdata", method = RequestMethod.GET)
    public Map<String, Object> getAllNations() {
        Map<String, Object> map = new HashMap<>();
        map.put("nations", empService.getAllNations());
        map.put("politics", empService.getAllPolitics());
        map.put("deps", departmentService.getDepByPid(-1L));
        map.put("positions", positionService.getAllPos());
        map.put("joblevels", jobLevelService.getAllJobLevels());
        map.put("workID", String.format("%08d", empService.getMaxWorkID() + 1));
        return map;
    }
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    public RespBean addEmp(Employee employee){
        if(empService.addEmp(employee)==1){
            List<Position> allPos=positionService.getAllPos();
            for(Position allPo:allPos){
                if(allPo.getId()==employee.getPosId()){
                    employee.setPosName(allPo.getName());
                }
            }
            executorService.execute(new EmailRunnable(employee,javaMailSender,templateEngine));
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }
    @RequestMapping("/maxWorkID")
    public String maxWorkID() {
        return String.format("%08d", empService.getMaxWorkID() + 1);
    }

    @RequestMapping(value = "/exportEmp",method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportEmp(){
        return PoiUtils.exportEmp2Excel(empService.getAllEmployees());
    }
    @RequestMapping(value = "/importEmp",method = RequestMethod.POST)
    public RespBean importEmp(MultipartFile file){
        List<Employee> emps=PoiUtils.importEmp2List(file,
                empService.getAllNations(),empService.getAllPolitics(),
                departmentService.getAllDeps(),positionService.getAllPos(),
                jobLevelService.getAllJobLevels());
        if(empService.addEmps(emps)==emps.size()){
            return RespBean.ok("导入成功");
        }
        return RespBean.error("导入失败");
    }
}
