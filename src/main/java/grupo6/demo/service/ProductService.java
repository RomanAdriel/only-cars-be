package grupo6.demo.service;
import grupo6.demo.model.Category;
import grupo6.demo.model.Feature;
import grupo6.demo.model.Image;
import grupo6.demo.model.Product;
import grupo6.demo.repository.ImageRepository;
import grupo6.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AWSS3ServiceImpl serviceAWS;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private ReservationService reservationService;

    public List<Product> getAll() {
        List<Product> listProducts = new ArrayList<>();
        for (Product product : repository.findAll()) {
            listProducts.add(product);
        }
        return listProducts;
    }

    public Product create(Product product, List<MultipartFile> files, MultipartFile categoryFile) throws Exception {
        if (productExist(product.getName())) {
            throw new Exception("There is already a registered product with the entered name.");
        } else {
            if (!files.get(0).isEmpty()) {
                for (MultipartFile file : files) {
                    String imageURL = serviceAWS.uploadFile(file);
                    Image image = new Image();
                    image.setUrl(imageURL);
                    product.getImages().add(image);
                    imageRepository.save(image);
                }
            }
            categoryService.create(product.getVehicule_category(), categoryFile);

            List<Feature> dbAddedFeatures = new ArrayList<>();

            for (Feature productFeature : product.getAddedFeatures()) {
                Feature dbProductFeature = featureService.getFeatureByTitle(productFeature.getTitle());
                if (dbProductFeature != null) {
                    dbAddedFeatures.add(dbProductFeature);
                } else {
                    featureService.create(productFeature);
                }
            }
            product.getAddedFeatures().clear();
            product.getAddedFeatures().addAll(dbAddedFeatures);
            repository.save(product);
            return product;
        }
    }


    public Product findById(Integer id) throws Exception {
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new Exception("Product not found.");
        }
    }


    public List<Product> findByNameOrDescription(String name, String description) throws Exception {

        if (name.isEmpty() && description.isEmpty()) {
            throw new Exception("Specify the name or description.");
        }

        List<Product> productList = repository.findByNameOrDescription(name, description);

        return productList;
    }

    public List<Product> findByCategoryName(String category) throws Exception {
        if(category.isEmpty()){
            throw new Exception("Specify the category");
        }

        List <Product> productList = repository.findByCategoryName(category);

        return productList;
    }


    public List<Product> findByCategoryOrFeature(String category, String feature) throws Exception {
        if(category.isEmpty() && feature.isEmpty()) {
            throw  new Exception("Specify the category or feature");
        }

        List <Product> productList = repository.findByCategoryOrFeature(category, feature);

        return  productList;
    }



    public boolean productExist(String name) {
        return repository.getProductByName(name).size() >= 1;
    }

    public void delete(Integer id) throws Exception {
        Optional<Product> product = repository.findById(id);

        if (product.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new Exception("The product does not exist.");
        }
    }

    public Product createWithoutCategory(Product product, List<MultipartFile> files) throws Exception {

        if (!files.get(0).isEmpty()) {
            for (MultipartFile file : files) {
                String imageURL = serviceAWS.uploadFile(file);
                Image image = new Image();
                image.setUrl(imageURL);
                product.getImages().add(image);
                imageRepository.save(image);
            }
            repository.save(product);
        }
        return product;
    }

    public Product update(Product product, List<MultipartFile> files, MultipartFile categoryFile, Integer id) throws Exception {

        Product dbProduct = repository.findById(id).orElse(null);
        List<Product> dbProductByName = repository.getProductByName(product.getName());

        if (dbProduct != null) {
            if (dbProductByName.isEmpty() || dbProductByName.get(0).getId().equals(id)) {
                if (!files.get(0).isEmpty()) {
                    for (MultipartFile file : files) {
                        String imageURL = serviceAWS.uploadFile(file);
                        Image image = new Image();
                        image.setUrl(imageURL);
                        product.getImages().add(image);
                        imageRepository.save(image);
                    }
                    product.setId(dbProduct.getId());
                    categoryService.createWithoutImages(product.getVehicule_category());

                    List<Feature> dbAddedFeatures = new ArrayList<>();

                    if (product.getAddedFeatures() != null) {

                        for (Feature productFeature : product.getAddedFeatures()) {
                            Feature dbProductFeature = featureService.getFeatureByTitle(productFeature.getTitle());
                            if (dbProductFeature != null) {
                                dbAddedFeatures.add(dbProductFeature);
                            } else {
                                featureService.create(productFeature);
                            }
                        }

                        product.getAddedFeatures().clear();
                        product.getAddedFeatures().addAll(dbAddedFeatures);

                    }

                    repository.save(product);
                }
            } else {
                throw new Exception("There is already a registered product with the entered name.");
            }
        } else {
            throw new Exception("The product with ID = " + id + " does not exist");
        }

        return product;
    }

    public Product partialUpdate(Product product, Integer id) throws Exception {

        Product dbProduct = repository.findById(id).orElse(null);
        List<Product> dbProductByName = repository.getProductByName(product.getName());

        Category dbCategory = categoryService.getCategoryByTitle(product.getVehicule_category().getTitle());

        if (dbProduct != null) {
            if (dbProductByName.isEmpty() || dbProductByName.get(0).getId().equals(id)) {
                product.setId(dbProduct.getId());
                List<Feature> dbAddedFeatures = new ArrayList<>();
                if (product.getAddedFeatures() != null) {
                    for (Feature productFeature : product.getAddedFeatures()) {
                        Feature dbProductFeature = featureService.getFeatureByTitle(productFeature.getTitle());
                        if (dbProductFeature != null) {
                            dbAddedFeatures.add(dbProductFeature);
                        } else {
                            featureService.create(productFeature);
                        }
                    }
                    product.getAddedFeatures().clear();
                    product.getAddedFeatures().addAll(dbAddedFeatures);
                }

                product.getVehicule_category().setId(dbCategory.getId());
                categoryService.createWithoutImages(product.getVehicule_category());

                repository.save(product);
            } else {
                throw new Exception("There is already a registered product with the entered name.");
            }
        } else {
            throw new Exception("The product with ID = " + id + " does not exist");
        }

        return product;
    }

    public List<Product> findByCategoryOrFeatureAndReservation(String category, String feature, LocalDate startDate, LocalDate endDate) throws Exception {
        List <Product> productList;
        System.out.println("Iniciando flujo de findByCategoryOrFeatureAndReservation");
        if(category.isEmpty() && feature.isEmpty()) {
            throw  new Exception("Specify the category or feature");
        }
        System.out.println("Paso el if de category o feature empty");
        if(startDate == null || endDate == null){
            System.out.println("algun campo de date es null");
            System.out.println(startDate);
            productList = repository.findByCategoryOrFeature(category, feature);
        } else{
            System.out.println("los campos de date estaban bien");
            List<Product> unfilteredList = repository.findByCategoryOrFeature(category, feature);
            productList = new ArrayList<>();
            for (Product product:unfilteredList) {
                System.out.println("Paseando por producto");
                if(reservationService.isDateRangeAvailableForReservationByDates(startDate, endDate, product.getId())){
                    System.out.println("El producto cumple con las fechas, agregando a lista.");
                    productList.add(product);
                }
            }
        }
        System.out.println("Flujo finalizado");
        return  productList;
    }

}
