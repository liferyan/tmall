package com.liferyan.spittr.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ryan on 2017/6/19.
 */
public class SpittleRepositoryImpl implements SpittleRepository {

  @Override
  public List<Spittle> findSpittles(long max, int count) {
    List<Spittle> spittles = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      spittles.add(new Spittle("Spittle" + i, new Date()));
    }
    return spittles;
  }
}
