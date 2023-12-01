package lab4.demo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//TODO
@Target(ElementType.METHOD)             //аннотация применяется только к методам
@Retention(RetentionPolicy.RUNTIME)     //аннотация доступна в рантайме
public @interface HasAnyRole {

    String minRoleName();  //можно выполнять действия, если роль такая же или больше чем данная
}
