package grupo6.demo.controller;
import grupo6.demo.model.Product;
import grupo6.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping("/productos")
    public ResponseEntity<Product> createProduct(@ModelAttribute Product product, @RequestParam(value = "files") List<MultipartFile> files, @RequestParam(value = "file") MultipartFile file) {
        ResponseEntity response;
        try {
            Product newProduct = service.create(product, files, file);
            response = ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }

    @GetMapping
    public ResponseEntity<Product> listProduct() {
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Integer id) throws Exception {
        ResponseEntity response;
        try {
            Product producto = service.findById(id);
            response = ResponseEntity.status(HttpStatus.OK).body(producto);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteById(@PathVariable Integer id) {

        ResponseEntity response;

        try {
            service.delete(id);
            response = ResponseEntity.ok().body("Removed product");
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }

        return response;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct2(@ModelAttribute Product product, @RequestParam(value = "files") List<MultipartFile> files) {
        ResponseEntity response;
        try {
            Product newProduct = service.createWithoutCategory(product, files);
            response = ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateById(@ModelAttribute Product product,
                                              @RequestParam(value = "files") List<MultipartFile> files,
                                              @RequestParam(value = "file") MultipartFile file,
                                              @PathVariable Integer id) {

        ResponseEntity response;

        try {
            Product updatedProduct = service.update(product, files, file, id);
            response = ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateById(@ModelAttribute Product product,
                                                     @PathVariable Integer id) {

        ResponseEntity response;

        try {
            Product updatedProduct = service.partialUpdate(product, id);
            response = ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }

        return response;
    }

    @GetMapping("/byNameOrDescription")
    public ResponseEntity<?> findByNameOrDescription(@RequestParam(name = "name", required = true) String name, @RequestParam(name = "description", required = false) String description) throws Exception {
        ResponseEntity response;

        try {
            List productList = service.findByNameOrDescription(name, description);
            response = ResponseEntity.status(HttpStatus.OK).body(productList);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return response;

    }

    @GetMapping("/byCategoryName")
    public ResponseEntity<?> findByCategoryName(@RequestParam(name = "category", required = true) String category) throws Exception {
        ResponseEntity response;

        try {
            List productList = service.findByCategoryName(category);
            response = ResponseEntity.status(HttpStatus.OK).body(productList);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return response;

    }

    @GetMapping("/byCategoryOrFeature")
    public ResponseEntity<List> findByCategoryOrFeature(@RequestParam(name = "category", required = true) String category, @RequestParam(name = "feature", required = false) String feature) throws Exception {
        ResponseEntity response;

        try {
            List productList = service.findByCategoryOrFeature(category, feature);
            response = ResponseEntity.status(HttpStatus.OK).body(productList);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return response;
    }

    @GetMapping("/byCategoryOrFeatureAndReservation")
    public ResponseEntity<List> findByCategoryOrFeatureAndReservation(@RequestParam(name = "category", required = false)String category,
                                                                   @RequestParam(name = "feature", required = false) String feature,
                                                                   @RequestParam(name = "startDate", required = false) LocalDate startDate,
                                                                   @RequestParam(name = "endDate", required = false) LocalDate endDate) throws Exception {
        ResponseEntity response;
        try {
            List productList = service.findByCategoryOrFeatureAndReservation(category, feature, startDate, endDate);
            response = ResponseEntity.status(HttpStatus.OK).body(productList);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }
}
