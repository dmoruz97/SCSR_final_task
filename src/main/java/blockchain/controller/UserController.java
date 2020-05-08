package blockchain.controller;

import blockchain.model.User;
import blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // LOGIN
    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){

        ModelAndView modelAndView = new ModelAndView();
        User user = new User();

        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/login");

        return modelAndView;
    }

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.POST)
    public ModelAndView checkLogin(@Valid User user, BindingResult bindingResult, ModelAndView modelAndView, HttpSession session){

        User userExists = userService.findUserByUsername(user.getUsername());

        if (bindingResult.hasErrors()){
            modelAndView.setViewName("user/login");
        }
        else {
            if (userExists == null){
                modelAndView.setViewName("user/login");
            }
            else {
                if (userExists.getUsername().compareTo(user.getUsername()) == 0 && bCryptPasswordEncoder.matches(user.getPassword(), userExists.getPassword())){
                        session.setAttribute("id_user", userExists.getId_user());
                        session.setAttribute("username", userExists.getUsername());

                        modelAndView.setViewName("home/dashboard");
                        modelAndView.addObject("loggedId", userExists.getId_user());
                        modelAndView.addObject("loggedUsername", userExists.getUsername());
                }
                else {
                    modelAndView.setViewName("user/login");
                    modelAndView.addObject("errorMessage", "User or password wrong!");
                }
            }
        }

        return modelAndView;
    }

    // REGISTRATION
    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){

        ModelAndView modelAndView = new ModelAndView();
        User user = new User();

        modelAndView.setViewName("user/registration");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView addUser(@Valid User user, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(user.getUsername());

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/registration");
        }
        else {
            if (userExists != null) {
                bindingResult.rejectValue("username", "error.user", "There is already a user registered with the username provided");
            }
            else {
                userService.saveUser(user);
                modelAndView.addObject("successMessage", "User has been registered successfully");
                modelAndView.setViewName("user/registration");
            }
        }

        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ModelAndView logout(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");

        return modelAndView;
    }

    // ACCESS DENIED
    @RequestMapping(value="/access-denied", method = RequestMethod.GET)
    public ModelAndView accessDenied() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("access_denied");

        return modelAndView;
    }
}
