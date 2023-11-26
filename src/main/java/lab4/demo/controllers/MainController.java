package lab4.demo.controllers;

import lab4.demo.dto.PointDto;
import lab4.demo.models.Attempt;
import lab4.demo.models.CollectionAttempts;
import lab4.demo.dao.AttemptRepository;
import lab4.demo.models.User;
import lab4.demo.services.AttemptValidator;
import lab4.demo.services.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/attempts")
public class MainController {
    //убрать collectionAttempts
    private CollectionAttempts collectionAttempts;
    private AttemptRepository attemptRepository;

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setCollectionAttempts(CollectionAttempts collectionAttempts) {
        this.collectionAttempts = collectionAttempts;
    }

    @Autowired
    public void setAttemptRepository(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
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
    public Attempt checkHit(@RequestHeader Map<String, String> headers, @RequestBody PointDto pointDto) {
        System.out.println("headers:");
        for (String name : headers.keySet()) {
            System.out.println(name + ": " + headers.get(name));
        }
        System.out.println("--------------------");
        String login = headers.get("login");
        String password = headers.get("password");

        User user = authenticationManager.getOldUserByHash(login, password);
        if (user == null) {
            System.out.println("user == null");
            return null;
        }

        String strX = pointDto.getStrX();
        String strY = pointDto.getStrY();
        String strR = pointDto.getStrR();
        if (AttemptValidator.validateXYR(strX, strY, strR)) {
            Attempt attempt = new Attempt(strX, strY, strR, user);

            collectionAttempts.update(attemptRepository.findAll());

            collectionAttempts.add(attempt);
            attemptRepository.save(attempt);
            return attempt;
        }
        return null;
    }

    @PostMapping("/clear")
    @CrossOrigin
    public void clearAttempts(@RequestHeader Map<String, String> headers) {
        User user = authenticationManager.getOldUserByHash(headers.get("login"), headers.get("password"));
        if (user == null) {
            System.out.println("user == null");
            return;
        }
        attemptRepository.deleteAll();
        collectionAttempts.update(attemptRepository.findAll());
    }

    @GetMapping("/all")
    @CrossOrigin
    public CollectionAttempts getAllAttempts(@RequestHeader Map<String, String> headers) {
        User user = authenticationManager.getOldUserByHash(headers.get("login"), headers.get("password"));
        if (user == null) {
            System.out.println("user == null");
            return new CollectionAttempts(new ArrayList<>());
        }
        collectionAttempts.update(attemptRepository.findAll());
        return collectionAttempts;
    }
}