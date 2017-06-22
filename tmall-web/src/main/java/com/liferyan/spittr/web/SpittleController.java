package com.liferyan.spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.liferyan.spittr.Spittle;
import com.liferyan.spittr.data.SpittleRepository;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ryan on 2017/6/19.
 */
@Controller
@RequestMapping("/spittles")
public class SpittleController {

  private static final String MAX_LONG_AS_STRING = "" + Long.MAX_VALUE;

  private SpittleRepository spittleRepository;

  @Autowired
  public SpittleController(SpittleRepository spittleRepository) {
    this.spittleRepository = spittleRepository;
  }

  @RequestMapping(method = GET)
  public String showSpittleList(
      @RequestParam(name = "max", defaultValue = MAX_LONG_AS_STRING) long max,
      @RequestParam(name = "count", defaultValue = "20") int count,
      Model model) {
    model.addAttribute(new SpittleForm());
    model.addAttribute(spittleRepository.findSpittles(max, count));
    return "spittles";
  }

  @RequestMapping(path = "/{spittleId}", method = GET)
  public String showSpittle(
      @PathVariable("spittleId") long id,
      Model model) {
    model.addAttribute(spittleRepository.findOne(id));
    return "spittle";
  }

  @RequestMapping(method = POST)
  public String saveSpittle(@Valid SpittleForm spittleForm, Errors errors, Model model) {
    if (errors.hasErrors()) {
      model.addAttribute(spittleRepository.findSpittles(Long.MAX_VALUE, 20));
      return "spittles";
    }
    spittleRepository.save(
        new Spittle(null, spittleForm.getMessage(), new Date(), spittleForm.getLongitude(),
            spittleForm.getLatitude()));
    return "redirect:/spittles";
  }

}
