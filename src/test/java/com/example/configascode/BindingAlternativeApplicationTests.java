package com.example.configascode;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class BindingAlternativeApplicationTests {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void mapToRequest() {
        var request = Map.of("key1", "value1",
                            "key2", Map.of(
                                "nested", List.of("value2", "value3")));
        System.out.println(restTemplate.postForEntity("https://httpbin.org/post", request, String.class));
    }

    @Test
    void responseToMap() {
        Map<String, Object> response = restTemplate.getForObject("https://httpbin.org/get", Map.class);
        System.out.println("response = " + response);
        System.out.println("User-Agent"+((Map<String, Object>) response.get("headers")).get("User-Agent"));
    }

    @Test
    void testNestedYamlRecord() {
        // constructRequestBodyUsingJackson
    }

//     return Map.of("record",  mapper.convertValue(record, new TypeReference<>() {
//         }));

    public Map<String, Object> constructRequestBodyUsingJackson(YamlRecord record) {
        ObjectMapper mapper = new ObjectMapper();
        return  Map.of("record",  mapper.convertValue(record, new TypeReference<>() {}));
    }

    @Test
    void testNestedUsingJackson() {
        YamlRecord yamlRecord = new YamlRecord();
        yamlRecord.setField1("f1"); yamlRecord.setField2("f2");
        System.out.println(constructRequestBodyUsingJackson(yamlRecord)); // {record={field1=f1, field2=f2}}
    }
}
