package com.example.configascode;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import java.net.http.HttpClient;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() throws Exception {
        // // load the keystore
        // KeyStore keyStore = KeyStore.getInstance("JKS");
        // keyStore.load(getClass().getResourceAsStream("/path/to/keystore.jks"), "password".toCharArray());
        //
        // // create an SSL context
        // SSLContext sslContext = SSLContexts.custom()
        //     .loadKeyMaterial(keyStore, "password".toCharArray())
        //     .build();
        //
        // // create a httpclient with the custom SSL context, try-resources??
        // CloseableHttpClient httpClient = HttpClients.custom()
        //     .setSSLContext(sslContext)
        //     .build();
        //
        // HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient); // compile error here
        // return new RestTemplate(requestFactory);

        return new RestTemplate();
    }
}
