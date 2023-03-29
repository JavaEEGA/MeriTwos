package se.iths.meritwos.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import se.iths.meritwos.ad.AdRepository;
import se.iths.meritwos.mapper.Mapper;
import se.iths.meritwos.GlobalControllerAdvice;
import se.iths.meritwos.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
@ContextConfiguration(classes = {SecurityConfig.class, StudentController.class, GlobalControllerAdvice.class})
class StudentControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    Mapper mapper;
    @MockBean
    AdRepository AdRepository;
    @MockBean
    StudentRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    void getAllStudentsAndReturn200ok() throws Exception {
        Student student1 = new Student();
        Student student2 = new Student();

        student1.setId(1L);
        student1.setName("Simon");
        student1.setProgram("java22");
        student1.setEmail("simon@iths.se");

        student2.setId(2L);
        student2.setName("Albert");
        student2.setProgram("Java22");
        student2.setEmail("albert@iths.se");

        when(repository.findAll()).thenReturn(List.of(student1,student2));

        mockMvc.perform(get("/api/students").with(user("user")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void getAStudentById() throws Exception{
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Albert");
        student1.setProgram("Java22");
        student1.setEmail("albert@iths");

        when(repository.findById(1L)).thenReturn(Optional.of(student1));

        mockMvc.perform(get("/api/students/1")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(authorities ="ADMIN")
    void AddANewStudentAndReturn200ok() throws Exception{
        Student student = new Student();
        student.setId(1L);
        student.setName("Simon");
        student.setProgram("Java22");
        student.setEmail("simon@ithsse");

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void RemoveAStudentById()throws Exception{
        Student student = new Student();
        student.setId(1L);
        student.setName("test");

        mockMvc.perform(delete("/api/students/1")).andExpect(status().isOk());
    }




}