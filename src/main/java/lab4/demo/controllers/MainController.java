package lab4.demo.controllers;

import lab4.demo.models.Attempt;
import lab4.demo.models.CollectionAttempts;
import lab4.demo.repositories.AttemptRepository;
import lab4.demo.utilities.AttemptValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attempts")
public class MainController {
    private CollectionAttempts collectionAttempts;

    //TODO why is write Autowired here wrong
    private AttemptRepository attemptRepository;


    @Autowired
    public void setCollectionAttempts(CollectionAttempts collectionAttempts) {
        this.collectionAttempts = collectionAttempts;
    }

    @Autowired
    public void setAttemptRepository(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    @PostMapping("/new")
    public Attempt check_hit(@RequestParam(value = "x") String strX,
                             @RequestParam(value = "y") String strY,
                             @RequestParam(value = "r") String strR) {
        //TODO рассмотреть возвращение ResponseEntity
        if (AttemptValidator.validateXYR(strX, strY, strR)) {
            Attempt attempt = new Attempt(strX, strY, strR);
            collectionAttempts.add(attempt);
            attemptRepository.save(attempt);
            return attempt;
        }
        return null;
    }

    @GetMapping("/all")
    public CollectionAttempts get_all_attempts() {
        return collectionAttempts;
    }
}