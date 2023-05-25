package com.infovision.addressapplication.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressConfiguration {
	@Bean
	ModelMapper modermMapper() {
		ModelMapper mapper=new ModelMapper();
		return mapper;
	}

}
