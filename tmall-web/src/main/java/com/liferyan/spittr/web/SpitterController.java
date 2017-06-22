package com.liferyan.spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.liferyan.spittr.Spitter;
import com.liferyan.spittr.data.SpitterRepository;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Ryan on 2017/6/20.
 */
@Controller
@RequestMapping("/spitter")
public class SpitterController {

  private SpitterRepository spitterRepository;

  @Autowired
  public SpitterController(SpitterRepository spitterRepository) {
    this.spitterRepository = spitterRepository;
  }

  @RequestMapping(path = "/register", method = GET)
  public String showRegistrationForm(Model model) {
    model.addAttribute(new Spitter());
    return "registerForm";
  }

  @RequestMapping(path = "/register", method = POST)
  public String registerSpitter(@Valid Spitter spitter, Errors errors, RedirectAttributes model) {
    if (errors.hasErrors()) {
      return "registerForm";
    }
    spitterRepository.save(spitter);
    model.addFlashAttribute("spitter", spitter);
    //for Chinese
    String username = null;
    try {
      username = URLEncoder.encode(spitter.getUsername(), "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "redirect:/spitter/" + username;
  }

  @RequestMapping(path = "/{username}", method = GET)
  public String showSpitterProfile(@PathVariable("username") String username, Model model) {
    if (!model.containsAttribute("spitter")) {
      model.addAttribute(spitterRepository.findByUsername(username));
    }
    return "profile";
  }

}
