package com.example.project.controller.Employee;

import com.example.project.Entity.Employee.Employees;
import com.example.project.Service.Employees.EmployeeService;
import com.example.project.dto.response.ErrorResponse;
import com.example.project.dto.response.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Employees createDto){
        logger.info("create controller method start");
        try{
           return ResponseEntity.ok(new ResponseDto("Details added successfully","SUC-200",employeeService.addEmployee(createDto)));

        }catch(Exception e){
            logger.error("Exception occurred at create controller method of employee ",e);
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/get")
    public ResponseEntity<?> read(@RequestParam Long id){
        logger.info("read controller method starts");
        try{

            Employees isExist = employeeService.getEmployee(id);
            if(isExist==null){
                logger.error("Details not found for this ID {}",id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Details not found ","NOT-404"));
            }

            return ResponseEntity.ok(new ResponseDto("Details fetched successfully","SUC-200",isExist));

        }catch(Exception e){
            logger.error("Exception occurred at read controller method of employee",e);
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        logger.error("delete controller method starts");

        try{
            boolean isDeleted = employeeService.deleteEmployee(id);
            if(!isDeleted) {
                logger.error("Details not found for ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Details not found ", "NOT-404"));
            }
            return ResponseEntity.ok(new ResponseDto("User Deleted Successfully ","SUC-200",true));

        }catch(Exception e){
            logger.error("Exception occurred at delete controller method",e);
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> fetchAllEmployeesController(){
        logger.info("fetch all employees controller method start");
        try{
            return ResponseEntity.ok(new ResponseDto("All Employees Fetched Successfully","SUC-200",employeeService.fetchAllEmployees()));
        }catch(Exception e){
            logger.error("Exception Occurred at fetchAll employees controller method",e);
            throw new RuntimeException(e);
        }
    }
}
