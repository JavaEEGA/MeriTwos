package se.iths.meritwos.webcontroller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.iths.meritwos.ad.AdRepository;
import org.springframework.stereotype.Controller;

@Controller
public class AdWebController {
    AdRepository adRepository;

    public AdWebController(AdRepository adRepository){
        this.adRepository = adRepository;
    }
    @GetMapping (path = "/ads")
    String ads(Model model){
        model.addAttribute("allAds", adRepository.findAll());
        return "ad";
    }
}
