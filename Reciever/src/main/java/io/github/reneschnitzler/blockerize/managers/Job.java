package io.github.reneschnitzler.blockerize.managers;

import lombok.Getter;

public class Job {

    @Getter
    Runnable runnable;
    @Getter
    Operation operations;

    public Job(Operation operations, Runnable runnable) {
        this.operations = operations;
        this.runnable = runnable;
    }



}
