package grupo6.demo.service;

import grupo6.demo.model.Category;
import grupo6.demo.model.Product;
import grupo6.demo.repository.CategoryRepository;
import grupo6.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AWSS3ServiceImpl serviceAWS;

    public Category create (Category category, MultipartFile file) throws  Exception{
        if(categoryExist(category.getTitle())){
            return getCategoryByTitle(category.getTitle());
        } else {
            if(!file.isEmpty()){
                String imageURL = serviceAWS.uploadFile(file);
                category.getImage().setUrl(imageURL);
                imageRepository.save(category.getImage());
            }
            repository.save(category);
            return category;

        }
    }

    public List<Category> getAll() {
        List<Category> listCategories = new ArrayList<>();
        for (Category category : repository.findAll()) {
            listCategories.add(category);
        }
        return listCategories;
    }

    public boolean categoryExist(String title) {
        return repository.getCategoryByTitle(title).size() >= 1;
    }

    public Category getCategoryByTitle(String title){
        return repository.getCategoryByTitle(title).get(0);
    }

    public void createWithoutImages(Category category) throws Exception{
            imageRepository.save(category.getImage());
            repository.save(category);

    }

    public Category findById(Integer id) throws Exception {
        Optional<Category> category = repository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new Exception("Category not found.");
        }
    }

    public List <Product> findByCategory(Integer id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception("Category does not exist.");
        }

        List<Product> productList = repository.findByCategory(id);
        if (productList.isEmpty()) {
            throw new Exception("No product found in this category.");
        }

        return productList;
    }

    public List <Product> findByCategoryTitle(String categoryTitle) throws Exception {
        if (repository.getCategoryByTitle(categoryTitle).isEmpty()) {
            throw new Exception("Category does not exist.");
        }

        List<Product> productList = repository.findByCategoryTitle(categoryTitle);

        return productList;
    }





    public void delete(Integer id) throws Exception {
        Optional<Category> category = repository.findById(id);

        if (category.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new Exception("The category does not exist.");
        }
    }

}
