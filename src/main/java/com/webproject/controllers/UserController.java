package com.webproject.controllers;

import com.webproject.models.User;
import com.webproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("user", new User());
        return mav;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request,
                                     @ModelAttribute("user") User user) {
        ModelAndView mav;
        User u = userService.validateUser(user);
        if (null != u) {
            request.getSession().setAttribute("userID", u.getId());
            mav = new ModelAndView("redirect:/home");

        } else {
            mav = new ModelAndView("login");
            mav.addObject("message", "Username or Password is wrong!!");
        }
        return mav;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHome() {
        return "home";
    }
}