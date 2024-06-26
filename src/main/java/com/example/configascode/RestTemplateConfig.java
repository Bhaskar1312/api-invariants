package com.example.configascode;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import java.net.http.HttpClient;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    // @Bean
    // public RestTemplate restTemplate(RestTemplateBuilder builder) {
    //     return builder.build();
    // }

    @Bean  //
    // @EventListener(ApplicationReadyEvent.class) // alternative for command line runner?
    CommandLineRunner printMapperConfig(ObjectMapper objectMapper) {
        return args -> {
            System.out.println("Mapper Modules: "+objectMapper.getRegisteredModuleIds());
            System.out.println("Fail on unknown properties: "+objectMapper.getDeserializationConfig().isEnabled(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
        };
        // configured  by spring boot, (yaml may not be and some json ones may be missing)
        // Mapper Modules: [com.fasterxml.jackson.datatype.jdk8.Jdk8Module, jackson-module-parameter-names, jackson-datatype-jsr310, org.springframework.boot.jackson.JsonMixinModule, org.springframework.boot.jackson.JsonComponentModule]
        // Fail on unknown properties: false
    }
    // jackson use default property-based access: getter defines field, setter is optional, jackson immediately uses reflection to set value
    // use @JsonCreator for constructors and static factory methods and use @JsonProperty for every param or else use -Pparameters for java compiler inn gradle
    // maven, it is default enabled by spring boot parent pom in mavnen-compiler-plugin config parameters set to true
    // gradle, in compileJava task, add options.compilerArgs = ["-parameters"]

    // slice unit testing using @JsonTEst and @Autowired JacksonTester<T> which extends AbstractJsonMarshalTester<T>
    // JsonContent<T> is returned by write =, use getJson()
    // with jackson, you can also use maps which has keys and values
    // httpbin.org/post just echoes back

    // unmarshal to JsonNode and use JsonPointer(thread-safe)
    // JsonPath.compile("") JsonPath by default doesn't use Jackson

    // use @JsonView(Summary.class) on fields and use @JsonView(Summary.class) on class to include fields in summary view
    // sort of like another ExtendedSummary.class



    // add spring.jackson.default-property-inclusion=non_null to application.properties to exclude null fields
}
