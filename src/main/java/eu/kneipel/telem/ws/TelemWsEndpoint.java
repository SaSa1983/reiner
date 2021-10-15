package eu.kneipel.telem.ws;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/telem/endpoint", encoders = TelemMessageCoder.class, decoders = TelemMessageCoder.class)
public class TelemWsEndpoint {

  private static final Logger LOG = Logger.getLogger(TelemWsEndpoint.class.getName());

  /**
   * Map with java http session id <-> websocket session
   */
  private static final List<Session> WS_SESSIONS = new CopyOnWriteArrayList<>();

  /**
   * Called when a client connects via Websocket to this endpoint
   *
   * @param session the websocket session
   * @param config  the websocket endpoint configuration
   * @see Endpoint#onOpen(Session, EndpointConfig)
   */
  @OnOpen public void onOpen(final Session session, final EndpointConfig config) {
    WS_SESSIONS.add(session);
  }


  /**
   * Called if a message on the websocket is received
   *
   * @param session the websocket on which the message was received
   * @param reason  close reason
   */
  @OnClose public void onClose(final Session session, final CloseReason reason) {
    WS_SESSIONS.remove(session);
  }



  /**
   * Called upon an unexpected error in the websocket session
   *
   * @param session the websocket session in which the error occured
   * @param cause   the causing exception
   * @see Endpoint#onError(Session, Throwable)
   */
  @OnError public void onError(final Session session, final Throwable cause) {
    // Nothing to do here
  }

  /**
   * Called if a message on the websocket is received
   *
   * @param message the message object which must match the decoder implementation in {@link TelemMessageCoder}
   * @param session the websocket on which the message was received
   */
  @OnMessage public void onMessage(final Void message, final Session session) {
    //We do not receive messges from the client
  }

  public static void sendMessage(TelemMessage message) throws EncodeException, IOException {
    for (Session session : WS_SESSIONS) {
      session.getBasicRemote().sendObject(message);
    }
  }
}
