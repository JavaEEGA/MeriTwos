package se.iths.meritwos.webcontroller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AdWebController {
    CompanyRepository companyRepository;

    public AdWebController(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }
    @GetMapping (path = "/ads")
    String ads(Model model){
        model.addAttribute("allAds", adRepository.findAll());
        return "ad";
    }
}
