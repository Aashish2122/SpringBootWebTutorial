package com.example.springbootwebtutorial.springbootwebtutorial.services;

import com.example.springbootwebtutorial.springbootwebtutorial.dto.EmployeeDTO;
import com.example.springbootwebtutorial.springbootwebtutorial.entities.EmployeeEntity;
import com.example.springbootwebtutorial.springbootwebtutorial.exceptions.ResourceNotFoundException;
import com.example.springbootwebtutorial.springbootwebtutorial.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public EmployeeService(EmployeeRepository employeeRepository,ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long employeeId) {
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
//        return employeeEntity.map(employeeEntity1 -> mapper.map(employeeEntity1,EmployeeDTO.class));
        return employeeRepository.findById(employeeId).map(employeeEntity -> mapper.map(employeeEntity,EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities =  employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> mapper.map(employeeEntity,EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO saveEmp(EmployeeDTO inputEmp) {
        EmployeeEntity toSaveEntity = mapper.map(inputEmp,EmployeeEntity.class);
        EmployeeEntity employeeEntity =  employeeRepository.save(toSaveEntity);
        return mapper.map(employeeEntity,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        isExistByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = mapper.map(employeeDTO,EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        //If the ID is present in the database then it updates it else a new entity is created.
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return mapper.map(savedEmployeeEntity,EmployeeDTO.class);

    }

    public boolean isExistByEmployeeId(Long employeeId){
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists) throw new ResourceNotFoundException("Employee not found with id " + employeeId);
        return true;

    }

    public boolean deleteEmployeeById(Long employeeId) {
            isExistByEmployeeId(employeeId);
            employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        isExistByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElse(null);
        updates.forEach((key,value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });
       return  mapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }
}
