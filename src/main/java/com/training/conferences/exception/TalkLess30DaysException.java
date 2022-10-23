package com.training.conferences.exception;

public class TalkLess30DaysException extends ConferenceException {

  public TalkLess30DaysException(String message) {
    super(message);
  }
}
