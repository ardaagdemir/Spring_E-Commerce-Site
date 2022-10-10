package com.example.services;

import com.example.entities.Category;
import com.example.entities.joinTables.JoinProCat;
import com.example.repositories.CategoryRepository;
import com.example.repositories.joinRepository.JoinProCatRepository;
import com.example.utils.REnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class CategoryService {
    final CategoryRepository categoryRepository;
    final JoinProCatRepository joinProCatRepository;

    public ResponseEntity save(Category category) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Optional<Category> optionalCategory = categoryRepository.findByCategoryNameEqualsIgnoreCase(category.getCategoryName());
        if (optionalCategory.isPresent()) {
            hm.put(REnum.status, false);
            hm.put(REnum.message, "The category has already been registered");
        } else {
            try {
                categoryRepository.save(category);
                hm.put(REnum.status, true);
                hm.put(REnum.result, category);
                hm.put(REnum.message, "The category has been registered.");
            }catch (IllegalArgumentException e){
                hm.put(REnum.message, e.getMessage());
            }
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //product update
    public ResponseEntity update(@Valid Category category) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Optional<Category> optionalCategory = categoryRepository.findById(category.getCid());
        try{
            if (optionalCategory.isPresent()) {
                categoryRepository.saveAndFlush(category);
                hm.put(REnum.status, true);
                hm.put(REnum.result, category);
            } else {
                hm.put(REnum.status, false);
                hm.put(REnum.message, "Category is not found.");
            }
        }catch (IllegalArgumentException e){
           hm.put(REnum.message, e.getMessage());
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //product delete
    public ResponseEntity delete(String pid) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            int id = Integer.parseInt(pid);
            categoryRepository.deleteById(id);
            hm.put(REnum.status, true);
            hm.put(REnum.message, "The category is successfully deleted");
        } catch (Exception ex) {
            hm.put(REnum.message, pid);
            hm.put(REnum.status, false);
            hm.put(REnum.message, "Category not found.");
            return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //product list
    public ResponseEntity getCategories() {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        List<Category> ls = categoryRepository.findAll();
        if (ls.isEmpty()){
            hm.put(REnum.message, "Category list is empty!");
        }else {
            hm.put(REnum.status, true);
            hm.put(REnum.result, ls);
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    //category id
    public ResponseEntity getBycategoryId(int cid){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Optional<Category> optionalCategory = categoryRepository.findById(cid);
        try{
            if (optionalCategory.isPresent()){
                hm.put(REnum.status, true);
                hm.put(REnum.result, optionalCategory);
            }
            else {
                hm.put(REnum.status,false);
                hm.put(REnum.message, "There is no such category. " + "Category Id:" + cid );
            }
        }catch (IllegalArgumentException e){
            hm.put(REnum.message, e);
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }


}
