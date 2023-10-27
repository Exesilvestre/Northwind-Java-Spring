package utn.frc.parcial.Services;

import org.springframework.stereotype.Service;
import utn.frc.parcial.Api.Exception.ResourceNotFoundException;
import utn.frc.parcial.Api.RequestDto.Creates.CreateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateSupplierDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateCustomerDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateSupplierDTO;
import utn.frc.parcial.Api.ResponseDto.CustomerResponseDTO;
import utn.frc.parcial.Api.ResponseDto.SuppliersResponseDTO;
import utn.frc.parcial.Entities.Customer;
import utn.frc.parcial.Entities.Supplier;
import utn.frc.parcial.Repositories.SuppliersRepository;

import java.util.List;
import java.util.Optional;


@Service
public class SupplierService {


    private SuppliersRepository suppliersRepository;

    public SupplierService(SuppliersRepository suppliersRepository) {
        this.suppliersRepository = suppliersRepository;
    }

    public List<SuppliersResponseDTO> findAll() {
        return suppliersRepository.findAll().stream().map(customer -> new SuppliersResponseDTO(customer)).toList();
    }


    public Optional<SuppliersResponseDTO> findById(Long supplierId) {
        Optional<Supplier> supplier = suppliersRepository.findById(supplierId);

        return supplier.isEmpty()
                ? Optional.empty()
                : Optional.of(new SuppliersResponseDTO(supplier.get()));
    }


    public SuppliersResponseDTO add(CreateSupplierDTO createSupplierDTO) {
        Supplier supplier = new Supplier(createSupplierDTO);
        return new SuppliersResponseDTO(suppliersRepository.save(supplier));
    }

    public SuppliersResponseDTO update(Long supplierId, UpdateSupplierDTO updateSupplierDTO) {
        Supplier supplier = suppliersRepository.findById(supplierId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Supplier %d inexistente", supplierId))
        );


        return new SuppliersResponseDTO(suppliersRepository.save(supplier.update(updateSupplierDTO)));
    }


    public boolean deleteById(Long supplierId) {
        if (!suppliersRepository.existsById(supplierId)) {
            return false;
        }

        suppliersRepository.deleteById(supplierId);
        return true;
    }
}
