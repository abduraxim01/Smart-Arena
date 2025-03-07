package com.practise.Smart_Arena.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        // general info
        Info info = new Info()
                .title("Smart Arena")
                .version("1.0.0")
                .description("Quyida Smart Arena loyihasi uchun API hujjatlar tagdim qilingan.")
                .contact(new Contact()
                        .name("Abduraxim")
                        .email("tursunboyevabdurahim2004@gmail.com")
                        .url("https://abduraxim.uz")
                );
//                .license(new License()
//                        .name("Videohub.uz")
//                        .url("https://videohub.uz/")
//                )
//                .termsOfService("Savol javob guruhi: https://t.me/code_uz_group");

        // servers (ishlatiladigan serverlar)
        Server server1 = new Server()
                .description("Local")
                .url("http://localhost:9091");

//        Server server2 = new Server()
//                .description("DEV")
//                .url("http://api.mazgi.uz");
//
//        Server server3 = new Server()
//                .description("PROD")
//                .url("http://api.giybat.uz");

        // security type (bizning holatda JWT)
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("bearerAuth");

        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setName("bearerAuth");
        securityScheme.setType(SecurityScheme.Type.HTTP);
        securityScheme.bearerFormat("JWT");
        securityScheme.setIn(SecurityScheme.In.HEADER);
        securityScheme.setScheme("bearer");

        Components components = new Components();
        components.addSecuritySchemes("bearerAuth", securityScheme);

        // collect all together
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);
//        openAPI.setServers(List.of(server1, server2, server3));
        openAPI.setServers(List.of(server1));
        openAPI.setSecurity(List.of(securityRequirement));
        openAPI.components(components);

        // return-xe
        return openAPI;
    }
}
