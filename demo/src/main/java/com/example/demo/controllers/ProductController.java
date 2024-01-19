package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.requests.FilterProductRequest;
import com.example.demo.responses.GeoInfoResponse;
import com.example.demo.responses.ProductResponse;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/page-product")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping
    public ResponseEntity<byte[]> getImage(@RequestParam("imageId") Integer imageId) throws IOException {

        byte[] image = productService.getOneImage(imageId);

        return ResponseEntity.ok(image);
    }


    @PostMapping("use_filter")
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestBody FilterProductRequest filterProductRequest,
                                                             @RequestHeader("Authorization") String tokenAuth) {

        List<ProductResponse> productResponseList = productService.getProductByFilterRequest(filterProductRequest, tokenAuth);


        return ResponseEntity.ok(productResponseList);
    }

    @GetMapping("/get_product_by_id")
    public ResponseEntity<ProductResponse> getProductById(@RequestParam("id") Integer id) {

        ProductResponse product = productService.getProductById(id);

        return ResponseEntity.ok(product);

    }

    @GetMapping("/get_geo")
    public ResponseEntity<GeoInfoResponse> getGeoInfo(@RequestParam("id") Integer id) {

        GeoInfoResponse geoInfoResponse = productService.getGeoInfo(id);

        return ResponseEntity.ok(geoInfoResponse);

    }

}
