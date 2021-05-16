package com.springBoot.microservices.sevice;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.springBoot.microservices.entity.AlbumEntity;


@Service
public class AlbumServiceImpl implements AlbumService {

	@Override
    public List<AlbumEntity> getAlbums(String userId) {
        List<AlbumEntity> returnValue = new ArrayList<>();
        
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setUserId(userId);
        albumEntity.setAlbumId(UUID.randomUUID().toString());
        albumEntity.setDescription("album 1 description");
        albumEntity.setId(1L);//set by db
        albumEntity.setName("album 1 name");
        
        AlbumEntity albumEntity2 = new AlbumEntity();
        albumEntity2.setUserId(userId);
        albumEntity2.setAlbumId(UUID.randomUUID().toString());
        albumEntity2.setDescription("album 2 description");
        albumEntity2.setId(2L);//set by db
        albumEntity2.setName("album 2 name");
        
        returnValue.add(albumEntity);
        returnValue.add(albumEntity2);
        
        return returnValue;
    }
    
}
