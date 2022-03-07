package io.github.reneschnitzler.blockerize.managers.queues;

import io.github.reneschnitzler.blockerize.logging.Logger;
import io.github.reneschnitzler.blockerize.managers.Job;
import io.github.reneschnitzler.blockerize.managers.Queue;

import java.util.ArrayList;


public class DatabaseQueue<T extends Job> extends Queue<T> {

    int front = -1, rear = -1;

    private ArrayList<T> jobs = new ArrayList<>();


    @Override
    public T getFront() {
        if(front == -1)
        {
            return null;
        }
        return jobs.get(front);
    }

    @Override
    public T getRear() {
        if (rear == -1)
        {
            return null;
        }
        return jobs.get(rear);
    }

    @Override
    public void enqueue(T t) {
        if(this.isEmpty())
        {
            front = 0;
            rear = 0;
            jobs.add(t);
        }else
        {
            front++;

            if(jobs.size() > front)
            {
                jobs.set(front,t);
            }else
            {
                jobs.add(t);
            }
        }
    }

    @Override
    public void dequeue(T t) {
        if(this.isEmpty()) {
            new Logger("The Queue is already empty",true).sendDebug();
        }else if(front == rear)
        {
            front = rear = 1;
        }else
        {
            rear++;
        }
    }

    @Override
    public boolean isEmpty() {
        if(front == -1 && rear == -1)
        {
            return true;
        }
        return false;
    }

    public int size() {
        return this.jobs.size();
    }

}