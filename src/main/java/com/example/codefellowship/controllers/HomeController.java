package com.example.codefellowship.controllers;

import jdk.nashorn.internal.ir.debug.PrintVisitor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage(Principal p, Model m) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        return "home";
    }

    @GetMapping("/signup")
    public String getSignUpPage(Principal p, Model m) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage(Principal p, Model m) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        return "login";
    }

    @GetMapping("/post")
    public String getCreatePostPage(Principal p, Model m) {
        if (p != null) {
            m.addAttribute("username", p.getName());
            m.addAttribute("loggedIn", true);
        }
        return "post";
    }

}
