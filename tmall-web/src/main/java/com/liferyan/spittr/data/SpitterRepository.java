package com.liferyan.spittr.data;

import com.liferyan.spittr.Spitter;

/**
 * Created by Ryan on 2017/6/20.
 */
public interface SpitterRepository {

  Spitter save(Spitter spitter);

  Spitter findByUsername(String username);

}
