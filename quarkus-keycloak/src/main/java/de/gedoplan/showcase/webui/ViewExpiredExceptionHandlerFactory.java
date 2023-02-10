package de.gedoplan.showcase.webui;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ViewExpiredExceptionHandlerFactory extends ExceptionHandlerFactory {

  public ViewExpiredExceptionHandlerFactory(ExceptionHandlerFactory parent) {
    super(parent);
  }

  @Override
  public ExceptionHandler getExceptionHandler() {
    return new ViewExpiredExceptionHandler(getWrapped().getExceptionHandler());
  }
}
