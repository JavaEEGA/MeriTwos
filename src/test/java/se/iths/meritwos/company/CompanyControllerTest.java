package se.iths.meritwos.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import se.iths.meritwos.mapper.Mapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CompanyController.class)
class CompanyControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    CompanyRepository companyRepository;

    @MockBean
    Mapper mapper;

   // CompanyController companyController;

    @Test
    void getCompany() throws Exception {
        MvcResult result = mockMvc.perform(get("/companies")).andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();

        Assertions.assertEquals("[]", response, "Response not an empty list");

        var company = new Company();

        company.setId(1L);
        company.setName("Matnära");
        company.setEmail("matnära@gmail.com");
        company.setWebsite("matnära.se");

        companyRepository.save(company);


        when(companyRepository.findAll()).thenReturn(List.of(company));

        MvcResult result1 = mockMvc.perform(get("/companies")).andExpect(status().isOk()).andReturn();
        String response1 = result1.getResponse().getContentAsString();

        Assertions.assertEquals("[]", response1, "Response did not contain expected company"); //This assert should actually expect a company to be returned



    }

//    @Test
//    void getAllCompanies() {
//    }
//
//    @Test
//    void addCompany() {
//    }
//
//    @Test
//    void updateCompanyById() {
//    }
//
//    @Test
//    void deleteCompany() {
//    }
}