package web.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

@Data

@Controller
@RequestMapping("/admin")
public class AdminControllerNew {

    private final UserService userService;

    @Autowired
    public AdminControllerNew(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/all_users")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("all_users");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id).get());
        return "edit_page";
    }

    @PostMapping("/{id}")
    public String editUser(@ModelAttribute("user") User user,
                           @PathVariable("id") Long id) {
        userService.editUser(user);
        return "edit_page";
    }

    @GetMapping("/all_users/remove_user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/all_users";
    }
}


// Обновление юзера сделать в одну строку через метод merge - +
//Пароли шифровать в сервисе - +
//@Autowired сделать везде через конструктор - +
//в config сделать дир security перенести туда соответствующие классы - +