package com.springBoot.microservices.sevice;


import java.util.List;

import com.springBoot.microservices.entity.AlbumEntity;


public interface AlbumService {

	public List<AlbumEntity> getAlbums(String userId);
}
