package de.gedoplan.showcase.command;

import java.math.BigDecimal;

import javax.inject.Inject;

import de.gedoplan.showcase.service.CurrencyService;
import picocli.CommandLine;

@CommandLine.Command
public class PicocliMain implements Runnable {

  @CommandLine.Option(names = {"-f", "--from"}, description = "Currency to convert from", defaultValue = "USD")
  String fromCurrencyId;

  @CommandLine.Option(names = {"-t", "--to"}, description = "Currency to convert to", defaultValue = "EUR")
  String toCurrencyId;

  @CommandLine.Option(names = {"-a", "--amount"}, description = "Value to convert", defaultValue = "1")
  BigDecimal fromAmount;

  @Inject CurrencyService currencyService;

  @Override public void run() {
    System.out.printf("%.4f %s = %.4f %s\n",
      fromAmount,
      fromCurrencyId,
      currencyService.convert(fromAmount, fromCurrencyId, toCurrencyId),
      toCurrencyId);
  }
}
