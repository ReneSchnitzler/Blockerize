package io.github.reneschnitzler.blockerize.envoirment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Variables {

    @Getter
    @Setter
    public volatile int UDP_PORT = 27960;
    @Getter
    @Setter
    public volatile int TCP_PORT = 27960;
    @Getter
    public volatile int MAX_PORT = 65535;
    @Getter
    public volatile int MIN_PORT = 0;
    @Getter
    @Setter
    public String LogFileName = "";



    public void generateLogFileName() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        this.setLogFileName(dateTimeFormatter.format(now));
    }

}
