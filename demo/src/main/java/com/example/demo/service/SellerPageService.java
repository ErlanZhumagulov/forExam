package com.example.demo.service;

import com.example.demo.models.Market;
import com.example.demo.models.Seller;
import com.example.demo.models.User;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.SellerRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.requests.AddMarketRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerPageService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    MarketRepository marketRepository;

    public String saveImage(MultipartFile file) {
        // Указываете путь к директории сохранения файла
        String uploadDir = "";

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

    public void addMarket(String tokenAuth, AddMarketRequest addMarketRequest) {

        String emailUser = jwtService.extractUsername(tokenAuth);

        User user = userRepository.findByEmail(emailUser).orElseThrow();

        Seller seller = sellerRepository.findByUserId(Long.valueOf(user.getId()))
                .orElseThrow();

        Market market = Market.builder().
                marketName(addMarketRequest.getMarketName())
                .marketDescription(addMarketRequest.getMarketDescription())
                .address(addMarketRequest.getAddress())
                .seller(seller)
                .yCoordinate(addMarketRequest.getY())
                .xCoordinate(addMarketRequest.getX())
                .build();

        marketRepository.save(market);

    }

    public List<Market> getMarkets(String tokenAuth) {
    //    System.out.println("Собираю магазины");

        String emailUser = jwtService.extractUsername(tokenAuth);

        User user = userRepository.findByEmail(emailUser).orElseThrow();
    //    System.out.println("Юзер нашелся");
        Seller seller = sellerRepository.findByUserId(Long.valueOf(user.getId()))
                .orElseThrow();
    //    System.out.println("Продавец нашелся нашелся");
        List<Market> markets = marketRepository.findAllBySellerId(seller.getId());
    //    System.out.println("Магазины собрались");

        return markets;

    }
}
