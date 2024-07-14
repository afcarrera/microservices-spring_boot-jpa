package com.carrera.bank.customer;

import com.carrera.bank.customer.controller.CustomerController;
import com.carrera.bank.customer.dto.impl.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CustomerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerController customerController;

    private CustomerDTO customerDTO;

    @BeforeEach
    public void setUp() {
        this.customerDTO = CustomerDTO.builder()
                .personId("1")
                .password("password")
                .identification("identification")
                .build();
    }

    @Test
    public void findAllTest() throws Exception{
        Page<CustomerDTO> page =new PageImpl<>(List.of(customerDTO), Pageable.unpaged(), 1);
        when(customerController.findAll(anyInt(), anyInt())).thenReturn(page);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
}
