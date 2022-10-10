package com.example.controllers;

import com.example.entities.Image;
import com.example.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    protected final ImageService imageService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Image image){
        return imageService.save(image);
    }

    @GetMapping("/getImages/{pid}")
    public ResponseEntity getImages(@PathVariable long pid) {
        return imageService.getImages(pid);
    }

    @DeleteMapping("/delete/{iid}")
    public ResponseEntity delete(@PathVariable long iid) {
        return imageService.delete(iid);
    }


}
