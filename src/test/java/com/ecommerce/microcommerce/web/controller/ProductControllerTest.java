package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.MicrocommerceApplication;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({
        "/product-delete-table.sql",
        "/product-create-table.sql",
        "/product-insert-table.sql",
})
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = MicrocommerceApplication.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllProduct() throws Exception {

        mockMvc.perform(get("/Produits"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].nom", is("Aspirateur Robot")))
        ;

    }

    @Test
    public void getProductByIdStatusOK() throws Exception {

        mockMvc.perform(get("/Produits/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Aspirateur Robot")))
        ;

    }

    @Test
    public void getProductByIdStatusNotFound() throws Exception {

        mockMvc.perform(get("/Produits/100000"))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;

    }

    @Test
    public void createProductStatusCreated() throws Exception {

        Product product = new Product(220, "iPhone 10", 1100, 500);

        mockMvc.perform(
                post("/Produits")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))
        )
                .andDo(print())
                .andExpect(status().isCreated())
        ;

    }

    @Test
    public void createProductStatusBadRequest() throws Exception {

        Product product = new Product(220, "iPhone 10", 0, 500);

        mockMvc.perform(
                post("/Produits")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Le prix ne peut etre nul ou egal a zero"))
        ;

    }







}
