package com.liferyan.spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.liferyan.spittr.data.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  public String spittles(
      @RequestParam(name = "max", defaultValue = MAX_LONG_AS_STRING) long max,
      @RequestParam(name = "count", defaultValue = "20") int count,
      Model model) {
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

}
