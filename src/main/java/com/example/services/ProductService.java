package com.example.services;

import com.example.entities.Product;
import com.example.entities.joinTables.JoinProCat;
import com.example.entities.joinTables.JoinProImg;
import com.example.repositories.ProductRepository;
import com.example.repositories.joinRepository.JoinProCatRepository;
import com.example.repositories.joinRepository.JoinProImgRepository;
import com.example.utils.REnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository productRepository;
    final JoinProCatRepository joinProCatRepository;
    final JoinProImgRepository joinProImgRepository;

    //product save
    public ResponseEntity save(Product product) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Optional<Product> optionalProduct = productRepository.findByTitleEqualsIgnoreCase(product.getTitle());
        if (optionalProduct.isPresent()) {
            hm.put(REnum.status, false);
            hm.put(REnum.message, "The product has already been registered.");
        } else {
            productRepository.save(product);
            hm.put(REnum.status, true);
            hm.put(REnum.result, product);
            hm.put(REnum.message, "The product has been registered.");
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //product update
    public ResponseEntity update(Product product) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Optional<Product> optionalProduct = productRepository.findById(product.getPid());
        if (optionalProduct.isPresent()) {
            productRepository.saveAndFlush(product);
            hm.put(REnum.status, true);
            hm.put(REnum.result, product);
        } else {
            hm.put(REnum.status, false);
            hm.put(REnum.message, "User name not found.");
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //product delete
    public ResponseEntity delete(String pid) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            int id = Integer.parseInt(pid);
            productRepository.deleteById(id);
            hm.put(REnum.status, true);
            hm.put(REnum.message, "The product has been deleted.");
        } catch (Exception ex) {
            hm.put(REnum.message, pid);
            hm.put(REnum.status, false);
            hm.put(REnum.message, "Product not found.");
            return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //product list
    public ResponseEntity getProducts() {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        List<JoinProCat> ls = joinProCatRepository.getProducts();
        if (ls.isEmpty()){
            hm.put(REnum.message, "Product list is empty.");
        }else {
            hm.put(REnum.status, true);
            hm.put(REnum.result, ls);
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //product image list
    public ResponseEntity getImagesByProduct( ) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        List<JoinProImg> ls = joinProImgRepository.getImagesByProduct();

        hm.put(REnum.message, "Image fields of products are empty.");
        hm.put(REnum.status, true);
        hm.put(REnum.result, ls);

        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //pproduct id image list
    public ResponseEntity getImageByProductId(long pid) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        List<JoinProImg> ls = joinProImgRepository.getImageByProductId(pid);

        hm.put(REnum.message, "Image fields of products are empty.");
        hm.put(REnum.status, true);
        hm.put(REnum.result, ls);

        return new ResponseEntity(hm, HttpStatus.OK);
    }


    //searchProduct
    public ResponseEntity searchProduct(String q) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        List<JoinProCat> product = joinProCatRepository.searchProduct("%" + q + "%");
        if (product.isEmpty()){
            hm.put(REnum.status,false);
            hm.put(REnum.message, "No results were found for your search " + "'" + q + "'");
        }
        else {
            hm.put(REnum.status, true);
            hm.put(REnum.result, product);
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //product id
    public ResponseEntity getProductById(int pid){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Optional<JoinProCat> optionalProduct = joinProCatRepository.getProductById(pid);
        if (optionalProduct.isPresent()){
            hm.put(REnum.status, true);
            hm.put(REnum.result, optionalProduct);
        }
        else {
            hm.put(REnum.status,false);
            hm.put(REnum.message, "There is no such product. Product Id:" + pid );
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity getProductByCategoryId(long cid){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status, true);
        hm.put(REnum.result, joinProImgRepository.getProductByCategoryId(cid));
        return new ResponseEntity(hm, HttpStatus.OK);
    }
}
