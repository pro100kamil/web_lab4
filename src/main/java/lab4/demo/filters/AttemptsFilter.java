package lab4.demo.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab4.demo.annotations.HasAnyRole;
import lab4.demo.controllers.MainController;
import lab4.demo.models.Role;
import lab4.demo.models.User;
import lab4.demo.services.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
public class AttemptsFilter implements Filter {
    private final AuthenticationManager authenticationManager;
    private final Class<MainController> controllerClass = MainController.class;

    @Autowired
    public AttemptsFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    //    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
    private void setCorsHeaders(HttpServletRequest request, HttpServletResponse response) {
//        String origin = request.getHeader("Origin");
//        response.setHeader("Access-Control-Allow-Origin", origin);
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
//        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, login, password");
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

                String login = req.getHeader("login");
                String password = req.getHeader("password");

//                for (Iterator<String> it = req.getHeaderNames().asIterator(); it.hasNext(); ) {
//                    String header = it.next();
//                    System.out.println(header);
//                }


                System.out.println(login);
                System.out.println(password);

                User user = authenticationManager.getOldUserByHash(login, password);
                if (user == null) continue;

                String curRoleName = user.getRole().getName();

                Role minRole = authenticationManager.getRoleByName(minRoleName);
                Role curRole = user.getRole();

//                if (curRole >= minRole)
                if (curRole.compareTo(minRole) >= 0) {
                    chain.doFilter(req, res);
                } else {
//                    res.setStatus(403);
//                    res.sendError(403);
//                    res.
//                    chain.doFilter(req, res);
                    res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                    return;
//                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }

                System.out.println(minRoleName);
                System.out.println(curRoleName);

                System.out.println("Response Status Code is: " + res.getStatus());

                System.out.println("--------------------------------------------");
                return;
            }


//            System.out.println(minRoleName);
//                System.out.println(user.getRole().getName());

        }
        chain.doFilter(req, res);

//        Method method = MainController.class.getMethod("clearAttempts", Map.class);
//        Annotation annotation = method.getAnnotation(HasAnyRole.class);
//        HasAnyRole hasAnyRole = (HasAnyRole) annotation;
//        String minRoleName = hasAnyRole.minRoleName();


    }
}
