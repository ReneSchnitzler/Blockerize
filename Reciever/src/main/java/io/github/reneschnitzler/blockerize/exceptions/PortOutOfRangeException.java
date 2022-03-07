package io.github.reneschnitzler.blockerize.exceptions;

import io.github.reneschnitzler.blockerize.security.PortCheckWorker;

public class PortOutOfRangeException extends Exception{

    public PortOutOfRangeException(String message) {
        super(message);
    }

}
