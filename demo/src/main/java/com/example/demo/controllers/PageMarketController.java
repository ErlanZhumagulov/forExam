package com.example.demo.controllers;

import com.example.demo.models.AdditionalSettings;
import com.example.demo.models.Category;
import com.example.demo.models.Market;
import com.example.demo.requests.AddMarketRequest;
import com.example.demo.requests.AdditionalSettingsRequest;
import com.example.demo.requests.FilterProductRequest;
import com.example.demo.requests.UpdateProductRequest;
import com.example.demo.responses.ProductResponse;
import com.example.demo.service.OneMarketPageService;
import com.example.demo.service.ProductService;
import com.example.demo.service.SellerPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/page-market")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class PageMarketController {

    @Autowired
    OneMarketPageService oneMarketPageService;

    @Autowired
    ProductService productService;


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Integer> addProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("price") Integer price,
            @RequestParam("id") Integer id,
            @RequestHeader("Authorization") String token) {

        Integer idAddedProduct = oneMarketPageService.addProduct(id, name, price, token, image);


        return ResponseEntity.ok(idAddedProduct);
    }


    @PostMapping("/update-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateProduct(@RequestBody UpdateProductRequest updateProductRequest, @RequestHeader("Authorization") String token) {
        System.out.println(updateProductRequest.getIdAddedProduct());
        System.out.println(updateProductRequest.getPrice());
        System.out.println(updateProductRequest.getDescription());
        System.out.println(updateProductRequest.getAvailability());
        System.out.println(updateProductRequest.getCategories().length);
        System.out.println(updateProductRequest.getAdditionalSettingsRequests().length);

        oneMarketPageService.updateProduct(updateProductRequest, token);


        return ResponseEntity.ok("Success");
    }


    @GetMapping("/get_for_one_market")
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestParam("id") Integer id) {

        List<ProductResponse> productResponseList = productService.getProductByFilterRequest(FilterProductRequest.builder().marketId(id).build(), "");


        return ResponseEntity.ok(productResponseList);
    }
}

