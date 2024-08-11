package com.example.springbootwebtutorial.springbootwebtutorial.controllers;

import com.example.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.example.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.example.springbootwebtutorial.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.example.springbootwebtutorial.springbootwebtutorial.repository.EmployeeRepository;
import com.example.springbootwebtutorial.springbootwebtutorial.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(path="/employees")
public class EmployeeController  {

//    @GetMapping(path = "/getMethod")
//    public String getMethodEg() {
//        return "Example of get method";
//    }

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {

        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeId);
//        if(employeeDTO == null) return ResponseEntity.notFound().build();
//        return  ResponseEntity.ok(employeeDTO);
        return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1)).
                orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    @GetMapping("")
    //requestparam are optional
    public ResponseEntity<List<EmployeeDTO>> getAllEmp(@RequestParam(required = false) Integer age, @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmp){
        EmployeeDTO employeeDTO =  employeeService.saveEmp(inputEmp);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO,@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,employeeDTO));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
         boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
         if(gotDeleted) return ResponseEntity.ok(true);
         return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId,updates);
        if(employeeDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }
}
