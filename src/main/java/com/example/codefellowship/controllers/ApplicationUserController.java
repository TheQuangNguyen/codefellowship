package com.example.codefellowship.controllers;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.ApplicationUserRepository;
import com.example.codefellowship.models.Post;
import com.example.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

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

    @GetMapping("/myprofile")
    public String getUserDetailPage(Model m, Principal p) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("currentUser", currentUser);
        return "userDetail";
    }

    @GetMapping("/myprofile/{id}")
    public String getOtherUsersDetailPage(@PathVariable("id") long id, Model m, Principal p) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        ApplicationUser currentUser = applicationUserRepository.getOne(id);
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("currentUser", currentUser);
        m.addAttribute("loggedInUser", loggedInUser);
        return "userDetail";
    }

    @GetMapping("/myprofile/edit")
    public String getUserDetailEditPage(Model m, Principal p) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("currentUser", currentUser);
        return "userDetailEdit";
    }

    @PostMapping("/myprofile/edit")
    public RedirectView editUserDetailPage(String username, String firstName, String lastName, String dateOfBirth, String bio, Principal p) {
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        currentUser.setUsername(username);
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setDateOfBirth(dateOfBirth);
        currentUser.setBio(bio);
        applicationUserRepository.save(currentUser);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/users")
    public String getAllUsersPage(Model m, Principal p) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        List<ApplicationUser> users = applicationUserRepository.findAll();
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        users.remove(currentUser);
        m.addAttribute("users", users);
        return "otherUsers";
    }

    @GetMapping("/feed")
    public String getMyFeedPage(Model m, Principal p) {
        if (p != null) {
            m.addAttribute("username", p.getName());
        }
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("currentUser", loggedInUser);
        return "feed";
    }

    @PostMapping("/follow")
    public RedirectView addUsersIFollow(@Param("id") long id, Principal p) {
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser userToFollow = applicationUserRepository.getOne(id);
        loggedInUser.addUsersToFollow(userToFollow);
        applicationUserRepository.save(loggedInUser);
        return new RedirectView("/myprofile");
    }

    @PostMapping("/post")
    public RedirectView createNewPost(String postBody, Principal p) {
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        Post newPost = new Post(postBody, currentUser);

        postRepository.save(newPost);
        return new RedirectView("/myprofile");
    }
}
