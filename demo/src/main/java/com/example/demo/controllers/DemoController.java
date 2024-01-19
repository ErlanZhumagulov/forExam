package com.example.demo.controllers;

import com.example.demo.models.Seller;
import com.example.demo.service.SellerPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/demo-controller")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class DemoController {

    @Autowired
    SellerPageService sellerPageService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> sayHello(){

        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestHeader("Authorization") String tokenAuth ){
        System.out.println(tokenAuth);
        sellerPageService.saveImage(file);
        return ResponseEntity.ok("Upload img");
    }
}

