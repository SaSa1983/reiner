package eu.kneipel.telem;

import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import eu.kneipel.telem.data.DataGenerator;
import eu.kneipel.telem.ws.TelemWsEndpoint;

@WebListener
public class ContextListener implements ServletContextListener {

  private static  final Logger LOG = Logger.getLogger(ContextListener.class.getName());

  private DataGenerator dataGenerator;

  @Override public void contextInitialized(ServletContextEvent sce) {
    dataGenerator = new DataGenerator();
    dataGenerator.start();
  }

  @Override public void contextDestroyed(ServletContextEvent sce) {
    dataGenerator.markForShutdown();
    try {
      dataGenerator.join(5000);
    }
    catch (InterruptedException e) {
      LOG.warning("Could not shut down data generator");
      Thread.currentThread().interrupt();
    }
  }
}
