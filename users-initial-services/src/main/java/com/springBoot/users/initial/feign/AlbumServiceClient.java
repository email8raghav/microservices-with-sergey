package com.springBoot.users.initial.feign;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springBoot.users.initial.model.AlbumResponseModel;

@FeignClient(name = "albums-ws", fallback = AlbumFallBack.class)
public interface AlbumServiceClient {

	//@GetMapping("/users/{publicUserId}/albumsss") //404 // For feign error decoder test// method key is getAlbums
	// as mentioned below
	@GetMapping("/users/{userId}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String userId);
}

// this is hystrix circuit brekar
// we enable @EnableCircuitBreaker
@Component
class AlbumFallBack implements AlbumServiceClient {

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		return new ArrayList<>();
	}

}
