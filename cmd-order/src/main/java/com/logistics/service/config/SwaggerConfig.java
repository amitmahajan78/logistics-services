package com.logistics.service.config;

import static springfox.documentation.builders.PathSelectors.regex;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("CQRS").apiInfo(apiInfo())
				.select().paths(regex("/.*")).build().directModelSubstitute(XMLGregorianCalendar.class, MixIn.class);
	}

	public static interface MixIn {
		@JsonIgnore
		public void setYear(int year);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Order Service CQRS REST APIs")
				.description("Order Service CQRS REST APIs").termsOfServiceUrl("http://....")
				.contact(new Contact("Logistics", "https://xyz.com", null)).license("XYZ Licensed")
				.version("1.0").build();
	}
}