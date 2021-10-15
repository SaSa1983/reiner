package eu.kneipel.telem.ws;

import javax.websocket.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TelemMessageCoder  implements Decoder.Text<Void>, Encoder.Text<TelemMessage> {

  @Override public Void decode(String s) throws DecodeException {
    throw new DecodeException(s, "Cannot handle messages from client");
  }

  @Override public boolean willDecode(String s) {
    return false;
  }

  @Override public void init(EndpointConfig config) {
    //nothing to do here
  }

  @Override public void destroy() {
    //nothing to do here
  }

  @Override public String encode(TelemMessage object) throws EncodeException {
    try {
      return new ObjectMapper().writeValueAsString(object);
    }
    catch(JsonProcessingException e) {
      throw new EncodeException(object, "Cannot encode message", e);
    }
  }
}
