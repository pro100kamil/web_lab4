package lab4.demo.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class CollectionAttempts {
    //нужно использовать потокобезопасные коллекции,
    // потому что взаимодействия с коллекциями могут происходить в разных потоках.
    private List<Attempt> attempts = new ArrayList<>();

    public CollectionAttempts(List<Attempt> attempts) {
        this.attempts = attempts;
    }

    //функция нужна, потому что могут быть изменения бд при непосредственном взаимодействии
    public void update(Iterable<Attempt> newIterable) {
        attempts.clear();
        for (Attempt attempt : newIterable) {
            attempts.add(attempt);
        }
    }

    public void add(Attempt attempt) {
        attempts.add(attempt);
    }

    public void clear() {
        attempts.clear();
    }

}
