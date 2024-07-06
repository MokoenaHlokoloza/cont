package mtn.momo.contract.repayment.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringDocConfig implements WebMvcConfigurer {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info().title("Repayment API")
                .version("1.0")
                .description("API for calculating repayments")
                .contact(new Contact().name("MTN MOMO Devs").email("mtn.momo@mtn.co.za")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("All")
                .pathsToMatch("/*/**")
                .build();
    }
}
