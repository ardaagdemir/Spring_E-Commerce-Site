package com.example.controllers;

import com.example.entities.Product;
import com.example.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    protected final ProductService productService;


    @PostMapping("/save")
    public ResponseEntity save(@Valid @RequestBody Product product){
        return productService.save(product);
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody Product product){
        return productService.update(product);
    }

    @DeleteMapping("/delete/{pid}")
    public ResponseEntity delete(@PathVariable String pid){
        return productService.delete(pid);
    }

    @GetMapping("/getProducts")
    public ResponseEntity getAllProducts(){return productService.getProducts();}

    @GetMapping("/getImagesByProduct")
    public ResponseEntity getImagesByProduct(){
        return productService.getImagesByProduct();
    }

    @GetMapping("/getImageByProductId/{pid}")
    public ResponseEntity getImageByProductId(@PathVariable long pid){
        return productService.getImageByProductId(pid);
    }
    @GetMapping("/searchProduct")
    public ResponseEntity searchProduct(@RequestParam String q){
        return productService.searchProduct(q);
    }

    @GetMapping("/getProductById/{pid}")
    public ResponseEntity getProductById (@PathVariable int pid){
        return productService.getProductById(pid);
    }

    @GetMapping("/getProductByCategoryId/{cid}")
    public ResponseEntity getProductByCategoryId(@PathVariable long cid){
        return productService.getProductByCategoryId(cid);
    }
}
