package ua.goit.shortener.security.controller;

//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//public class AuthController {
//    @GetMapping("/userInfo")
//    @PreAuthorize("hasRole({'user','admin'})")
//    public @ResponseBody Map<String, String> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
//        return Map.of("userName", userDetails.getUsername());
//    }
//
//    @GetMapping("/login")
//    public ModelAndView loginPage() {
//
//        return new ModelAndView("shortener/login");
//    }
//}

//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//@RestController
//@RequestMapping("/api")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//
//    public AuthController(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView login() {
//        return new ModelAndView("shortener/login");
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ModelAndView login(@RequestParam String username, @RequestParam String password) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (Exception e) {
//            // Handle authentication failure, e.g., show an error message
//            return new ModelAndView("shortener/login", "error", "Invalid username or password");
//        }
//
//        // Redirect to a secured page after successful login
//        return new ModelAndView("redirect:/shortener/main-page");
//    }
//
////    @RequestMapping("/logout")
////    public String logout() {
////        new SecurityContextLogoutHandler().logout(SecurityContextHolder.getContext().getAuthentication(), null, null);
////        return "redirect:/login?logout";
////    }
//
//    @RequestMapping("/secure-page")
//    public ModelAndView securePage() {
//        return new ModelAndView("shortener/main-page");
//    }
//}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            return "login?error";
        }
        return "redirect:/secure-page";
    }

    @RequestMapping("/secure-page")
    public String securePage() {
        return "secure-page";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
}


