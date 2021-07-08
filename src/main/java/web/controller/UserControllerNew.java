package web.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

@Data

@Controller
public class UserControllerNew {

    private final UserService userService;

    @Autowired
    public UserControllerNew(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/new_user")
    public String newUser() {
        return "new_user";
    }

    @PostMapping("/new_user")
    public String createNewUser(@ModelAttribute("user") User user) {
        user.setPasswordReal(user.getPassword());

        userService.createNewUser(user);
        return "redirect:/new_user";
    }

    @GetMapping("/user")
    public ModelAndView showUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user_page");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

}
