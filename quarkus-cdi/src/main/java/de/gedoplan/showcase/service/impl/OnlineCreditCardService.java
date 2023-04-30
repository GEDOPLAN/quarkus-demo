package de.gedoplan.showcase.service.impl;

import de.gedoplan.showcase.service.CreditCardService;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OnlineCreditCardService implements CreditCardService {

  @Override
  public boolean isValid(String cardNo, String owner, int validToYear, int validToMonth, String checkNo) {
    throw new RuntimeException("Not yet implemented");
  }
}
