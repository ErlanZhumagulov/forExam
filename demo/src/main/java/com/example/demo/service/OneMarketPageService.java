package com.example.demo.service;

import com.example.demo.models.*;
import com.example.demo.models.enums.Availability;
import com.example.demo.models.enums.ECategory;
import com.example.demo.repository.*;
import com.example.demo.requests.AdditionalSettingsRequest;
import com.example.demo.requests.UpdateProductRequest;
import com.example.demo.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OneMarketPageService {

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    MarketRepository marketRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AdditionalSettingsRepository additionalSettingsRepository;

    public Integer addProduct(Integer id, String nameProduct, Integer price, String token, MultipartFile image) {


        String emailUser = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(emailUser).orElseThrow();

        Seller seller = sellerRepository.findByUserId(Long.valueOf(user.getId()))
                .orElseThrow();

        List<Market> markets = marketRepository.findAllBySellerId(seller.getId());

        Market market = marketRepository.findById(id).orElseThrow();

        if (!markets.contains(market)) return -1;

        Product product = Product.builder()
                .market(market)
                .availability(Availability.YES)
                .price(price)
                .nameProduct(nameProduct)
                .build();

        productRepository.save(product);

        System.out.println("Сохраняем  товар");


        Image imageForProduct = Image.builder()
                .path(saveImage(image))
                .product(product)
                .build();
        imageRepository.save(imageForProduct);

        return product.getId();

    }


    public String saveImage(MultipartFile file) {
        // Указываете путь к директории сохранения файла
        String uploadDir = "C:/Users/Ерлан/OneDrive/Рабочий стол/ImageStorage/";

        // Создаете уникальное имя файла для сохранения
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        // Создаете путь к файлу в директории сохранения
        Path filePath = Path.of(uploadDir, fileName);

        // Сохраняете файл в указанную директорию
        try {
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    public List<ProductResponse> getAllProducts() {


        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponseList = new ArrayList<>();

        for (Product p : products) {

            List<Integer> imagesIdList = new ArrayList<>();

            List<Image> imageList = imageRepository.findAllByProductId(p.getId());
            for (Image image : imageList) {
                imagesIdList.add(image.getId());
            }
            List<String> criteriaStringList = new ArrayList<>();
            List<String> valueStringList = new ArrayList<>();
            for (AdditionalSettings additionalSet: p.getOptionsProduct()) {
                criteriaStringList.add(additionalSet.getCriteriaValue());
                valueStringList.add(additionalSet.getCriteria());
            }

            List<String> categories = new ArrayList<>();
            for (Category category: p.getCategoriesProduct()) {
                categories.add(String.valueOf(category.getCategory()));
            }


            productResponseList.add(
                    ProductResponse.builder()
                            .id(p.getId())
                            .nameProduct(p.getNameProduct())
                            .price(p.getPrice())
                            .description(p.getDescription())
                            .availability(p.getAvailability().name())
                            .additionalSettingsCriteriaList(criteriaStringList)
                            .additionalSettingsValueList(valueStringList)
                            .categoriesList(categories)
                            .idImages(new ArrayList<>(imagesIdList))
                            .build()

            );





        }


        return productResponseList;
    }

    public void updateProduct(UpdateProductRequest updateProductRequest, String token) {


        String emailUser = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(emailUser).orElseThrow();

        Seller seller = sellerRepository.findByUserId(Long.valueOf(user.getId()))
                .orElseThrow();



        List<Market> markets = marketRepository.findAllBySellerId(seller.getId());

        System.out.println(updateProductRequest.getIdAddedProduct());

        Product product = productRepository.findById(updateProductRequest.getIdAddedProduct()).orElseThrow();
        Market marketWithUpdateProduct = product.getMarket();

        if (!markets.contains(marketWithUpdateProduct)) return;




        product.setPrice(updateProductRequest.getPrice());
        product.setDescription(updateProductRequest.getDescription());
        product.setAvailability(Availability.valueOf(updateProductRequest.getAvailability()));

        productRepository.save(product);

        for (AdditionalSettingsRequest addSettings : updateProductRequest.getAdditionalSettingsRequests()) {

            AdditionalSettings addSetting = AdditionalSettings.builder()
                    .product(product)
                    .criteria(addSettings.getCriteria())
                    .criteriaValue(addSettings.getCriteriaValue())
                    .build();

            additionalSettingsRepository.save(addSetting);

        }

        for (String categoryString : updateProductRequest.getCategories()) {
            try {
                Category category = Category.builder()
                        .product(product)
                        .category(ECategory.valueOf(categoryString))
                        .build();

                categoryRepository.save(category);
            }
            catch (IllegalArgumentException e){
                System.out.println("Не удалось найти соответствующую категорию");
            }

        }

    }


//    private MultipartFile getImage(String nameFile) {
//
//        File file = new File("src\\main\\resources\\static\\" + nameFile);
//
//
//        }
//    }


}
