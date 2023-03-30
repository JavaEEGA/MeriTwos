package se.iths.meritwos.webcontroller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.meritwos.ad.AdRepository;
import se.iths.meritwos.company.CompanyRepository;
import se.iths.meritwos.security.SecurityConfig;
import se.iths.meritwos.user.UserRepository;


import java.util.List;

import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AdWebController.class)
@Import(SecurityConfig.class)
class AdWebControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    AdRepository adRepository;

    @MockBean
    CompanyRepository companyRepository;
    @MockBean
    BCryptPasswordEncoder encoder;
    @MockBean
    UserRepository userRepository;
    @Test
    void adsWithoutLoginReturnsOK() throws Exception {
        when(companyRepository.findAll()).thenReturn(List.of());
        mvc.perform(get("/ads")).andExpect(status().isOk());

    }
    @Test
    void newStudentWithoutLoginReturns302() throws Exception {
        mvc.perform(get("/newstudent")).andExpect(status().isFound());

    }
    @WithMockUser(authorities = "ADMIN")
    @Test
    void newStudentWithLoginReturnsOk() throws Exception {
        mvc.perform(get("/newstudent")).andExpect(status().isOk());

    }
    @Test
    void newAdWithoutLoginReturns302() throws Exception {
        mvc.perform(get("/newad")).andExpect(status().isFound());

    }
    @WithMockUser(authorities = "ADMIN")
    @Test
    void newAdWithLoginReturnsOk() throws Exception {
        mvc.perform(get("/newad")).andExpect(status().isOk());

    }

    @Test
    void newCompanyWithoutLoginReturns302() throws Exception {
        mvc.perform(get("/newcompany")).andExpect(status().isFound());

    }
    @WithMockUser(authorities = "ADMIN")
    @Test
    void newCompanyWithLoginReturnsOk() throws Exception {
        mvc.perform(get("/newcompany")).andExpect(status().isOk());

    }




    }
