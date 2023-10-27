package utn.frc.parcial.Api.Controllers;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateEmployeeDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCategoryDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateEmployeeDTO;
import utn.frc.parcial.Api.ResponseDto.CategoriesResponseDTO;
import utn.frc.parcial.Api.ResponseDto.EmployeesResponseDTO;
import utn.frc.parcial.Services.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeesResponseDTO>> findEmployees() {
        List<EmployeesResponseDTO> employees = employeeService.findAll();

        return employees.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeesResponseDTO> findEmployee(@PathVariable Long id) {
        Optional<EmployeesResponseDTO> employee = employeeService.findById(id);

        return employee.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(employee.get());
    }


    @PostMapping
    public ResponseEntity<EmployeesResponseDTO> addEmployee(@RequestBody @Valid CreateEmployeeDTO createEmployeeDTO) {
        return new ResponseEntity<>(employeeService.add(createEmployeeDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployeesResponseDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeDTO updateEmployeeDTO
            ) {
        return new ResponseEntity<>(employeeService.update(id, updateEmployeeDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
