package com.example.demo.service;

import com.example.demo.dto.AdditionalSettingsDTO;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ImageDTO;
import com.example.demo.models.*;
import com.example.demo.repository.*;
import com.example.demo.requests.AdditionalSettingsRequest;
import com.example.demo.requests.FilterProductRequest;
import com.example.demo.responses.GeoInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.responses.ProductResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

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
    ClientRepository clientRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AdditionalSettingsRepository additionalSettingsRepository;

    public byte[] getOneImage(Integer imageId) {
        System.out.println(imageId);
        Image image = imageRepository.findById(imageId).orElseThrow();

        String imagePathString = "C:/Users/Ерлан/OneDrive/Рабочий стол/ImageStorage/" + image.getPath();
        System.out.println(image.getPath());
        File imageFile = new File(imagePathString);
        if (!imageFile.exists()) {
            System.out.println("Изображение не найдено");
            return null;
        }

        try {
            Path imagePath = Paths.get(imagePathString);
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            System.out.println("Ошибка -- Изображение не найдено");
            return null;
        }
    }

    public List<ProductResponse> getProductByFilterRequest(FilterProductRequest filterProductRequest, String tokenAuth) {

//        filterProductRequest = FilterProductRequest.builder()
//                .category("CATEGORY1")
//                .build();


        if (filterProductRequest == null) return null;
        List<Product> allProducts;


        if (filterProductRequest.getMarketId() == null) allProducts = productRepository.findAll();
        else allProducts = productRepository.findAllByMarketId(filterProductRequest.getMarketId());

        if (filterProductRequest.getMaxPrice() != null) if (filterProductRequest.getMaxPrice() > 1) {
            for (int c = 0; c < allProducts.size(); c++) {
                if (allProducts.get(c).getPrice() > filterProductRequest.getMaxPrice()) allProducts.remove(c);
                System.out.println("удаление товара по максимальной цене");

            }
        }


        List<CategoryDTO> categoryDTOList = categoryRepository.findAllCategories();
        int[] accordanceArray = new int[allProducts.size()];

        for (int i = 0; i < allProducts.size(); i++) {
            if (filterProductRequest.getMarketName() != null)
                if (filterProductRequest.getMarketName()
                        .equals(allProducts.get(i).getMarket().getMarketName())) accordanceArray[i] = 50;
                else accordanceArray[i] = 0;


            if (filterProductRequest.getCategory() != null) {
                List<CategoryDTO> categoryDTOListSearch = categoryRepository.findByProductId(allProducts.get(i).getId());
                for (CategoryDTO ctdDto : categoryDTOListSearch) {
                    if (ctdDto.getCategory().name().equals(filterProductRequest.getCategory()))
                        accordanceArray[i] = accordanceArray[i] + 30;
                }
            }

//            if (allProducts.get(i).getCategoriesProduct()
//                    .contains(filterProductRequest.getCategory())) accordanceArray[i] = accordanceArray[i] + 30;

            if (filterProductRequest.getSearch() != null)
                if (allProducts.get(i).getNameProduct().toLowerCase()
                        .contains(filterProductRequest.getSearch().toLowerCase()))
                    accordanceArray[i] = accordanceArray[i] + 30;

            if (filterProductRequest.getMaxPrice() != null && allProducts.get(i).getPrice() != null)
                if (allProducts.get(i).getPrice() <=
                        filterProductRequest.getMaxPrice()) accordanceArray[i] = accordanceArray[i] + 10;


            if (tokenAuth != null && tokenAuth.length() > 50) {

                String emailUser = jwtService.extractUsername(tokenAuth);

                User user = userRepository.findByEmail(emailUser).orElseThrow();

                Client client = clientRepository.findByUserId(user.getId()).orElseThrow();

                accordanceArray[i] = accordanceArray[i] + countGeoAccordance(client, allProducts.get(i));

            }

            int matchCount = 0;


            if (filterProductRequest.getSettings() != null) {
                System.out.println("Заходим в условие");
                List<AdditionalSettingsRequest> additionalSettingsRequestArrayList = Arrays.asList(filterProductRequest.getSettings());
                List<AdditionalSettingsDTO> additionalSettingsListDTO = additionalSettingsRepository.findByProductId(allProducts.get(i).getId());


                for (AdditionalSettingsDTO obj1 : additionalSettingsListDTO) {
                    for (AdditionalSettingsRequest obj2 : additionalSettingsRequestArrayList) {
                        System.out.println(obj1.getCriteriaValue() + " " + obj2.getCriteriaValue());
                        System.out.println(obj1.getCriteria() + " " + obj2.getCriteria());
                        if (obj1.getCriteria().contains(obj2.getCriteria())
                                && obj1.getCriteriaValue().contains(obj2.getCriteriaValue())) {
                            System.out.println("Совпадение критериев ");
                            matchCount++;
                        }
                    }
                }
            }

            accordanceArray[i] = accordanceArray[i] + matchCount * 2;

        }

        System.out.println("Рейтинг товаров");
        for (int k : accordanceArray) {
            System.out.println(k + ", ");
        }

        List<ProductScore> productScoreList = new ArrayList<>();

        for (int i = 0; i < allProducts.size(); i++) {
            productScoreList.add(new ProductScore(allProducts.get(i), accordanceArray[i]));
        }

        Collections.sort(productScoreList, (ps1, ps2) -> ps2.getScore() - ps1.getScore());

        List<Product> sortedProductList = new ArrayList<>();

        for (ProductScore ps : productScoreList) {
            sortedProductList.add(ps.getProduct());
        }

        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product p : sortedProductList) {
//            System.out.println("Ниже информация об отсортированных продуктах");
//            printProduct(p);

            List<Integer> imagesIdList = new ArrayList<>();

            List<ImageDTO> imageListDTO = imageRepository.findByProductId(p.getId());
            for (ImageDTO imageDTO : imageListDTO) {
                imagesIdList.add(imageDTO.getId());
            }

            List<String> criteriaStringList = new ArrayList<>();
            List<String> valueStringList = new ArrayList<>();
            for (AdditionalSettingsDTO additionalSetDTO : additionalSettingsRepository.findByProductId(p.getId())) {
            //    System.out.println("Размер массива опций просматриваемого товара = " + p.getOptionsProduct().size());
                criteriaStringList.add(additionalSetDTO.getCriteriaValue());
            //    System.out.println("Добавляемое значение критерия = " + additionalSetDTO.getCriteriaValue());
                valueStringList.add(additionalSetDTO.getCriteria());
            //    System.out.println("Добавляемый критерий  = " + additionalSetDTO.getCriteria());
            }

            List<String> categories = new ArrayList<>();
            for (CategoryDTO categoryDTO : categoryRepository.findByProductId(p.getId())) {
                categories.add(categoryDTO.getCategory().name());
            }
//            System.out.println("Добавляемые категории = " + categories.size());
//
//            System.out.println("Категории просмотренного  продукта = " + categories.size());

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
                            .idImages(imagesIdList)
                            .build()

            );

        }


        return productResponseList;
    }

    public ProductResponse getProductById(Integer idProduct) {
        Product p = productRepository.findById(idProduct).orElseThrow();

        List<Integer> imagesIdList = new ArrayList<>();

        List<ImageDTO> imageListDTO = imageRepository.findByProductId(p.getId());
        for (ImageDTO imageDTO : imageListDTO) {
            imagesIdList.add(imageDTO.getId());
        }

        List<String> criteriaStringList = new ArrayList<>();
        List<String> valueStringList = new ArrayList<>();
        for (AdditionalSettingsDTO additionalSetDTO : additionalSettingsRepository.findByProductId(p.getId())) {
       //     System.out.println("Размер массива опций просматриваемого товара = " + p.getOptionsProduct().size());
            criteriaStringList.add(additionalSetDTO.getCriteriaValue());
      //      System.out.println("Добавляемое значение критерия = " + additionalSetDTO.getCriteriaValue());
            valueStringList.add(additionalSetDTO.getCriteria());
      //      System.out.println("Добавляемый критерий  = " + additionalSetDTO.getCriteria());
        }

        List<String> categories = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoryRepository.findByProductId(p.getId())) {
            categories.add(categoryDTO.getCategory().name());
        }
