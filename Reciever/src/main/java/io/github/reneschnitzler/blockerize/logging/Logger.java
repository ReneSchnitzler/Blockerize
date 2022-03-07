package io.github.reneschnitzler.blockerize.logging;

import io.github.reneschnitzler.blockerize.Reciever;
import lombok.SneakyThrows;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.SimpleLayout;

public class Logger{

    String message;
    boolean writeLogFile;

    public Logger(String message, boolean writeLogFile) {
        this.message = message;
        this.writeLogFile = writeLogFile;
    }


    public void sendDebug() {
        Reciever.getLogger().debug(this.message);
        if(writeLogFile) {
                writeLogFile(Level.DEBUG);
        }
    }

    public void sendInfo() {
        Reciever.getLogger().debug(this.message);
        if(writeLogFile) {
            writeLogFile(Level.INFO);
        }
    }

    public void sendWarning() {
        Reciever.getLogger().debug(this.message);
        if(writeLogFile) {
            writeLogFile(Level.WARN);
        }
    }

    public void sendError() {
        Reciever.getLogger().debug(this.message);
        if(writeLogFile) {
            writeLogFile(Level.ERROR);
        }
    }

    public void sendFatal() {
        Reciever.getLogger().debug(this.message);
        if(writeLogFile) {
            writeLogFile(Level.FATAL);
        }
    }

    @SneakyThrows
    private void writeLogFile(Level level) {
        SimpleLayout simpleLayout = new SimpleLayout();
        ConsoleAppender consoleAppender = new ConsoleAppender(simpleLayout);
        Reciever.getLogger().addAppender(consoleAppender);
        FileAppender fileAppender = new FileAppender(simpleLayout, System.getProperty("user.dir") + "/logs/" + Reciever.getVariables().getLogFileName() + ".log", true);
        Reciever.getLogger().addAppender(fileAppender);
        Reciever.getLogger().setLevel(level);
    }



}
