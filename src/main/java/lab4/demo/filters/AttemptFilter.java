package lab4.demo.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab4.demo.annotations.HasAnyRole;
import lab4.demo.controllers.AttemptController;
import lab4.demo.models.Role;
import lab4.demo.models.User;
import lab4.demo.services.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
public class AttemptFilter implements Filter {
    private final AuthenticationManager authenticationManager;
    private final Class<AttemptController> controllerClass = AttemptController.class;

    @Autowired
    public AttemptFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private void setCorsHeaders(HttpServletRequest request, HttpServletResponse response) {
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200"); //local
        response.setHeader("Access-Control-Allow-Origin", "https://se.ifmo.ru");    //helios
        response.setHeader("Access-Control-Allow-Private-Network", "true");         //helios

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        setCorsHeaders(req, res);

        System.out.println("Request URI is: " + req.getRequestURI());

        String uri = req.getRequestURI();

        System.out.println("Method: " + req.getMethod());
        if (!req.getMethod().equals("POST") && !req.getMethod().equals("GET")) {
            return;
        }

        for (Method method : controllerClass.getMethods()) {
            Annotation annotation = method.getAnnotation(HasAnyRole.class);
            if (annotation == null) continue;

            String controllerUri = controllerClass.getAnnotation(RequestMapping.class).value()[0];

            String methodUri = (method.getAnnotation(PostMapping.class) != null
                    ? method.getAnnotation(PostMapping.class).value()[0]
                    : method.getAnnotation(GetMapping.class).value()[0]);

            String curUri = controllerUri + methodUri;
            if (curUri.equals(uri)) {
                HasAnyRole hasAnyRole = (HasAnyRole) annotation;
                String minRoleName = hasAnyRole.minRoleName();

                //вывод всех заголовков, для тестирования может пригодиться
//                Collections.list(req.getHeaderNames()).stream().forEach(System.out::println);

                String authorizationHeader = req.getHeader("authorization");

                User user = authenticationManager.getOldUserByAuthorizationHeader(authorizationHeader);

                if (user == null) {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                }
                else {
                    Role minRole = authenticationManager.getRoleByName(minRoleName);
                    Role curRole = user.getRole();


                    if (curRole.compareTo(minRole) >= 0) {  // if (curRole >= minRole)
                        chain.doFilter(req, res);
                    } else {
                        res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                    }
                }

                System.out.println("Response Status Code is: " + res.getStatus());

                System.out.println("--------------------------------------------");
                return;
            }

        }
        chain.doFilter(req, res);
    }
}
