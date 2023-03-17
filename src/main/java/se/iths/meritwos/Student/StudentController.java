package se.iths.meritwos.Student;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import se.iths.meritwos.mapper.Mapper;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository StudRepo;
    private final Mapper mapper;

    public StudentController(StudentRepository studentRepository,Mapper mapper){
        this.StudRepo = studentRepository;
        this.mapper = mapper;
    }


    @GetMapping
    List<StudentDTO> getAllStudents(){
        return mapper.mapStudentToDto(StudRepo.findAll());
    }

    @GetMapping("/{id}")
    Optional<StudentDTO> getName(@PathVariable Long id){
        return mapper.mapStudentToDto(StudRepo.findById(id).orElseThrow());
    }

    @PostMapping
    void addName(@RequestBody Student student){
        String name = student.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        StudRepo.save(student);
    }
    @DeleteMapping("/{id}")
    void killStudent(@PathVariable Long id){
        StudRepo.deleteById(id);
    }
}

