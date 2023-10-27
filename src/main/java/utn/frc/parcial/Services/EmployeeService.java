package utn.frc.parcial.Services;

import org.springframework.stereotype.Service;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateEmployeeDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateSupplierDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateEmployeeDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateSupplierDTO;
import utn.frc.parcial.Api.ResponseDto.EmployeesResponseDTO;
import utn.frc.parcial.Api.ResponseDto.SuppliersResponseDTO;
import utn.frc.parcial.Entities.Employee;
import utn.frc.parcial.Entities.Supplier;
import utn.frc.parcial.Repositories.EmployeesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeesRepository employeesRepository;

    public EmployeeService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public List<EmployeesResponseDTO> findAll() {
        return employeesRepository.findAll().stream()
                .map(employee -> new EmployeesResponseDTO(employee)).toList();
    }


    public Optional<EmployeesResponseDTO> findById(Long employeeId) {
        Optional<Employee> employee = employeesRepository.findById(employeeId);

        return employee.isEmpty()
                ? Optional.empty()
                : Optional.of(new EmployeesResponseDTO(employee.get()));
    }


    public EmployeesResponseDTO add(CreateEmployeeDTO createEmployeeDTO) {
        Employee employeeJefe = employeesRepository.findById(createEmployeeDTO.getReportsToiD())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Jefe %d inexistente", createEmployeeDTO.getReportsToiD())));
        Employee employee = new Employee(employeeJefe, createEmployeeDTO);
        return new EmployeesResponseDTO(employeesRepository.save(employee));
    }

    public EmployeesResponseDTO update( Long employeeId, UpdateEmployeeDTO updateEmployeeDTO) {
        Employee employeeJefe = employeesRepository.findById(updateEmployeeDTO.getReportsToiD())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Jefe %d inexistente", updateEmployeeDTO.getReportsToiD())));
        Employee employee = employeesRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Employee %d inexistente", employeeId))
        );



        return new EmployeesResponseDTO(employeesRepository.save(employee.update(updateEmployeeDTO, employeeJefe)));
    }


    public boolean deleteById(Long employeeId) {
        if (!employeesRepository.existsById(employeeId)) {
            return false;
        }

        employeesRepository.deleteById(employeeId);
        return true;
    }

}
