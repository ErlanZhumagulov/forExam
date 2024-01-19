package com.example.demo.controllers;

import com.example.demo.models.Market;
import com.example.demo.requests.AddMarketRequest;
import com.example.demo.service.SellerPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/page-seller")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class PageSellerController {

    @Autowired
    SellerPageService sellerPageService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> sayHello() {

        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @PostMapping("add_market")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addMarket(@RequestHeader("Authorization") String tokenAuth,
                                            @RequestBody AddMarketRequest addMarketRequest) {

        System.out.println("market запрос");
        sellerPageService.addMarket(tokenAuth, addMarketRequest);


        return ResponseEntity.ok("add Market completed ");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestHeader("Authorization") String tokenAuth) {
        System.out.println(tokenAuth);
        sellerPageService.saveImage(file);
        return ResponseEntity.ok("Upload img");
    }

    @GetMapping("get_markets")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Market>> getMarkets(@RequestHeader("Authorization") String tokenAuth) {

        System.out.println("get markets запрос");
        List<Market> markets = sellerPageService.getMarkets(tokenAuth);


        return ResponseEntity.ok(markets);
    }

}

