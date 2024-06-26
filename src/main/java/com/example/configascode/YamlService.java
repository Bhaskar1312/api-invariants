package com.example.configascode;

import org.apache.catalina.mapper.Mapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

@Service
public class YamlService {
    // public List<YamlRecord> readYaml(String filePath) {
    //     Yaml yaml = new Yaml(new Constructor(YamlRecord.class));
    //     InputStream inputStream = this.getClass()
    //         .getClassLoader().getResourceAsStream(filePath);
    //     Iterable<Object> yamlObjects = yaml.loadAll(inputStream);
    //     return (List<Object>)yamlObjects;
    // }
    public List<YamlRecord> readYaml(String filePath) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(new File(filePath), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of(); // should I throw an exception here?
    }


    // static
    public boolean writeToYaml(String filePath, List<YamlRecord> records) { // should the return type be void?
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)); // disabling   optional
        // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        try {
            // mapper.copyWith(new YAMLFactory()); // for any defaults for all records??
            mapper.writeValue(new File(filePath), records);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // private ObjectMapper objectMapper;
    // public YamlService(Jackson2ObjectMapperBuilder builder) {
    //     this.objectMapper = builder.failOnUnknownProperties(true).build();
    // }

}
