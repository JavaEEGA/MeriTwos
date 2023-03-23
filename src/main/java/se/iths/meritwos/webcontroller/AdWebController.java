package se.iths.meritwos.webcontroller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.iths.meritwos.ad.AdRepository;
import org.springframework.stereotype.Controller;
import se.iths.meritwos.company.CompanyRepository;

@Controller
public class AdWebController {
    AdRepository adRepository;
    CompanyRepository companyRepository;

    public AdWebController(AdRepository adRepository, CompanyRepository companyRepository){
        this.adRepository = adRepository;
        this.companyRepository = companyRepository;
    }
    @GetMapping (path = "/ads")
    String ads(Model model){
        model.addAttribute("allCompanies", companyRepository.findAll());
        return "ad";
    }
}
