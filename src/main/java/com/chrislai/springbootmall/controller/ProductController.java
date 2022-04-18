package com.chrislai.springbootmall.controller;

import com.chrislai.springbootmall.constant.ProductCategory;
import com.chrislai.springbootmall.dto.ProductQueryRequest;
import com.chrislai.springbootmall.dto.ProductRequest;
import com.chrislai.springbootmall.model.Product;
import com.chrislai.springbootmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) ProductCategory category,
                                                     @RequestParam(required = false) String search) {
        ProductQueryRequest productQueryRequest = new ProductQueryRequest();
        productQueryRequest.setCategory(category);
        productQueryRequest.setSearch(search);
       List<Product> productList = productService.getProducts(productQueryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductId(productId);
        if (!Objects.isNull(product)) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest request) {
        Integer productId = productService.createProduct(request);
        Product product = productService.getProductId(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest request) {
        Product product = productService.getProductId(productId);
        if (product == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productId, request);
        Product updateProduct = productService.getProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
