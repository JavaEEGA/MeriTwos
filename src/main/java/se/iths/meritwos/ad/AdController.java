package se.iths.meritwos.ad;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController {
    private final AdRepository adRepository;

    public AdController(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @GetMapping("/{id}")
    Ad getAName(@PathVariable long id) {
        return adRepository.findById(id).orElseThrow();
    }
    @GetMapping
    List<Ad> getAllAds(){
        return adRepository.findAll();
    }

    @PostMapping
    void addAd(@RequestBody Ad ad) {
//        if(adIsEmptyOrNull(ad))
//            throw new IllegalArgumentException();
        adRepository.save(ad);
    }

    private static boolean adIsEmptyOrNull(Ad ad) {
        return ad.getName() == null || ad.getCompany() == null || ad.getName().isEmpty() || ad.getCompany().isEmpty();
    }

    @DeleteMapping("/{id}")
    void deleteAd(@PathVariable long id){
        adRepository.deleteById(id);
    }
}
