package eu.kneipel.telem.data;

import java.io.IOException;
import java.util.Random;
import javax.websocket.EncodeException;
import eu.kneipel.telem.ws.TelemMessage;
import eu.kneipel.telem.ws.TelemWsEndpoint;

public class DataGenerator extends Thread {

  private boolean shutdown = false;

  private Random random = new Random();

  @Override public void run() {
    while(!this.shutdown) {
      TelemMessage message = new TelemMessage()
        .setAxisTemperatureBottomLeft(random.nextInt(100))
        .setAxisTemperatureBottomRight(random.nextInt(100))
        .setAxisTemperatureTopLeft(random.nextInt(100))
        .setAxisTemperatureTopRight(random.nextInt(100))
        .setBreakTemperatureBottomLeft(random.nextInt(100))
        .setBreakTemperatureBottomRight(random.nextInt(100))
        .setBreakTemperatureTopLeft(random.nextInt(100))
        .setBreakTemperatureTopRight(random.nextInt(100))
        .setInnerWheelTemperatureBottomLeft(random.nextInt(100))
        .setInnerWheelTemperatureBottomRight(random.nextInt(100))
        .setInnerWheelTemperatureTopLeft(random.nextInt(100))
        .setInnerWheelTemperatureTopRight(random.nextInt(100))
        .setOuterWheelTemperatureBottomLeft(random.nextInt(100))
        .setOuterWheelTemperatureBottomRight(random.nextInt(100))
        .setOuterWheelTemperatureTopLeft(random.nextInt(100))
        .setOuterWheelTemperatureTopRight(random.nextInt(100))
        .setSpeed(random.nextInt(350))
        .setRpm(random.nextInt(14000))
        .setThrottle(random.nextInt(100) * (random.nextBoolean() ? 1 : -1))
        .setSteering(random.nextInt(100) * (random.nextBoolean() ? 1 : -1));


      try {
        TelemWsEndpoint.sendMessage(message);
        Thread.sleep(10);
      }
      catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      catch (EncodeException | IOException e) {
        //Ignore oder behandeln?
      }
    }
  }

  public void markForShutdown() {
    this.shutdown = true;
  }
}
