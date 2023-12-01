package lab4.demo.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab4.demo.annotations.HasAnyRole;
import lab4.demo.controllers.MainController;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

@Component
@WebFilter(urlPatterns = {"/api/attempts/*"})
public class AttemptsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        System.out.println("Request URI is: " + req.getRequestURI());

        String uri = req.getRequestURI();

        for (Method method : MainController.class.getMethods()) {
            Annotation annotation = method.getAnnotation(HasAnyRole.class);
            if (annotation == null) continue;

            String controllerUri = MainController.class.getAnnotation(RequestMapping.class).value()[0];
            String methodUri = (method.getAnnotation(PostMapping.class) != null
                    ? method.getAnnotation(PostMapping.class).value()[0]
                    : method.getAnnotation(GetMapping.class).value()[0]);

            String curUri = controllerUri + methodUri;
            if (curUri.equals(uri)) {
                HasAnyRole hasAnyRole = (HasAnyRole) annotation;
                String minRoleName = hasAnyRole.minRoleName();

                String login = req.getHeader("login");
                String password = req.getHeader("password");

                for (Iterator<String> it = req.getHeaderNames().asIterator(); it.hasNext(); ) {
                    String header = it.next();
//                    System.out.println(header);
                }

                System.out.println(login);
                System.out.println(password);

                chain.doFilter(req, res);

                System.out.println("Response Status Code is: " + res.getStatus());

                System.out.println("--------------------------------------------");
                return;
            }


//            System.out.println(minRoleName);
//                System.out.println(user.getRole().getName());

        }

//        Method method = MainController.class.getMethod("clearAttempts", Map.class);
//        Annotation annotation = method.getAnnotation(HasAnyRole.class);
//        HasAnyRole hasAnyRole = (HasAnyRole) annotation;
//        String minRoleName = hasAnyRole.minRoleName();


    }
}
