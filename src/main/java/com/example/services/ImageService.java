package com.example.services;

import com.example.entities.Image;
import com.example.repositories.ImageRepository;
import com.example.repositories.joinRepository.JoinProImgRepository;
import com.example.services.utils.REnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageService {

    final ImageRepository imageRepository;
    final JoinProImgRepository joinProImgRepository;

    public ResponseEntity save(Image image){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try{
            imageRepository.save(image);
            hm.put(REnum.status, true);
            hm.put(REnum.result, image);
            return new ResponseEntity(hm, HttpStatus.OK);

        }catch (IllegalArgumentException e){
            hm.put(REnum.message, e.getMessage());
            hm.put(REnum.result, image);
            return new ResponseEntity(hm, HttpStatus.OK);
        }
    }

    public ResponseEntity getImages( long pid ) {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", true);
        hm.put("result", imageRepository.findByPidEquals(pid));
        return new ResponseEntity(hm, HttpStatus.OK);
    }



    public ResponseEntity delete(long iid){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try{
            imageRepository.deleteById(iid);
            hm.put(REnum.status,true);
        }catch (IllegalArgumentException e){
            hm.put(REnum.status,false);
        }
        hm.put(REnum.result, iid);
        return new ResponseEntity(hm, HttpStatus.OK);

    }
}
