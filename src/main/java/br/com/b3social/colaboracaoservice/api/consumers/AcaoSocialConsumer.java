package br.com.b3social.colaboracaoservice.api.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.b3social.colaboracaoservice.api.dtos.AcaoSocialDTO;

@Component
public class AcaoSocialConsumer {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HttpHeaders headers;
    
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${acaosocial.baseurl}")
    private String acaoSocialUrl;

    public AcaoSocialDTO buscarAcaoSocialPorId(String id){
        ResponseEntity<AcaoSocialDTO> resp;

        headers.set(AUTHORIZATION_HEADER, "Bearer " + buscarTokenJwt());

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        String urlId = acaoSocialUrl+"/"+id;

        try {
            resp = restTemplate.exchange(urlId, HttpMethod.GET, httpEntity, AcaoSocialDTO.class);
            return resp.getBody();
        } catch (Exception e) {
            return null;
        }       
        
    }

    String buscarTokenJwt(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication!=null && authentication.getPrincipal() instanceof Jwt){
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getTokenValue();
        }

        throw new AccessDeniedException("Token não encontrado");
    }
}
