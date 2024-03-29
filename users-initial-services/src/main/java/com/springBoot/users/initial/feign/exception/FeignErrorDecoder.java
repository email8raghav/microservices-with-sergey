package com.springBoot.users.initial.feign.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;


@Component
public class FeignErrorDecoder implements ErrorDecoder {

	
	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
		case 400:
			// Do something
			// return new BadRequestException();
			break;
		case 404: {
			//methodKey=className+methodName i.e. AilbumServiceClient#getAlbums as we defined getAlbums method
			if (methodKey.contains("getAlbums")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Users albums are not found!!!");
			}
			break;
		}
		default:
			return new Exception(response.reason());
		}
		return null;
	}

}
