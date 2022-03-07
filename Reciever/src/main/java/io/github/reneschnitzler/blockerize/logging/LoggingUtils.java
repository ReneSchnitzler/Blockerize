package io.github.reneschnitzler.blockerize.logging;

import io.github.reneschnitzler.blockerize.Reciever;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class LoggingUtils {


    public void createLogDirectory() throws IOException {
        final File file = new File(System.getProperty("user.dir") + "/logs");
        if(!file.exists()) {
            new Logger("Directory wasnt found, Creating...",false).sendInfo();
            FileUtils.forceMkdir(file);
        }else {
            new Logger("Directory already existing. Skipping...",false).sendInfo();
        }
    }
    public void createLogFile() throws IOException {
        final File file = new File(System.getProperty("user.dir") + "/logs/" + Reciever.getVariables().getLogFileName() + ".log");
        if(!file.exists()) {
            new Logger("Log File wasnt found, Creating...",false).sendInfo();
            FileUtils.forceMkdir(file);
        }else {
            new Logger("Log File already existing, Skipping...",false).sendInfo();
        }
    }

}