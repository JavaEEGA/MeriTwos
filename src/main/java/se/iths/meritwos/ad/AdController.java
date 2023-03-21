package se.iths.meritwos.ad;

import org.springframework.web.bind.annotation.*;
import se.iths.meritwos.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ad")
public class AdController {
    private final AdRepository adRepository;
    private final Mapper mapper;

    public AdController(AdRepository adRepository, Mapper mapper) {
        this.adRepository = adRepository;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    Optional<AdDTO> getAName(@PathVariable long id) {
        return mapper.mapAdToDTO(adRepository.findById(id).orElseThrow());
    }
    @GetMapping
    List<AdDTO> getAllAds(){
        return mapper.mapAdToDTO(adRepository.findAll());
    }

    @PostMapping
    void addAd(@RequestBody Ad ad) {
//        if(adIsEmptyOrNull(ad))
//            throw new IllegalArgumentException();
        adRepository.save(ad);
    }
    //

    private static boolean adIsEmptyOrNull(Ad ad) {
        return ad.getName() == null || ad.getCompany() == null || ad.getName().isEmpty();
    }

    @DeleteMapping("/{id}")
    void deleteAd(@PathVariable long id){
        adRepository.deleteById(id);
    }
}
