package se.iths.meritwos.Student;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository Studrepo;
   // private final StudentDTO studDTO;

    public StudentController(StudentRepository studentRepository){//, StudentDTO studentDTO){
        this.Studrepo=studentRepository;
        //this.studDTO=studentDTO;
    }

    @GetMapping("/{id}")
    Student getAStudent(@PathVariable Long id){
        return Studrepo.findById(id).orElseThrow();
    }

    @GetMapping
    List<Student> getStudent(){
        return Studrepo.findAll();
    }

    @PostMapping
    void addName(@RequestBody Student student){
        String name = student.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        Studrepo.save(student);
    }
    @DeleteMapping("/{id}")
    void killStudent(@PathVariable Long id){
        Studrepo.deleteById(id);
    }
}
