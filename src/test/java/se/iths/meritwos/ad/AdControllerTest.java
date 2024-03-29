package se.iths.meritwos.ad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.meritwos.GlobalControllerAdvice;
import se.iths.meritwos.mapper.Mapper;
import se.iths.meritwos.security.SecurityConfig;
import se.iths.meritwos.student.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(controllers = AdController.class)
@ContextConfiguration(classes = {SecurityConfig.class, AdController.class, GlobalControllerAdvice.class})
class AdControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AdRepository adRepository;
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    Mapper mapper;
    @Test
    void getAllAdsShouldReturnOk() throws Exception{
        Ad ad1 = new Ad(1L,"Världens bästa lia!","hello my friend");
        Ad ad2 = new Ad(2L, "världens näst bästa lia!", "hey pal");

        when(adRepository.findAll()).thenReturn(List.of(ad1,ad2));
        mockMvc.perform(get("/api/ads").with(user("admin").password("admin")))
                .andExpect(status().isOk());
    }
    @Test
    void getAdsByIDShouldReturnOk() throws Exception{
        Ad ad1 = new Ad(1L,"Världens bästa lia!","hello my friend");

        when(adRepository.findById(1L)).thenReturn(Optional.of(ad1));

        mockMvc.perform(get("/api/ads/1").with(user("admin").password("admin")))
                .andExpect(status().isOk());
    }
    @Test
    void getAdsByIdThatDoesNotExistShouldReturn404() throws Exception{
        mockMvc.perform(get("/api/ads/1").with(user("admin").password("admin")))
                .andExpect(status().isNotFound());
    }
}
