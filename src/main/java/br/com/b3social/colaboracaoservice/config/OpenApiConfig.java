package br.com.b3social.colaboracaoservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(
		title = "Swagger Colaboração Api", 
		version = "1.0", 
		description = "Api desenvolvida para gerenciamento de colaborações em ações sociais",
        contact = @Contact(
            name = "B3Social",
            email = "fake.email@gmail.com",
            url = "https://www.b3.com.br/pt_br/b3/"
        )        
	)
)
public class OpenApiConfig {}
