package io.github.reneschnitzler.blockerize.managers.workers;

import io.github.reneschnitzler.blockerize.exceptions.WrongOperationWorkerException;
import io.github.reneschnitzler.blockerize.logging.Logger;
import io.github.reneschnitzler.blockerize.managers.Job;
import io.github.reneschnitzler.blockerize.managers.Operation;
import io.github.reneschnitzler.blockerize.managers.Worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingWorker extends Worker {

    ExecutorService executorService;

    public boolean isRunning = false;

    Job job = null;


    @Override
    public void start() {
        if(executorService != null) {
            new Logger("The NetworkingWorker is already started.",true).sendDebug();
        }
        executorService = Executors.newFixedThreadPool(2);
        new Logger("NetworkingWorker started.",true).sendDebug();
        isRunning = true;
    }

    @Override
    public void stop() {
        if(executorService.isShutdown()) {
            new Logger("NetworkingWorker already offline",true).sendDebug();
        }else {
            executorService.shutdownNow();
            new Logger("NetworkingWorker is shutting down",true).sendDebug();
            isRunning = false;
        }
    }

    @Override
    public Job getCurrentJob() {
        return job;
    }

    @Override
    protected void run() throws WrongOperationWorkerException, InterruptedException {
        if(this.job.getOperations() != Operation.NETWORKING) {
            new Logger("There was an Error in the NetworkingWorker, Operation not Applicable for Worker",true).sendFatal();
            System.exit(-1);
            isRunning = false;
            throw new WrongOperationWorkerException("Operation not Applicable for NetworkingWorker");
        }
        executorService.submit(job.getRunnable());
        executorService.wait(100);
    }

    public void setJob(Job job) {
        if(job != null) {
            new Logger("There is already a Job in Process at the Moment please hold up",false).sendInfo();
            return;
        }
        this.job = job;
    }
}

