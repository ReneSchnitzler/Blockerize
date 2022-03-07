package io.github.reneschnitzler.blockerize.networking.packages.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import io.github.reneschnitzler.blockerize.networking.packages.request.TestRequest;
import io.github.reneschnitzler.blockerize.networking.packages.response.TestResponse;
import org.apache.http.annotation.Obsolete;

public class TestListener extends Listener {

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object o) {
        if(o instanceof TestRequest) {
            TestResponse testResponse = new TestResponse();

            connection.sendTCP(testResponse);
        }
    }
}
