package se.iths.meritwos.student;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import se.iths.meritwos.ad.AdRepository;
import se.iths.meritwos.mapper.Mapper;

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
    Optional<StudentDTO> getName(@PathVariable Long id) {
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

    @DeleteMapping("/{id}")
    void killStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}

