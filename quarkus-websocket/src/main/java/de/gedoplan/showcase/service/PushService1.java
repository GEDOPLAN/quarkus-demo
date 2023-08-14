package de.gedoplan.showcase.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/push-1")
public class PushService1 extends AbstractPushService {
}
