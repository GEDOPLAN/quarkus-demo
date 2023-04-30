package de.gedoplan.showcase.service.impl;

import de.gedoplan.showcase.service.CreditCardService;
import jakarta.annotation.Priority;
import java.util.Calendar;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
@Priority(1)
public class MockCreditCardService implements CreditCardService {

  @Override
  public boolean isValid(String cardNo, String owner, int validToYear, int validToMonth, String checkNo) {
    Calendar calendar = Calendar.getInstance();
    int curYear = calendar.get(Calendar.YEAR);
    if (validToYear < curYear) {
      return false;
    }
    int curMonth = calendar.get(Calendar.MONTH) + 1;
    if (validToYear == curYear && validToMonth < curMonth) {
      return false;
    }

    if (cardNo.startsWith("34") || cardNo.startsWith("37")) {
      // AMEX
      return cardNo.length() == 15;
    }

    if (cardNo.startsWith("4")) {
      // VISA
      return cardNo.length() == 13 || cardNo.length() == 16;
    }

    if (cardNo.startsWith("51") || cardNo.startsWith("52") || cardNo.startsWith("53") || cardNo.startsWith("54") || cardNo.startsWith("55")) {
      // MC
      return cardNo.length() == 16;
    }

    throw new IllegalArgumentException("Test implemented for AMEX, VISA and MC only");
  }
}
