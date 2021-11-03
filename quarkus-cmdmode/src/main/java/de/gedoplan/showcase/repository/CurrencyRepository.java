package de.gedoplan.showcase.repository;

import de.gedoplan.showcase.entity.Currency;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

@ApplicationScoped
public class CurrencyRepository {

  private Map<String, Currency> currencyMap = Map.of(
    "CHF", new Currency("CHF", BigDecimal.valueOf(0.92843)),
    "CNY", new Currency("CNY", BigDecimal.valueOf(0.12443)),
    "EUR", new Currency("EUR", BigDecimal.ONE),
    "GBP", new Currency("GBP", BigDecimal.valueOf(1.12250)),
    "JPY", new Currency("JPY", BigDecimal.valueOf(0.00819)),
    "USD", new Currency("USD", BigDecimal.valueOf(0.88195))
  );

  public Collection<Currency> findAll() {
    return currencyMap.values();
  }

  public Currency findById(String id) {
    return currencyMap.get(id);
  }
}
