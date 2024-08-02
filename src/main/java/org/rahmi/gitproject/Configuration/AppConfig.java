package org.rahmi.gitproject.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AppConfig {

    @Value("${git.api.token}")
    private String gitToken;
    @Bean
    public RestTemplate getrestTemplate() {
        return new RestTemplate();
    }
    @Bean
    public HttpEntity<String> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + gitToken);
        return new HttpEntity<>(headers);
    }



}
