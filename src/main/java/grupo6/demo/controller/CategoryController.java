package grupo6.demo.controller;

import grupo6.demo.model.Category;
import grupo6.demo.model.Product;
import grupo6.demo.service.CategoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @PostMapping("/categorias")
    public ResponseEntity<Category> createCategory (@ModelAttribute Category category, @RequestParam(value = "file") MultipartFile file) {
        ResponseEntity response;
        try {
            Category newCategory = service.create(category, file);
            response = ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }
    @GetMapping
    public ResponseEntity<Category> listCategories(){
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") Integer id) throws Exception {
        ResponseEntity response;
        try {
            Category category = service.findById(id);
            response = ResponseEntity.status(HttpStatus.CREATED).body(category);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return response;
    }

    @GetMapping("byCategory/{id}")
    public ResponseEntity <?> findByCategory(@PathVariable("id") Integer id) throws Exception {
        ResponseEntity response;

        try {
            List productList = service.findByCategory(id);
            response = ResponseEntity.status(HttpStatus.CREATED).body(productList);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return response;
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteById(@PathVariable Integer id) {

        ResponseEntity response;

        try {
            service.delete(id);
            response = ResponseEntity.ok().body("Category removed.");
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body("The category is in use.");
        }

        return response;
    }

    @GetMapping("byCategoryTitle")
    public ResponseEntity <?> findByCategoryTitle(@RequestParam(value = "categoryTitle") String categoryTitle) throws Exception {
        ResponseEntity response;

        try {
            List productList = service.findByCategoryTitle(categoryTitle);
            response = ResponseEntity.status(HttpStatus.CREATED).body(productList);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return response;
    }
}
