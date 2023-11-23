package lab4.demo.controllers;

import lab4.demo.dto.PointDto;
import lab4.demo.models.Attempt;
import lab4.demo.models.CollectionAttempts;
import lab4.demo.dao.AttemptRepository;
import lab4.demo.services.AttemptValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attempts")
public class MainController {
    private CollectionAttempts collectionAttempts;
    private AttemptRepository attemptRepository;


    @Autowired
    public void setCollectionAttempts(CollectionAttempts collectionAttempts) {
        this.collectionAttempts = collectionAttempts;
    }

    @Autowired
    public void setAttemptRepository(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

//    Вариант через параметры адресной строки
//    @PostMapping("/new")
//    public Attempt check_hit(@RequestParam(value = "x") String strX,
//                             @RequestParam(value = "y") String strY,
//                             @RequestParam(value = "r") String strR) {
//        //TODO рассмотреть возвращение ResponseEntity
//        if (AttemptValidator.validateXYR(strX, strY, strR)) {
//            Attempt attempt = new Attempt(strX, strY, strR);
//            collectionAttempts.add(attempt);
//            attemptRepository.save(attempt);
//            return attempt;
//        }
//        return null;
//    }
    @PostMapping("/new")
    @CrossOrigin
    public Attempt check_hit(@RequestBody PointDto pointDto) {
        String strX = pointDto.getStrX();
        String strY = pointDto.getStrY();
        String strR = pointDto.getStrR();
        System.out.println("new point:");
        System.out.println(strX + " " + strY + " " + strR);
        if (AttemptValidator.validateXYR(strX, strY, strR)) {
            System.out.println("validate");
            Attempt attempt = new Attempt(strX, strY, strR);

            collectionAttempts.update(attemptRepository.findAll());

            collectionAttempts.add(attempt);
            attemptRepository.save(attempt);
            return attempt;
        }
        return null;
    }

    @PostMapping("/clear")
    @CrossOrigin
    public void clearAttempts() {
        attemptRepository.deleteAll();
        collectionAttempts.update(attemptRepository.findAll());
    }

    @GetMapping("/all")
    @CrossOrigin
    public CollectionAttempts get_all_attempts() {
        collectionAttempts.update(attemptRepository.findAll());
        return collectionAttempts;
    }
}