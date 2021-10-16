package eu.kneipel.telem.data;

import java.io.IOException;
import java.util.Random;
import javax.websocket.EncodeException;

import com.eh7n.f1telemetry.F12018TelemetryUDPServer;
import com.eh7n.f1telemetry.data.PacketCarTelemetryData;
import eu.kneipel.telem.ws.TelemMessage;
import eu.kneipel.telem.ws.TelemWsEndpoint;

public class DataGenerator extends Thread {

    private boolean shutdown = false;

    private Random random = new Random();

    @Override
    public void run() {

        try {
            F12018TelemetryUDPServer.create()
                    .bindTo("0.0.0.0")
                    .onPort(20777)
                    .consumeWith((p) -> {

                        int id = p.getHeader().getPlayerCarIndex();

                        int throttle = -1;
                        int brake = -1;

                        switch (p.getHeader().getPacketId()) {
                            case 0:
                                //return buildPacketMotionData(header);
                            case 1:
                                //return buildPacketSessionData(header);
                            case 2:
                                //return buildPacketLapData(header);
                            case 3:
                                //return buildPacketEventData(header);
                            case 4:
                                //return buildPacketParticipantsData(header);
                            case 5:
                                //return buildPacketCarSetupData(header);
                            case 6:
                                throttle = ((PacketCarTelemetryData) p).getCarTelemetryData()
                                        .get(id).getThrottle();
                                brake = ((PacketCarTelemetryData) p).getCarTelemetryData()
                                        .get(id).getBrake();

                                break;
                            case 7:
                                //return buildPacketCarStatusData(header);
                        }

                        TelemMessage message = new TelemMessage();

                            message.setThrottle(throttle);
                            message.setBrake(brake);

                        try {

                            TelemWsEndpoint.sendMessage(message);

                        } catch (EncodeException | IOException e) {
                            //Ignore oder behandeln?
                        }

                    }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

//    while(!this.shutdown) {
//      TelemMessage message = new TelemMessage()
//        .setAxisTemperatureBottomLeft(random.nextInt(100))
//        .setAxisTemperatureBottomRight(random.nextInt(100))
//        .setAxisTemperatureTopLeft(random.nextInt(100))
//        .setAxisTemperatureTopRight(random.nextInt(100))
//        .setBreakTemperatureBottomLeft(random.nextInt(100))
//        .setBreakTemperatureBottomRight(random.nextInt(100))
//        .setBreakTemperatureTopLeft(random.nextInt(100))
//        .setBreakTemperatureTopRight(random.nextInt(100))
//        .setInnerWheelTemperatureBottomLeft(random.nextInt(100))
//        .setInnerWheelTemperatureBottomRight(random.nextInt(100))
//        .setInnerWheelTemperatureTopLeft(random.nextInt(100))
//        .setInnerWheelTemperatureTopRight(random.nextInt(100))
//        .setOuterWheelTemperatureBottomLeft(random.nextInt(100))
//        .setOuterWheelTemperatureBottomRight(random.nextInt(100))
//        .setOuterWheelTemperatureTopLeft(random.nextInt(100))
//        .setOuterWheelTemperatureTopRight(random.nextInt(100))
//        .setSpeed(random.nextInt(350))
//        .setRpm(random.nextInt(14000))
//        .setThrottle(random.nextInt(100) * (random.nextBoolean() ? 1 : -1))
//        .setSteering(random.nextInt(100) * (random.nextBoolean() ? 1 : -1));
//
//
//      try {
//        TelemWsEndpoint.sendMessage(message);
//        Thread.sleep(10);
//      }
//      catch (InterruptedException e) {
//        Thread.currentThread().interrupt();
//      }
//      catch (EncodeException | IOException e) {
//        //Ignore oder behandeln?
//      }
    }


    public void markForShutdown() {
        this.shutdown = true;
    }
}
