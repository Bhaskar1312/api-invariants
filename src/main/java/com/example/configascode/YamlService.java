package com.example.configascode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

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
        return List.of();
    }
}
