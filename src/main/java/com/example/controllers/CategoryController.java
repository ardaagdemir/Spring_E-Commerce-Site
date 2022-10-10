package com.example.controllers;

import com.example.entities.Category;
import com.example.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    protected final CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity save(@Valid @RequestBody Category category){
        return categoryService.save(category);
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody Category category){
        return categoryService.update(category);
    }

    @DeleteMapping("/delete/{cid}")
    public ResponseEntity delete(@PathVariable String cid){
        return categoryService.delete(cid);
    }
    @GetMapping("/getCategories")
    public ResponseEntity getCategories(){
        return categoryService.getCategories();
    }
    @GetMapping("/getCategoryById/{cid}")
    public ResponseEntity getCategoryById(@PathVariable int cid){
        return categoryService.getBycategoryId(cid);
    }



}
