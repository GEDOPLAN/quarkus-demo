package de.gedoplan.showcase.webui;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named
public class MessagePresenter {
  private StringBuilder message = new StringBuilder();

  public String getMessage() {
    return this.message.toString();
  }

  public void append(String text) {
    this.message.append(text);
  }
}
