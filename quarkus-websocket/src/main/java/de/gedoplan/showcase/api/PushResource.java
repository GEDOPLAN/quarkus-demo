package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.PushService1;
import de.gedoplan.showcase.service.PushService2;
import de.gedoplan.showcase.service.PushService3;
import de.gedoplan.showcase.webui.MessagePresenter;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/push")
public class PushResource {

  @Inject
  PushService1 pushService1;

  @Inject
  PushService2 pushService2;

  @Inject
  PushService3 pushService3;

  @Inject
  MessagePresenter messagePresenter;

  @POST
  @Consumes("*/*")
  public void push(String text) {
    this.pushService1.send(text + " (appended in DOM)");

    this.messagePresenter.append(text + " (appended to managed bean property)");
    this.pushService2.send("unused");
    this.pushService3.send("alsoUnused");

  }
}