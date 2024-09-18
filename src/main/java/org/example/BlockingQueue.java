package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private final int capasity;
    private final Queue<T> queue;
    private final Object lock = new Object();

    public BlockingQueue(int capasity) {
        this.capasity = capasity;
        this.queue = new LinkedList<>();
    }

    public void enqueue(T item) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() == capasity) {
                lock.wait();
            }
            queue.add(item);
            lock.notifyAll();

        }
    }

    public T dequeue() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();
            }
            T item = queue.poll();
            lock.notifyAll();
            return item;
        }
    }

    public int size() {
        synchronized (lock) {
            return queue.size();
        }
    }
}
