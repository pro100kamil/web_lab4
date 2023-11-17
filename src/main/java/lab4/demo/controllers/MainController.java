package lab4.demo.controllers;

import lab4.demo.models.Attempt;
import lab4.demo.models.CollectionAttempts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attempts")
public class MainController {
    private CollectionAttempts collectionAttempts;

    @Autowired
    public void setCollectionAttempts(CollectionAttempts collectionAttempts) {
        this.collectionAttempts = collectionAttempts;
    }

    @PostMapping("/new")
    public Attempt check_hit(@RequestParam(value = "x", required = false, defaultValue = "-1") String strX,
                             @RequestParam(value = "y", required = false, defaultValue = "-1") String strY,
                             @RequestParam(value = "r", required = false, defaultValue = "-1") String strR) {
        Attempt attempt = new Attempt(strX, strY, strR);
        collectionAttempts.add(attempt);
        return attempt;
    }

    @GetMapping("/all")
    public CollectionAttempts get_all_attempts() {
        return collectionAttempts;
    }
}