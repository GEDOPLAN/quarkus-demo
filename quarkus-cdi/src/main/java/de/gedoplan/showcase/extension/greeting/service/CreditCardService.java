package de.gedoplan.showcase.service;

import java.io.Serializable;

public interface CreditCardService extends Serializable {
  public boolean isValid(String cardNo, String owner, int validToYear, int validToMonth, String checkNo);
}
