package utn.frc.parcial.Api.Controllers;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frc.parcial.Api.RequestDto.Creates.CreateProductDTO;
import utn.frc.parcial.Api.RequestDto.Creates.CreateSupplierDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateProductDTO;
import utn.frc.parcial.Api.RequestDto.Updates.UpdateSupplierDTO;
import utn.frc.parcial.Api.ResponseDto.ProductsByCategoryAndEmplResponseDTO;
import utn.frc.parcial.Api.ResponseDto.ProductsResponseDTO;
import utn.frc.parcial.Api.ResponseDto.SuppliersResponseDTO;
import utn.frc.parcial.Entities.Product;
import utn.frc.parcial.Services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductsResponseDTO>> findProducts(){
         List<ProductsResponseDTO> products =productService.findAll();
         return products.isEmpty()
                 ?ResponseEntity.noContent().build()
                :ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> findProduct(@PathVariable Long id) {
        Optional<ProductsResponseDTO> product = productService.findById(id);

        return product.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(product.get());
    }

    @PostMapping
    public ResponseEntity<ProductsResponseDTO> addProduct(@RequestBody @Valid CreateProductDTO productDTO) {
        return new ResponseEntity<>(productService.add(productDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductDTO productDTO
    ) {
        return new ResponseEntity<>(productService.update(id, productDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        return productService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
    //En caso que pidan con request
    /* @GetMapping(params = {"artistid","genreid"})
    public ResponseEntity<?> getTracksByArtistAndGenre(@RequestParam("artistid") @Positive Long artistId,@RequestParam("genreid") @Positive Long genreId){

         @Override
    public List<Track> getTracksByArtistAndGenre(Long artistId, Long genreId) {


        artistsService.getById(artistId).orElseThrow(()->
                new RuntimeException("No existe el artista con id: "+artistId));

        List<Track> tracksfiltrados = new ArrayList<>();

        List<Album> album = albumService.getAllByArtistId(artistId);

        for (Album album1 : album) {
            List<Track> tracks = trackRepository.getTrackByAlbumIdAndGenreId(album1.getId(), genreId);
            tracksfiltrados.addAll(tracks);
        }
        return tracksfiltrados;
    }

     */

    @GetMapping(params = {"supplierId", "categoryId", "stockMin"})
    public ResponseEntity<Optional<List<ProductsByCategoryAndEmplResponseDTO>>> getProductsBySupplierAndCategoryAndStock(
            @RequestParam("supplierId") Long supplierId, @RequestParam("categoryId") Long categoryId, @RequestParam("stockMin") Integer stockMin
    ) {
        Optional<List<Product>> listaProductos = productService.getProductsBySupplierAndCategoryAndStock(supplierId, categoryId, stockMin);

        if (listaProductos.isPresent() && !listaProductos.get().isEmpty()) {
            List<ProductsByCategoryAndEmplResponseDTO> productosDTO = listaProductos.get().stream()
                    .map(product -> new ProductsByCategoryAndEmplResponseDTO(
                            product.getId(),
                            product.getProductName(),
                            product.getUnitsInStock() + product.getUnitOnOrder(),
                            product.getUnitPrice()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Optional.of(productosDTO));
        } else {
            return ResponseEntity.noContent().build();
        }
    }


}
