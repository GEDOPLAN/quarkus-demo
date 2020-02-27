package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.interceptor.TraceCall;
import de.gedoplan.showcase.service.CreditCardService;
import de.gedoplan.showcase.service.GreetingService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.commons.logging.Log;

@RequestScoped
@Path("demo")
@TraceCall
public class DemoEndpoint {

  @Inject
  // @Formal
  // @Greeting(type = GreetingType.NORMAL)
  // @Greeting(type = GreetingType.FORMAL)
  GreetingService greetingService;

  @Path("greeting")
  @GET
  public String getGreeting() {
    return this.greetingService.getGreeting();
  }

  @Inject
  CreditCardService creditCardService;

  @Path("card-check")
  @GET
  public String getCardCheckResult(
      @QueryParam("cardNo") @DefaultValue("5555555555554444") String cardNo,
      @QueryParam("owner") @DefaultValue("Max Mustermann") String owner,
      @QueryParam("validToYear") @DefaultValue("2024") int validToYear,
      @QueryParam("validToMonth") @DefaultValue("12") int validToMonth,
      @QueryParam("checkNo") @DefaultValue("1234") String checkNo) {
    try {
      return this.creditCardService.isValid(cardNo, owner, validToYear, validToMonth, checkNo) ? "valid" : "invalid";
    } catch (Exception e) {
      return e.toString();
    }
  }

  @Inject
  Log log;

  @PostConstruct
  void postConstruct() {
    this.log.debug("postConstruct");
  }

  @PreDestroy
  void preDestroy() {
    this.log.debug("preDestroy");
  }
}
