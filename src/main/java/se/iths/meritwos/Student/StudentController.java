package se.iths.meritwos.Student;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository StudRepo;

    public StudentController(StudentRepository studentRepository){
        this.StudRepo =studentRepository;
    }

    @GetMapping
    List<Student> getStudent(){
        return StudRepo.findAll();
    }

    @GetMapping("/{id}")
    Student getAStudent(@PathVariable Long id){
        return StudRepo.findById(id).orElseThrow();
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
