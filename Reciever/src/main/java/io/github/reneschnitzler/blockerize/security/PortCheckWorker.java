package io.github.reneschnitzler.blockerize.security;

import io.github.reneschnitzler.blockerize.Reciever;
import io.github.reneschnitzler.blockerize.exceptions.PortOutOfRangeException;
import io.github.reneschnitzler.blockerize.logging.Logger;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class PortCheckWorker {

    int port = 0;

    public PortCheckWorker(int port) {
        this.port = port;
    }

    public boolean isAvailable() throws PortOutOfRangeException, IOException {
        if(this.port < Reciever.getVariables().getMIN_PORT() || port > Reciever.getVariables().getMAX_PORT())
        {
            new Logger("PortOutOfRangeException: Thrown by PortCheckWorker. Bitte gebe einen gültigen Port an",true).sendFatal();
            System.exit(0);
            throw new PortOutOfRangeException("The Port you put in isn't in Range.");
        }

        ServerSocket serverSocket = null;
        DatagramSocket datagramSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            datagramSocket = new DatagramSocket(port);
            datagramSocket.setReuseAddress(true);
            return true;
        }catch (IOException exception) {
        }finally {
            if(datagramSocket != null) {
                datagramSocket.close();
            }

            if(serverSocket != null) {
                serverSocket.close();
            }
        }
        return false;
    }

}
