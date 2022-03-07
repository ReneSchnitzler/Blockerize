package io.github.reneschnitzler.blockerize.managers;

import io.github.reneschnitzler.blockerize.exceptions.WrongOperationWorkerException;

public abstract class Worker {

    public abstract void start();

    public abstract void stop();

    public abstract Job getCurrentJob();

    protected abstract void run() throws WrongOperationWorkerException, InterruptedException;

}
