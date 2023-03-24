package se.iths.meritwos.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.iths.meritwos.ad.AdRepository;
import se.iths.meritwos.student.Student;
import se.iths.meritwos.student.StudentRepository;

@Controller
public class AdWebController {
    AdRepository adRepository;


    public AdWebController(AdRepository adRepository, StudentRepository studentRepository) {
        this.adRepository = adRepository;

    }

    @GetMapping(path = "/ads")
    String ads(Model model) {
        model.addAttribute("allAds", adRepository.findAll());
        return "ad";
    }

    @GetMapping(path = "/newstudent")
    String student(Model model) {
        model.addAttribute("student", new Student());
        return "studentCRUD";
    }
}
