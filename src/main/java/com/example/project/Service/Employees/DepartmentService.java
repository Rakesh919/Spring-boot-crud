package com.example.project.Service.Employees;

import com.example.project.Entity.Employee.Department;
import com.example.project.Repository.Employee.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department addDepartment(Department department){
        logger.info("addDepartment service method");
        try{
            return departmentRepository.save(department);
        }catch(Exception e){
            logger.info("Exception occurred at addDepartment service",e);
            throw new RuntimeException(e);
        }
    }

    public Department getDepartment(Long id){
        logger.info("getDepartment service method");
        try {
            return departmentRepository.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Exception Occurred at get department ",e);
            throw new RuntimeException(e);
        }
        }

    public boolean deleteDepartment(Long id){
        logger.info("delete department service method");
        try{
            Department details = departmentRepository.findById(id).orElse(null);

            if(details==null) return false;

            departmentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error("Exception Occurred at deleteDepartment ",e);
            throw new RuntimeException(e);
        }
    }

    public List<Department> fetchAllDepartments(){
        logger.info("fetch all departments method start");
      try{
          List<Department> details;
          details = departmentRepository.findAll();
          return details;
      } catch (Exception e) {
          logger.error("Exception Occurred at fetchAll Departments ",e);
          throw new RuntimeException(e);
      }
    }
}
