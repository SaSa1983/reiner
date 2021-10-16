package com.eh7n.f1telemetry;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.eh7n.f1telemetry.data.Packet;
import com.eh7n.f1telemetry.util.PacketDeserializer;

/**
 * The base class for the F1 2018 Telemetry app. Starts up a non-blocking I/O
 * UDP server to read packets from the F1 2018 video game and then hands those
 * packets off to a parallel thread for processing based on the lambda function
 * defined. Leverages a fluent API for initialization. 
 * 
 * Also exposes a main method for starting up a default server
 * 
 * @author eh7n
 *
 */
public class F12018TelemetryUDPServer {

	private static final String DEFAULT_BIND_ADDRESS = "0.0.0.0";
	private static final int DEFAULT_PORT = 20777;
	private static final int MAX_PACKET_SIZE = 1341;

	private String bindAddress;
	private int port;
	private Consumer<Packet> packetConsumer;

	private boolean shutdown = false;

	private F12018TelemetryUDPServer() {
		bindAddress = DEFAULT_BIND_ADDRESS;
		port = DEFAULT_PORT;
	}

	/**
	 * Create an instance of the UDP server
	 * 
	 * @return
	 */
	public static F12018TelemetryUDPServer create() {
		return new F12018TelemetryUDPServer();
	}

	/**
	 * Set the bind address
	 * 
	 * @param bindAddress
	 * @return the server instance
	 */
	public F12018TelemetryUDPServer bindTo(String bindAddress) {
		this.bindAddress = bindAddress;
		return this;
	}

	/**
	 * Set the bind port
	 * 
	 * @param port
	 * @return the server instance
	 */
	public F12018TelemetryUDPServer onPort(int port) {
		this.port = port;
		return this;
	}

	/**
	 * Set the consumer via a lambda function
	 * 
	 * @param consumer
	 * @return the server instance
	 */
	public F12018TelemetryUDPServer consumeWith(Consumer<Packet> consumer) {
		packetConsumer = consumer;
		return this;
	}

	/**
	 * Start the F1 2018 Telemetry UDP server
	 * 
	 * @throws IOException           if the server fails to start
	 * @throws IllegalStateException if you do not define how the packets should be
	 *                               consumed
	 */
	public void start() throws IOException {

		if (packetConsumer == null) {
			throw new IllegalStateException("You must define how the packets will be consumed.");
		}

		// Create an executor to process the Packets in a separate thread
		// To be honest, this is probably an over-optimization due to the use of NIO,
		// but it was done to provide a simple way of providing back pressure on the
		// incoming UDP packet handling to allow for long-running processing of the
		// Packet object, if required.
		ExecutorService executor = Executors.newSingleThreadExecutor();

		try (DatagramChannel channel = DatagramChannel.open()) {
			channel.socket().bind(new InetSocketAddress(bindAddress, port));
			ByteBuffer buf = ByteBuffer.allocate(MAX_PACKET_SIZE);
			buf.order(ByteOrder.LITTLE_ENDIAN);
			while (!shutdown) {

				channel.receive(buf);
				final Packet packet = PacketDeserializer.read(buf.array());
				executor.submit(() -> {
					packetConsumer.accept(packet);
				});
				buf.clear();
			}
		} finally {
			executor.shutdown();
		}
	}

	public void markForShutdown() {
		this.shutdown = true;
	}

	public boolean isMarkedForShutdown() {
		return this.shutdown;
	}
}
