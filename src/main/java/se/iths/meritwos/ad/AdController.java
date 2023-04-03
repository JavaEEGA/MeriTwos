package se.iths.meritwos.ad;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.iths.meritwos.mapper.Mapper;
import se.iths.meritwos.student.StudentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    private final AdRepository adRepository;

    private final Mapper mapper;

    public AdController(AdRepository adRepository, StudentRepository studentRepository, Mapper mapper) {
        this.adRepository = adRepository;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    Optional<AdDTO> getAdById(@PathVariable long id) {
        return mapper.mapAdToDTO(adRepository.findById(id).orElseThrow());
    }
    @GetMapping
    List<AdDTO> getAllAds(){
        return mapper.mapAdToDTO(adRepository.findAll());
    }

    @GetMapping("/find/{name}")
    Optional<AdDTO> getAdByName(@PathVariable String name) {
        Ad ad = adRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapper.mapAdToDTO(ad);
    }

    private static boolean adIsEmptyOrNull(Ad ad) {
        return ad.getName() == null || ad.getName().isEmpty();
    }
}
