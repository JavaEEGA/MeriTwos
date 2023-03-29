package se.iths.meritwos.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.meritwos.ad.AdRepository;
import se.iths.meritwos.mapper.Mapper;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final Mapper mapper;
    private final AdRepository adRepository;

    public StudentController(StudentRepository studentRepository, Mapper mapper, AdRepository adRepository) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
        this.adRepository = adRepository;
    }


    @GetMapping
    List<StudentDTO> getAllStudents() {
        return mapper.mapStudentToDto(studentRepository.findAll());
    }

    @GetMapping("/{id}")
    Optional<StudentDTO> getStudent(@PathVariable Long id) {
        return mapper.mapStudentToDto(studentRepository.findById(id).orElseThrow());
    }

    @PostMapping
    void addName(@RequestBody Student student) {
        var copyOfAd = Set.copyOf(student.getAds());
        student.getAds().clear();
        student.getAds().addAll(adRepository.saveAll(copyOfAd));
        studentRepository.save(student);

    }

    @PostMapping("/{studentId}/{adId}")
    void addAdToStudent(@PathVariable long studentId, @PathVariable long adId) {
        var student = studentRepository.findById(studentId).orElseThrow();
        student.getAds().add(adRepository.findById(adId).orElseThrow());
        studentRepository.save(student);

    }

    @PostMapping(value = "/newstudent", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Void> addStudentByForm(@ModelAttribute Student student) {
        studentRepository.save(student);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/newstudent"))
                .build();
    }


    @DeleteMapping("/{id}")
    void killStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}

