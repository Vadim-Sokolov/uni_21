package thyme;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ConfigurationBeans {

	@Bean
	public Docket thymeDocumentation() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("ThymeApi")
				.select()
				.paths(PathSelectors.ant("/thyme/**"))
				.apis(RequestHandlerSelectors.basePackage("thyme.controller.thyme"))
				.build();
	}

	@Bean
	public Docket restDocumentation() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("RestApi")
				.select()
				.paths(PathSelectors.ant("/rest/**"))
				.apis(RequestHandlerSelectors.basePackage("thyme.controller.rest"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfo("University API", "Sample API", "1.0", "Free to use",
				new springfox.documentation.service.Contact("vsokolov", "url", "email"), "License", "Another url",
				Collections.emptyList());
	}
}
