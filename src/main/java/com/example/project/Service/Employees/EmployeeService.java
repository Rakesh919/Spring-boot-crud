package com.example.project.Service.Employees;

import com.example.project.Entity.Employee.Employees;
import com.example.project.Repository.Employee.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeRepository employeeRepository;

    public Employees addEmployee(Employees employee){
      return employeeRepository.save(employee);
    }

    public Employees getEmployee(Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    public boolean deleteEmployee(Long id){

        Employees details = employeeRepository.findById(id).orElse(null);
        if(details==null)
            return false;
         employeeRepository.deleteById(id);
         return true;
    }

    public List<Employees> fetchAllEmployees(){
        logger.info("fetch all employees service ");
        try{
            List<Employees> employeeDetails;
            employeeDetails= employeeRepository.findAll();
            return employeeDetails;
        } catch (Exception e) {
            logger.error("Exception Occurred at fetchAll employees service");
            throw new RuntimeException(e);
        }
    }
}
