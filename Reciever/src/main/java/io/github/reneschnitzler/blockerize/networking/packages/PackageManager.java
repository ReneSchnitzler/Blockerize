package io.github.reneschnitzler.blockerize.networking.packages;

import com.esotericsoftware.kryo.Kryo;
import io.github.reneschnitzler.blockerize.Reciever;
import io.github.reneschnitzler.blockerize.networking.packages.listener.TestListener;
import io.github.reneschnitzler.blockerize.networking.packages.request.TestRequest;
import io.github.reneschnitzler.blockerize.networking.packages.response.TestResponse;
import jdk.incubator.vector.VectorOperators;

public class PackageManager {

    public void registerPackages() {
        Kryo kryo = Reciever.getNetworkMaster().getServer().getKryo();
        kryo.register(TestRequest.class);
        kryo.register(TestResponse.class);
        Reciever.getNetworkMaster().server.addListener(new TestListener());
    }


}
