package com.liferyan.spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.liferyan.spittr.data.Spittle;
import com.liferyan.spittr.data.SpittleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ryan on 2017/6/19.
 */
@Controller
@RequestMapping("/spittles")
public class SpittleController {

  private SpittleRepository spittleRepository;

  @Autowired
  public SpittleController(SpittleRepository spittleRepository) {
    this.spittleRepository = spittleRepository;
  }

  /**
   * Spring自动推断Model和View
   */
  @RequestMapping(method = GET)
  public List<Spittle> spittles() {
    return spittleRepository.findSpittles(Long.MAX_VALUE, 20);
  }

  /*@RequestMapping(method = GET)
  public String spittles(Model model) {
    model.addAttribute(spittleRepository.findSpittles(Long.MAX_VALUE, 20));
    return "spittles";
  }*/

}
