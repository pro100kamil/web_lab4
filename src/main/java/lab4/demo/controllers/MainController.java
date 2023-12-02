package lab4.demo.controllers;

import lab4.demo.annotations.HasAnyRole;
import lab4.demo.dto.PointDto;
import lab4.demo.models.Attempt;
import lab4.demo.dao.AttemptRepository;
import lab4.demo.models.User;
import lab4.demo.services.AttemptValidator;
import lab4.demo.services.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attempts")
public class MainController {
    private AttemptRepository attemptRepository;

    private AuthenticationManager authenticationManager;

    private final String minUserRoleName = "min_user";
    private final String averageUserRoleName = "average_user";
    private final String maxUserRoleName = "max_user";

    @Autowired
    public void setAttemptRepository(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/all")
    @CrossOrigin
    @HasAnyRole(minRoleName = minUserRoleName)
    public List<Attempt> getAllAttempts(@RequestHeader Map<String, String> headers) {
//        System.out.println(headers.get("authorization"));
//
//        for (String header : headers.keySet()) {
//            System.out.println(header);
//        }

//        User user = authenticationManager.getOldUserByHash(headers.get("login"), headers.get("password"));
        User user = authenticationManager.getOldUserByAuthorizationHeader(headers.get("authorization"));
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return attemptRepository.findByUser(user);
    }

    @PostMapping("/new")
    @CrossOrigin
    @HasAnyRole(minRoleName = averageUserRoleName)
    public Attempt checkHit(@RequestHeader Map<String, String> headers, @RequestBody PointDto pointDto) {
        User user = authenticationManager.getOldUserByAuthorizationHeader(headers.get("authorization"));

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String strX = pointDto.getStrX();
        String strY = pointDto.getStrY();
        String strR = pointDto.getStrR();

        if (!AttemptValidator.validateXYR(strX, strY, strR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Attempt attempt = new Attempt(strX, strY, strR, user);

        attemptRepository.save(attempt);
        return attempt;
    }

    @PostMapping("/clear")
    @CrossOrigin
    @Transactional
    @HasAnyRole(minRoleName = maxUserRoleName)
    public void clearAttempts(@RequestHeader Map<String, String> headers) {
        User user = authenticationManager.getOldUserByAuthorizationHeader(headers.get("authorization"));

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        attemptRepository.deleteByUser(user);
    }
}