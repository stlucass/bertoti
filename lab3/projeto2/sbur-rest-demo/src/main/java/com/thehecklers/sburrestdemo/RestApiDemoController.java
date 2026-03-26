package com.thehecklers.sburrestdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/motos")
@CrossOrigin(origins = "*")
public class RestApiDemoController {
    private final MotoRepository motoRepository;

    public RestApiDemoController(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    @GetMapping
    Iterable<Moto> getMotos() {
        return motoRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Moto> getMotoById(@PathVariable String id) {
        return motoRepository.findById(id);
    }

    @PostMapping
    Moto postMoto(@RequestBody Moto moto) {
        return motoRepository.save(moto);
    }

    @PutMapping("/{id}")
    ResponseEntity<Moto> putMoto(@PathVariable String id, @RequestBody Moto moto) {
        return (motoRepository.existsById(id)) ? new ResponseEntity<>(motoRepository.save(moto), HttpStatus.OK)
                : new ResponseEntity<>(motoRepository.save(moto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void deleteMoto(@PathVariable String id) {
        motoRepository.deleteById(id);
    }
}
