package utn.frc.parcial.Api.Controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateSupplierDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateSupplierDTO;
import utn.frc.parcial.Api.ResponseDto.SuppliersResponseDTO;
import utn.frc.parcial.Services.SupplierService;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    private SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<SuppliersResponseDTO>> findSupplier() {
        List<SuppliersResponseDTO> supplier = supplierService.findAll();

        return supplier.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(supplier);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuppliersResponseDTO> findSupplier(@PathVariable Long id) {
        Optional<SuppliersResponseDTO> supplier = supplierService.findById(id);

        return supplier.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(supplier.get());
    }


    @PostMapping
    public ResponseEntity<SuppliersResponseDTO> addSupplier(@RequestBody @Valid CreateSupplierDTO supplier) {
        return new ResponseEntity<>(supplierService.add(supplier), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SuppliersResponseDTO> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSupplierDTO supplierDTO
    ) {
        return new ResponseEntity<>(supplierService.update(id, supplierDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSupplier(@PathVariable Long id) {
        return supplierService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
