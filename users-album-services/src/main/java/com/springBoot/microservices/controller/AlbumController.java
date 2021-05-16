package com.springBoot.microservices.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.microservices.entity.AlbumEntity;
import com.springBoot.microservices.model.AlbumResponseModel;
import com.springBoot.microservices.sevice.AlbumService;


@RestController
@RequestMapping("/users/{userId}/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumsService;
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
    @GetMapping(
            produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
            })
    public List<AlbumResponseModel> userAlbums(@PathVariable String userId) {

    	List<AlbumResponseModel> returnValue = new ArrayList<>();

    	List<AlbumEntity> albumsEntities = albumsService.getAlbums(userId);

        if(albumsEntities == null || albumsEntities.isEmpty()){
            return returnValue;
        }

        // Creating List using ModelMapper
        Type listType = new TypeToken<List<AlbumResponseModel>>(){}.getType();
        
        returnValue = new ModelMapper().map(albumsEntities, listType);
        
        logger.info("Returning " + returnValue.size() + " albums");

        return returnValue;
    }
}
