package se.iths.meritwos.webcontroller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.iths.meritwos.ad.Ad;
import se.iths.meritwos.ad.AdRepository;
import se.iths.meritwos.company.Company;
import se.iths.meritwos.company.CompanyRepository;
import se.iths.meritwos.student.Student;
import org.springframework.stereotype.Controller;
import se.iths.meritwos.user.User;
import se.iths.meritwos.user.UserController;
import se.iths.meritwos.user.UserRepository;

import java.util.List;

@Controller
public class AdWebController {
    AdRepository adRepository;
    CompanyRepository companyRepository;

    public AdWebController(AdRepository adRepository, CompanyRepository companyRepository) {
        this.adRepository = adRepository;
        this.companyRepository = companyRepository;

    }

    @GetMapping (path = "/ads")
    String ads(Model model){
        model.addAttribute("allCompanies", companyRepository.findAll());
        return "ad";
    }

    @GetMapping(path = "/newstudent")
    String newStudent(Model model) {
        model.addAttribute("student", new Student());
        return "studentCRUD";
    }

    @GetMapping(path = "/newad")
    String newAd(Model model) {
        model.addAttribute("ad", new Ad());
        model.addAttribute("companies", companyRepository.findAll());
        return "adCRUD";
    }

    @GetMapping(path = "/newcompany")
    String newCompany(Model model) {
        model.addAttribute("company", new Company());
        return "companyCRUD";
    }
    @GetMapping(path = "/newuser")
    String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", List.of(User.Role.STUDENT, User.Role.COMPANY));
        return "userCRUD";
    }
    @GetMapping(path = "/homepage")
    String homepage(Model model) {
        return "homepage";
    }
}
