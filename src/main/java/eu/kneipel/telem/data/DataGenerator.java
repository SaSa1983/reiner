package eu.kneipel.telem.data;

import java.io.IOException;
import java.util.Random;
import java.util.function.Consumer;
import javax.websocket.EncodeException;

import com.eh7n.f1telemetry.F12018TelemetryUDPServer;
import com.eh7n.f1telemetry.data.Packet;
import com.eh7n.f1telemetry.data.PacketCarTelemetryData;
import eu.kneipel.telem.ws.TelemMessage;
import eu.kneipel.telem.ws.TelemWsEndpoint;

public class DataGenerator extends Thread {

    private boolean shutdown = false;
    private F12018TelemetryUDPServer server = null;
    private final PacketConsumer packetConsumer = new PacketConsumer();

    public void markForShutdown(){
        this.shutdown=true;
    }

    @Override
    public void run() {
        while (!shutdown) {
            if (server == null) {
                server = F12018TelemetryUDPServer.create()
                        .bindTo("0.0.0.0")
                        .onPort(20777);
                server.consumeWith(packetConsumer);
                try {
                    server.start();
                }
                catch(IOException e) {
                    e.printStackTrace();
                    this.markForShutdown();
                    server.markForShutdown();
                }
            }
        }
        if (server != null && !server.isMarkedForShutdown()) {
            server.markForShutdown();
        }

    }

    private  final class PacketConsumer implements Consumer<Packet> {

        public PacketConsumer(){}

        @Override
        public void accept(Packet p) {

            int id = p.getHeader().getPlayerCarIndex();

            int throttle = -1;
            int brake = -1;
            int steering = -101;

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
                    steering = ((PacketCarTelemetryData) p).getCarTelemetryData()
                            .get(id).getSteer();

                    break;
                case 7:
                    //return buildPacketCarStatusData(header);
            }

            TelemMessage message = new TelemMessage();

            if (throttle >= 0)
                message.setThrottle(throttle);

            if (brake >= 0)
                message.setBrake(brake);

            if (steering >= -100)
                message.setBrake(steering);

            try {

                TelemWsEndpoint.sendMessage(message);

            } catch (EncodeException | IOException e) {
                //Ignore oder behandeln?
            }

        }
    }
}


