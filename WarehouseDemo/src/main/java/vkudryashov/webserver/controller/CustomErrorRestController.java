package vkudryashov.webserver.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vkudryashov.webserver.model.Error;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CustomErrorRestController implements ErrorController {
    @RequestMapping(value = "/error",produces = "application/json")
    @ResponseBody
    public void handleError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUrl = request.getAttribute("javax.servlet.forward.request_uri").toString();
        String url = requestUrl.matches("^/rest/.*") ? "/rest/errors" : "/web/errors";
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @RequestMapping(value = "/rest/errors",produces = "application/json")
    @ResponseBody
    public Error restError(HttpServletRequest request){
        String statusCode = request.getAttribute("javax.servlet.error.status_code").toString();
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String exceptionMessage = exception == null ? null : exception.getMessage();
        String errorMessage = request.getAttribute("javax.servlet.error.message").toString();
        return new Error(statusCode, exceptionMessage, errorMessage);
    }

    @RequestMapping(value = "/web/errors", produces = "text/html")
    public String webError(HttpServletRequest request, Model model){
        String statusCode = request.getAttribute("javax.servlet.error.status_code").toString();
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String exceptionMessage = exception == null ? null : exception.getMessage();
        String errorMessage = request.getAttribute("javax.servlet.error.message").toString();
        model.addAttribute("error",new Error(statusCode, exceptionMessage, errorMessage));
        return "/errors";
    }

    @Override
    public String getErrorPath() {
        return "/errors";
    }
}
