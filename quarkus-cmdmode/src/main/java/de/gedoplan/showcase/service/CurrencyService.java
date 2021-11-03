package de.gedoplan.showcase.service;

import de.gedoplan.showcase.entity.Currency;
import de.gedoplan.showcase.repository.CurrencyRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

@ApplicationScoped
public class CurrencyService {

  @Inject CurrencyRepository currencyRepository;

  public BigDecimal convert(@NotNull BigDecimal amount, @NotNull Currency from, @NotNull Currency to) {
    return amount
      .multiply(from.getEuroValue())
      .divide(to.getEuroValue(), 4, RoundingMode.HALF_UP);
  }

  public BigDecimal convert(@NotNull BigDecimal amount, @NotNull String fromId, @NotNull String toId) {
    Currency from = currencyRepository.findById(fromId);
    Currency to = currencyRepository.findById(toId);
    return convert(amount, from, to);
  }
}
