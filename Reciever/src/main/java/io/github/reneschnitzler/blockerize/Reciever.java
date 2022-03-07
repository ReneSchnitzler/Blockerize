package io.github.reneschnitzler.blockerize;

import io.github.reneschnitzler.blockerize.envoirment.Variables;
import io.github.reneschnitzler.blockerize.exceptions.PortIsntAvailableException;
import io.github.reneschnitzler.blockerize.exceptions.PortOutOfRangeException;
import io.github.reneschnitzler.blockerize.logging.LoggingUtils;
import io.github.reneschnitzler.blockerize.managers.Operation;
import io.github.reneschnitzler.blockerize.managers.queues.DatabaseQueue;
import io.github.reneschnitzler.blockerize.managers.queues.NetworkingQueue;
import io.github.reneschnitzler.blockerize.managers.workers.DatabaseWorker;
import io.github.reneschnitzler.blockerize.managers.workers.NetworkingWorker;
import io.github.reneschnitzler.blockerize.networking.NetworkMaster;
import io.github.reneschnitzler.blockerize.security.PortCheckWorker;
import lombok.Getter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Reciever {

    @Getter
    public static Variables variables = new Variables();
    @Getter
    public final static Logger logger = Logger.getRootLogger();
    @Getter
    public final static LoggingUtils loggingUtils = new LoggingUtils();
    @Getter
    public final static DatabaseQueue databaseQueue = new DatabaseQueue();
    @Getter
    public final static NetworkingQueue networkingQueue = new NetworkingQueue();
    @Getter
    public final static DatabaseWorker databaseWorker = new DatabaseWorker();
    @Getter
    public final static NetworkingWorker networkingWorker = new NetworkingWorker();
    @Getter
    public final static NetworkMaster networkMaster = new NetworkMaster();


    public static void main(String[] args) throws InterruptedException, IOException, PortOutOfRangeException, PortIsntAvailableException {
        if(new PortCheckWorker(variables.getTCP_PORT()).isAvailable() && new PortCheckWorker(variables.getTCP_PORT()).isAvailable()) {
            variables.generateLogFileName();
            new io.github.reneschnitzler.blockerize.logging.Logger("Generating new Log File Name", false).sendInfo();
            try {
                loggingUtils.createLogDirectory();
                loggingUtils.createLogFile();
            }catch (IOException exception) {
                new io.github.reneschnitzler.blockerize.logging.Logger("LoggingUtils Error! Check Folder \n " + exception.getMessage(), true).sendFatal();
            }
            try {
                runWorkers(Operation.DATABASE);
                runWorkers(Operation.NETWORKING);
            }catch (InterruptedException exception) {
                new io.github.reneschnitzler.blockerize.logging.Logger("The Workers were Interrupted. Restarting Workers ( It can cause lost of old Jobs )", false).sendInfo();
                if(databaseWorker.isRunning) {
                    databaseWorker.stop();
                    databaseWorker.wait(100);
                    new io.github.reneschnitzler.blockerize.logging.Logger("Restarting", false).sendInfo();
                    databaseWorker.start();
                }
                new io.github.reneschnitzler.blockerize.logging.Logger("Starting Worker", false).sendInfo();
                databaseWorker.start();
            }
            networkMaster.startServer();
        }
        new io.github.reneschnitzler.blockerize.logging.Logger("The Ports arent available to Expose the Reciever. Please check the availabitlity and rerun the Application. Shutting down...",true).sendFatal();
        throw new PortIsntAvailableException("Port isnt available for exposing Application!");
    }
    public static void runWorkers(Operation operation) throws InterruptedException {
        switch (operation){
            case DATABASE:
                if(!databaseQueue.isEmpty()) {
                    for(int i = 0;i < databaseQueue.size();i++) {
                        if (!databaseWorker.isRunning) {
                            databaseWorker.start();
                        }
                        databaseWorker.setJob(databaseQueue.getFront());
                    }
                }else {
                    synchronized (databaseQueue) {
                        while(databaseQueue.isEmpty()) {
                            databaseQueue.wait();
                        }
                        for(int i = 0;i < databaseQueue.size();i++) {
                            if (!databaseWorker.isRunning) {
                                databaseWorker.start();
                            }
                            databaseWorker.setJob(databaseQueue.getFront());
                        }
                    }
                    }
            case NETWORKING:
                if(!networkingQueue.isEmpty()) {
                    for(int i = 0;i < networkingQueue.size();i++) {
                        if (!networkingWorker.isRunning) {
                            networkingWorker.start();
                        }
                        networkingWorker.setJob(networkingQueue.getFront());
                    }
                }else {
                    synchronized (networkingQueue) {
                        while(networkingQueue.isEmpty()) {
                            databaseQueue.wait();
                        }
                        for(int i = 0;i < networkingQueue.size();i++) {
                            if (!networkingWorker.isRunning) {
                                networkingWorker.start();
                            }
                            networkingWorker.setJob(networkingQueue.getFront());
                        }
                    }
                }
        }
    }

}
