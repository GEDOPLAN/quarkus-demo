package de.gedoplan.showcase.service;

import io.vertx.core.impl.ConcurrentHashSet;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.util.Set;

@ApplicationScoped
@ServerEndpoint("/push1")
public class PushService1 {
  Set<Session> sessions = new ConcurrentHashSet<>();

  @Inject
  Logger logger;

  @OnOpen
  public void onOpen(Session session) {
    logger.debugf("Session opened: %s", session);
    sessions.add(session);
  }

  @OnClose
  public void onClose(Session session) {
    logger.debugf("Session closed: %s", session);
    sessions.remove(session);
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    logger.debugf("Session cancelled: %s (%s)", session, throwable);
    sessions.remove(session);
  }

  @OnMessage
  public void onMessage(String message) {
    logger.debugf("Received message \"%s\"", message);

    send(message + " (from webSocket)");
  }

  public void send(String message) {
    logger.debugf("Sending \"%s\" to %d browsers", message, sessions.size());
    sessions.forEach(s -> {
      s.getAsyncRemote().sendObject(message, result ->  {
        if (result.getException() != null) {
          logger.error("Unable to send message", result.getException());
        }
      });
    });
  }
}
