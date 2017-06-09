package com.liferyan.tmall.service;

/**
 * Created by Ryan on 2017/6/8.
 */
public class TextEditor {

  private SpellChecker spellChecker;
  private String name;

  public void setSpellChecker(SpellChecker spellChecker) {
    this.spellChecker = spellChecker;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void spellCheck() {
    spellChecker.checkSpelling();
  }
}
