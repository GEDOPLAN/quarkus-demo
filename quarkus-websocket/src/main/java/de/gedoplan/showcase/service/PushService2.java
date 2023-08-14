package de.gedoplan.showcase.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/push-2")
public class PushService2 extends AbstractPushService {
}
