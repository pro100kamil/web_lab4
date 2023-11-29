package lab4.demo.controllers;

import lab4.demo.dto.PointDto;
import lab4.demo.models.Attempt;
import lab4.demo.dao.AttemptRepository;
import lab4.demo.models.User;
import lab4.demo.services.AttemptValidator;
import lab4.demo.services.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attempts")
public class MainController {
    //убрать collectionAttempts
    private AttemptRepository attemptRepository;

    private AuthenticationManager authenticationManager;

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
        String login = headers.get("login");
        String password = headers.get("password");

        User user = authenticationManager.getOldUserByHash(login, password);
        if (user == null) {
            return null;
        }

        String strX = pointDto.getStrX();
        String strY = pointDto.getStrY();
        String strR = pointDto.getStrR();
        if (AttemptValidator.validateXYR(strX, strY, strR)) {
            Attempt attempt = new Attempt(strX, strY, strR, user);

            attemptRepository.save(attempt);
            return attempt;
        }
        return null;
    }

    @PostMapping("/clear")
    @CrossOrigin
    @Transactional
    public void clearAttempts(@RequestHeader Map<String, String> headers) {
        User user = authenticationManager.getOldUserByHash(headers.get("login"), headers.get("password"));
        if (user == null) {
            return;
        }
        attemptRepository.deleteByUser(user);
    }

    @GetMapping("/all")
    @CrossOrigin
    public List<Attempt> getAllAttempts(@RequestHeader Map<String, String> headers) {
        User user = authenticationManager.getOldUserByHash(headers.get("login"), headers.get("password"));
        if (user == null) {
            return new ArrayList<>();
        }
        return attemptRepository.findByUser(user);
    }
}