//        System.out.println("Добавляемые категории = " + categories.size());
//
//        System.out.println("Категории просмотренного  продукта = " + categories.size());


        return ProductResponse.builder()
                .id(p.getId())
                .nameProduct(p.getNameProduct())
                .price(p.getPrice())
                .description(p.getDescription())
                .availability(p.getAvailability().name())
                .additionalSettingsCriteriaList(criteriaStringList)
                .additionalSettingsValueList(valueStringList)
                .categoriesList(categories)
                .idImages(imagesIdList)
                .build();
    }

    private int countGeoAccordance(Client client, Product product) {
        if (client == null || product == null) return 0;

        final double EARTH_RADIUS = 6371.0;


        double lat1Rad = Math.toRadians(product.getMarket().getXCoordinate());
        double lon1Rad = Math.toRadians(product.getMarket().getYCoordinate());
        double lat2Rad = Math.toRadians(client.getXCoordinate());
        double lon2Rad = Math.toRadians(client.getYCoordinate());

        // Разница координат
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Формула гаверсинусов
        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Расстояние в километрах
        double distance = EARTH_RADIUS * c;

        System.out.println(distance);

        if (distance < 1) return 20;

        if (distance < 3) return 15;

        if (distance < 5) return 5;

        if (distance < 10) return 2;

        if (distance < 300) return 1;

        return 0;
    }


    public static void printProduct(Product product) {
        System.out.println("id: " + product.getId());
        System.out.println("nameProduct: " + product.getNameProduct());
        System.out.println("price: " + product.getPrice());
        System.out.println("description: " + product.getDescription());
        System.out.println("availability: " + product.getAvailability());

        System.out.println("imagesProduct:");
        for (Image image : product.getImagesProduct()) {
            System.out.println("  id: " + image.getId());
            // Выведите другие поля сущности Image, если таковые имеются
        }

        System.out.println("categoriesProduct:");
        for (Category category : product.getCategoriesProduct()) {
            System.out.println("  id: " + category.getId());
            // Выведите другие поля сущности Category, если таковые имеются
        }

        System.out.println("optionsProduct:");
        for (AdditionalSettings settings : product.getOptionsProduct()) {
            System.out.println("  id: " + settings.getId());
            // Выведите другие поля сущности AdditionalSettings, если таковые имеются
        }

        System.out.println("market:");
        if (product.getMarket() != null) {
            System.out.println("  id: " + product.getMarket().getId());
            // Выведите другие поля сущности Market, если таковые имеются
        }
    }


    public GeoInfoResponse getGeoInfo(Integer id) {

        Product product = productRepository.findById(id).orElseThrow();

        Market market = product.getMarket();
        System.out.println(market.getMarketName());
        System.out.println(market.getAddress());
        return new GeoInfoResponse(market.getXCoordinate().toString(), market.getYCoordinate().toString(), market.getAddress(), market.getMarketName());

    }
}


