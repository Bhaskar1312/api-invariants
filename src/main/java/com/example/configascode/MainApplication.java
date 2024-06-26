package com.example.configascode;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class MainApplication //{
    implements CommandLineRunner {

    public static void main(String[] args) {
        // YamlRecord tempRecord1 = new YamlRecord();
        // tempRecord1.setField1("value12");
        // tempRecord1.setField2("value22");
        // YamlService.writeToYaml("src/main/resources/data2.yaml", List.of(tempRecord1));
        SpringApplication.run(MainApplication.class, args).close();
    }


    @Autowired
    private YamlService yamlService;

    @Autowired
    private ApiService apiService;
    @Override
    public void run(String... args) throws Exception {
        String yamlPath = "src/main/resources/data.yaml";
        String url = "https://httpbin.org/post";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        // headers.add("Authorization", "Bearer 1234567890");
        List<YamlRecord> records  = yamlService.readYaml(yamlPath);
        for (YamlRecord record : records) {
            Map<String, Object> requestBody = constructRequestBody(record);

            // print the request body
            StringBuilder requestBodyStr = new StringBuilder();
            requestBody.forEach((key, value) -> {
               requestBodyStr.append(key).append(": ").append(value).append("\n");
            });
            System.out.println("requestBody: "+requestBodyStr);
            for (int attempt = 0; attempt < 3; attempt++) {
                try {
                    ResponseEntity<String> response = apiService.postRequest(url, requestBody, headers);
                    System.out.println("response: "+response.getBody());
                    break;
                } catch (Exception e) {
                    System.out.println("Attempt "+ (attempt + 1));
                    if(attempt < 2) {
                        Thread.sleep(1000 * (attempt + 1));
                    } else {
                        System.out.println("Failed to send request after 3 attempts");
                    }
                }
            }
        }

    }

    private Map<String, Object> constructRequestBody(YamlRecord record) { // should this be improved? auto-convert to Map?
        // Map<String, Object> requestBody = new HashMap<>();
        // requestBody.put("field1", record.getField1());
        // requestBody.put("field2", record.getField2());
        // // Add more fields as needed
        // return requestBody;

        // return constructRequestBodyUsingReflection(record);

        return constructRequestBodyUsingJackson(record);
    }


    private Map<String, Object> constructRequestBodyUsingReflection(YamlRecord record) {// can I use jackson?
        Map<String, Object> requestBody = new HashMap<>();
        for(Field field: record.getClass().getDeclaredFields()) {
            field.setAccessible(true); // use getter instead? in case of primitives, in case of objects, use recursively?
            try {
                requestBody.put(field.getName(), field.get(record));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return requestBody;
    }

    public Map<String, Object> constructRequestBodyUsingJackson(YamlRecord record) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(record, new TypeReference<>() {
        });
    }
}
