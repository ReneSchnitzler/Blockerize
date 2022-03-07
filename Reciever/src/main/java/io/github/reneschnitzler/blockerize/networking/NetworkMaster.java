package io.github.reneschnitzler.blockerize.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import io.github.reneschnitzler.blockerize.Reciever;
import io.github.reneschnitzler.blockerize.logging.Logger;
import lombok.Getter;

import java.io.IOException;

public class NetworkMaster {

    @Getter
    public Server server = new Server();


    public void startServer() {
        server.start();

        try {
            server.bind(Reciever.getVariables().TCP_PORT);
        }catch (IOException exception) {
            new Logger("Port Binding wasnt succesfull.",true).sendFatal();
            System.exit(-1);
        }
    }

    public void stopServer() {
        server.stop();
    }


}
