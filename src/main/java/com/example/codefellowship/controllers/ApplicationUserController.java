package com.example.codefellowship.controllers;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.ApplicationUserRepository;
import com.example.codefellowship.models.Post;
import com.example.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ApplicationUserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    private long currentUserId;

    @PostMapping("/signup")
    public RedirectView createNewUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) {
        ApplicationUser newUser = new ApplicationUser(username,
                                passwordEncoder.encode(password),
                                firstName,
                                lastName,
                                dateOfBirth,
                                bio);

        applicationUserRepository.save(newUser);
        return new RedirectView("/");
    }

    @GetMapping("/users/{id}")
    public String getUserDetailPage(@PathVariable long id, Model m, Principal p) {
        if (p != null) {
            m.addAttribute("username", p.getName());
            m.addAttribute("loggedIn", true);
        } else {
            m.addAttribute("username", "User");
        }
        ApplicationUser currentUser = (ApplicationUser) applicationUserRepository.getOne(id);

        m.addAttribute("currentUser", currentUser);
        return "userDetail";
    }

    @PostMapping("/post")
    public RedirectView createNewPost(String postBody, Principal p) {
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        Post newPost = new Post(postBody, currentUser);

        postRepository.save(newPost);
        return new RedirectView("/users/" + currentUser.getId());
    }
}
