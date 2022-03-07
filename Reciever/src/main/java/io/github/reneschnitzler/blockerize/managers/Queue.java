package io.github.reneschnitzler.blockerize.managers;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Queue<T extends Job> {


    public abstract T getFront();

    public abstract T getRear();

    public abstract void enqueue(T t);

    public abstract void dequeue(T t);

    public abstract boolean isEmpty();

}
