package com.example.configascode;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

class YamlRecordTest {
    @Test
    void testInnerClass_serializeObjectIntoByteStream() throws IOException {
        Address address = new Address();
        address.setStreetNo("Pune");
        address.setPincodes(new int[]{4, 1, 1, 0, 5, 7});
        Employee employee = new Employee();
        employee.setFirstName("Bhaskar");
        employee.setLastName("Bantupalli");
        employee.setSalary(BigDecimal.ONE);
        employee.setAddress(address);

        String filePath = "src/test/resources/employee.yaml";

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("mapper = " + mapper.convertValue(employee, new TypeReference<>() {}));


        mapper =new ObjectMapper(new YAMLFactory());
        // System.out.println("mapper = " + mapper.convertValue(employee, new TypeReference<>() {}));
        mapper.writeValue(new File(filePath),
            mapper.convertValue(employee, new TypeReference<>() {}));
    }

    @Test
    void testInnerClass_DeserializeByteStreamIntoObject() throws IOException {
        String filePath = "src/test/resources/employee.yaml";

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Employee employee =mapper.readValue(new File(filePath), new TypeReference<>() {});
        System.out.println("employee.getFirstName() = " + employee.getFirstName());
        System.out.println("employee.getLastName() = " + employee.getLastName());
        System.out.println("employee.getSalary() = " + employee.getSalary());
        System.out.println("employee.getAddress().getStreetNo() = " + employee.getAddress().getStreetNo());
        System.out.println("employee.getAddress().getPincodes() = " + Arrays.stream(employee.getAddress().getPincodes()).boxed().toList());
        assertAll(()->{
            assertEquals("Bhaskar", employee.getFirstName());
            assertEquals("Bantupalli", employee.getLastName());
            assertEquals(BigDecimal.ONE, employee.getSalary());
            assertEquals("Pune", employee.getAddress().getStreetNo());
            // assertEquals(null, employee.getAddress().getPincodes());
            assertArrayEquals(new int[]{4,1,1,0,5,7}, employee.getAddress().getPincodes());
        });
    }

}