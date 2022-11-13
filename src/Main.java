import java.util.LinkedList;
import java.util.Scanner;

import java.util.Queue;


class dataQueue {
    private final Queue<Integer> queue = new LinkedList<>();
    private final LinkedList<Integer> primes = new LinkedList<>();
    private final Object fullQueue = new Object();
    private final Object emptyQueue = new Object();
    private boolean isFinished = false;
    private final int buffering_size;
    private int lastPosition;
    private final int N;

    public dataQueue(int b, int n) {
        buffering_size = b;
        N = n;
    }

    public static boolean isprime(int value){
        if(value < 2){
            return false;
        }
        for (int i = 2; i * i <= value; i++){
            if(value % i == 0){
                return false;
            }
        }
        return true;
    }
    public void generate(){
        for (int number = 0; number <= N; number++) {
            if(!isprime(number))continue;
            primes.add(number);
        }
    }
    public int nextPrime(){
        if(lastPosition == primes.size()){
            return -1;
        }
        return primes.get(lastPosition++);
    }
    public void add(int value){
        synchronized (queue){
            queue.add(value);
        }
    }
    public int remove() {
        synchronized (queue) {
            return queue.poll();
        }
    }
    boolean isFull(){
        return queue.size() == buffering_size;
    }
    boolean isEmpty(){
        return queue.size() == 0;
    }
    public void waitOnFull() throws InterruptedException {
        synchronized (fullQueue) {
            fullQueue.wait();
        }
    }
    public void notifyAllForFull() {
        synchronized (fullQueue) {
            fullQueue.notifyAll();
        }
    }
    public void waitOnEmpty() throws InterruptedException {
        synchronized (emptyQueue) {
            emptyQueue.wait();
        }
    }
    public void notifyAllForEmpty() {
        synchronized (emptyQueue) {
            emptyQueue.notify();
        }
    }
    public void setFinished(){
        isFinished = true;
    }
    public boolean getFinished(){
        return isFinished;
    }
    public boolean exist(){
        return lastPosition < primes.size() || !isEmpty();
    }
}

class Producer implements Runnable{
    private dataQueue data;
    private volatile boolean isRunning;
    Producer(dataQueue d){
        data = d;
        isRunning = true;
    }
    @Override
    public void run(){
        // call produce method
        produce();
    }
    @Deprecated
    public void stop() {
        isRunning = false;
        data.notifyAllForEmpty();
    }
    public void produce(){
        while (isRunning) {
            int prime = data.nextPrime();
            if (prime == -1){
                data.setFinished();
                stop();
                break;
            }
            while (data.isFull()) {
                try {
                    data.waitOnFull();
                } catch (InterruptedException e) {
                    break;
                }
            }
            if (!isRunning) {
                break;
            }
            data.add(prime);
            data.notifyAllForEmpty();
        }
    }
}

class Consumer implements Runnable{
    dataQueue data;
    private volatile boolean isRunning;

    Consumer(dataQueue d){
        data = d;
        isRunning = true;
    }
    @Override
    public void run(){
        // print file
        consume();
    }

    @Deprecated

    public void stop() {
        isRunning = false;
        data.notifyAllForEmpty();
    }
    public void consume(){
        while (isRunning){
            if(!data.exist()){
                stop();
                break;
            }
            if (data.isEmpty()) {
                try {
                    data.waitOnEmpty();
                } catch (InterruptedException e) {
                    break;
                }
            }
            int prime = data.remove();
            if (!isRunning) {
                break;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(prime);
            data.notifyAllForFull();
        }
    }
}
public class Main {


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n, bf;
        //mariam
        n = input.nextInt();
        bf = input.nextInt();
        dataQueue data = new dataQueue(bf, n);
        data.generate();
        Producer producer = new Producer(data);
        Consumer consumer = new Consumer(data);
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();
        try {
            
            producerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            consumerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        producer.stop();
        consumer.stop();
        input.close();
    }

}