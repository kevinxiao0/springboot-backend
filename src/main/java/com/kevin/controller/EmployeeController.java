package com.kevin.controller;

import com.kevin.exception.ResourceNotFoundException;
import com.kevin.model.Employee;
import com.kevin.respository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

//    @GetMapping("/employees/{id}")
//    public Employee getEmployeeById(@PathVariable long id){
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("error: Employee not found : "+ id));
//        return employee;
//    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error: Employee not found : "+ id));
        return ResponseEntity.ok(employee);
    }


    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        Employee e = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("error, no found"));
        e.setFirstName(employee.getFirstName());
        e.setLastName(employee.getLastName());
        e.setEmail(employee.getEmail());

        Employee updateEmployee = employeeRepository.save(e);

        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee e = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("error, no found"));

        employeeRepository.delete(e);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);


    }



}
