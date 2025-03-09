package com.example.project.controller.Employee;

import com.example.project.Entity.Employee.Department;
import com.example.project.Service.Employees.DepartmentService;
import com.example.project.dto.response.ErrorResponse;
import com.example.project.dto.response.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<?> createDepartment(@RequestBody Department createDto){
        logger.info("createDepartment controller method starts ");
        try{
            return ResponseEntity.ok(new ResponseDto("Department added successfully","SUC-200",departmentService.addDepartment(createDto)));

        }catch(Exception e){
            logger.error("Exception occurred at createDepartment controller method",e);
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/get")
    public ResponseEntity<?> getDepartment(@RequestParam Long id){
        logger.info("getDepartment controller method starts ");
        try{
            Department details = departmentService.getDepartment(id);
            if(details==null){
                logger.warn("Details not found for this ID {}",id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Details not found ","NOT-404"));
            }

            return ResponseEntity.ok(new ResponseDto("Details Fetched successfully ","SUC-200",details));

        }catch(Exception e){
            logger.error("Exception occurred at getDepartment controller method",e);
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDepartment(@RequestParam Long id){
        logger.info("deleteDepartment controller method starts");
        try{

            boolean isDeleted = departmentService.deleteDepartment(id);
            if(!isDeleted){
                logger.warn("Details not found for ID : {}",id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Details not found ","NOT-404"));
            }
            return ResponseEntity.ok(new ResponseDto("Department deleted successfully","SUC-200",true));
        }catch(Exception e){
            logger.error("Exception occurred at deleteDepartment controller method",e);
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDepartmentsList(){
        logger.info("get all departments list controller method");
        try{
            return ResponseEntity.ok(new ResponseDto("Department list fetched successfully","SUC-200",departmentService.fetchAllDepartments()));
        } catch (Exception e) {
            logger.error("Exception Occurred at get all departments list",e);
            throw new RuntimeException(e);
        }
    }

}
