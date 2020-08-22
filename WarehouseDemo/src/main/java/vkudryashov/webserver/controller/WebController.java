package vkudryashov.webserver.controller;

import vkudryashov.webserver.model.User;
import vkudryashov.webserver.service.SecurityService;
import vkudryashov.webserver.service.UserService;
import vkudryashov.webserver.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for {@link User}'s pages.
 *
 * @author Vladimir Kudryashov
 * @version 1.0
 * */

@Controller
public class WebController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    UserValidator userValidator;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(Model model){
        return "login";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, String error, String logout){
        securityService.findLoggedInUser().setAuthenticated(false);
        return "redirect:/home";
    }

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public String home(Model model){
        Authentication loggedInUser = securityService.findLoggedInUser();
        if (loggedInUser == null ||
                loggedInUser.getAuthorities().toString().equals("[ROLE_ANONYMOUS]")) {
            model.addAttribute("isLoggedIn", "");
            return "redirect:/login";
        }else{
            model.addAttribute("name",loggedInUser.getName());
            model.addAttribute("users",userService.getAllUsers());
            return "home";
        }
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model){
        model.addAttribute("name",securityService.findLoggedInUser().getName());
        model.addAttribute("role",securityService.findLoggedInUser().getAuthorities());
        return "admin";
    }
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,Model model){
        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.save(userForm);

        return "redirect:/home";
    }


}
