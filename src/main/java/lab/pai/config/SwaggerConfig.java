package lab.pai.config;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import static springfox.documentation.builders.PathSelectors.regex;

public class SwaggerConfig {
	@Bean
    public Docket productApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("lab.pai"))
                .paths(regex("/rest.*"))
                .build();
    }
}
