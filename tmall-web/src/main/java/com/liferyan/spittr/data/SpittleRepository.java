package com.liferyan.spittr.data;

import com.liferyan.spittr.Spittle;
import java.util.List;

/**
 * Created by Ryan on 2017/6/19.
 */
public interface SpittleRepository {

  List<Spittle> findSpittles(long max, int count);

  Spittle findOne(long id);

}
