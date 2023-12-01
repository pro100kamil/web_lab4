package lab4.demo.controllers;

import lab4.demo.annotations.HasAnyRole;
import lab4.demo.dto.PointDto;
import lab4.demo.models.Attempt;
import lab4.demo.dao.AttemptRepository;
import lab4.demo.models.User;
import lab4.demo.services.AttemptValidator;
import lab4.demo.services.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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


//        //TODO рассмотреть возвращение ResponseEntity и возращение ошибок

    @GetMapping("/all")
    @CrossOrigin
    @HasAnyRole(minRoleName = minUserRoleName)
    public List<Attempt> getAllAttempts(@RequestHeader Map<String, String> headers) throws NoSuchMethodException {
        User user = authenticationManager.getOldUserByHash(headers.get("login"), headers.get("password"));
        if (user == null) {
            return new ArrayList<>();
        }

//        Method method = MainController.class.getMethod("getAllAttempts", Map.class);
//        Annotation annotation = method.getAnnotation(HasAnyRole.class);
//        HasAnyRole hasAnyRole = (HasAnyRole) annotation;
//        String minRoleName = hasAnyRole.minRoleName();



//        System.out.println("------------------------");

        return attemptRepository.findByUser(user);
    }

    @PostMapping("/new")
    @CrossOrigin
    @HasAnyRole(minRoleName = averageUserRoleName)
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
    @HasAnyRole(minRoleName = maxUserRoleName)
    public void clearAttempts(@RequestHeader Map<String, String> headers) {
        User user = authenticationManager.getOldUserByHash(headers.get("login"), headers.get("password"));
        if (user == null) {
            return;
        }
        attemptRepository.deleteByUser(user);
    }

    public void ahah(int a, String b) {

    }

    public static void main(String[] args) throws NoSuchMethodException {
//        System.out.println(MainController.class.getMethod("ahah", int.class, String.class));
        Method method = MainController.class.getMethod("clearAttempts", Map.class);
        Annotation annotation = method.getAnnotation(HasAnyRole.class);
        HasAnyRole hasAnyRole = (HasAnyRole) annotation;
        String minRoleName = hasAnyRole.minRoleName();

        System.out.println(minRoleName);

        System.out.println("------------------------");
    }
}