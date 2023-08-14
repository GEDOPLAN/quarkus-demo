package de.gedoplan.showcase.service;

import java.util.Set;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;

import io.vertx.core.impl.ConcurrentHashSet;
import org.jboss.logging.Logger;

public abstract class AbstractPushService {
  Set<Session> sessions = new ConcurrentHashSet<>();

  protected Logger logger = Logger.getLogger(getClass());

  @OnOpen
  public void onOpen(Session session) {
    this.logger.debugf("Session opened: %s", session);
    this.sessions.add(session);
  }

  @OnClose
  public void onClose(Session session) {
    this.logger.debugf("Session closed: %s", session);
    this.sessions.remove(session);
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    this.logger.debugf("Session cancelled: %s (%s)", session, throwable);
    this.sessions.remove(session);
  }

  @OnMessage
  public void onMessage(String message) {
    this.logger.debugf("Received message \"%s\"", message);
  }

  public void send(String text) {
    this.logger.debugf("Sending \"%s\" to %d browsers", text, this.sessions.size());
    this.sessions.forEach(s -> {
      s.getAsyncRemote().sendText(text, result -> {
        if (result.getException() != null) {
          this.logger.error("Unable to send message", result.getException());
        }
      });
    });
  }
}
