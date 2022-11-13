import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


class dataQueue {
    private final Queue<Integer> queue = new LinkedList<>();
    private final LinkedList<Integer> primes = new LinkedList<>();
    private final Object FULL_STATE = new Object();
    private final Object EMPTY_STATE = new Object();
    private final int BUFFERING_SIZE, N;
    private int lastPosition;

    public dataQueue(int n, int bufferSize) {
        N = n;
        BUFFERING_SIZE = bufferSize;
    }

    public static boolean isPrime(int number){
        if(number < 2){
            return false;
        }
        for (int divisor = 2; divisor * divisor <= number; divisor++){
            if(number % divisor == 0){
                return false;
            }
        }
        return true;
    }
    public void generatePrimes(){
        for (int number = 0; number <= N; number++) {
            if(!isPrime(number))continue;
            primes.add(number);
        }
    }
    public int nextPrime(){
        if(lastPosition >= primes.size()){
            return -1;
        }
        int prime = primes.get(lastPosition);
        lastPosition++;
        return prime;
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
        return queue.size() >= BUFFERING_SIZE;
    }
    boolean isEmpty(){
        return queue.size() == 0;
    }
    public void waitingFullState() throws InterruptedException {
        synchronized (FULL_STATE) {
            FULL_STATE.wait();
        }
    }
    public void notifyFullState() {
        synchronized (FULL_STATE) {
            FULL_STATE.notifyAll();
        }
    }
    public void waitEmptyState() throws InterruptedException {
        synchronized (EMPTY_STATE) {
            EMPTY_STATE.wait();
        }
    }
    public void notifyEmptyState() {
        synchronized (EMPTY_STATE) {
            EMPTY_STATE.notify();
        }
    }
    public boolean exist(){
        if(!isEmpty() || lastPosition < primes.size()){
            return true;
        }
        else{
            return false;
        }
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
        data.notifyEmptyState();
    }
    public void produce(){
        while (isRunning) {
            int prime = data.nextPrime();
            if (prime == -1){
                stop();
                return;
            }
            while (data.isFull()) {
                try {
                    data.waitingFullState();
                } catch (InterruptedException e) {
                    break;
                }
            }
            if (!isRunning) {
                break;
            }
            else {
                data.add(prime);
                data.notifyEmptyState();
            }
        }
    }
}

class Consumer implements Runnable{
    dataQueue data;
    static PrintWriter output;
    private volatile boolean isRunning;

    Consumer(dataQueue d, String outputFileName){
        data = d;
        isRunning = true;
        try {
            output = new PrintWriter(outputFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run(){
        // print file
        consume();
    }

    @Deprecated

    public void stop() {
        isRunning = false;
        data.notifyEmptyState();
    }
    public void consume(){
        while (isRunning){
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(!data.exist()){
                stop();
                output.close();
                return;
            }
            if (data.isEmpty()) {
                try {
                    data.waitEmptyState();
                } catch (InterruptedException e) {
                    break;
                }
            }
            int prime = data.remove();
            output.println(prime);
            data.notifyFullState();
        }
    }
}

public class Main {
    static class FastScanner {
        BufferedReader in;
        StringTokenizer st;

        public FastScanner() {
            try {
                this.in = new BufferedReader(new InputStreamReader(System.in));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String nextToken() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(in.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(nextToken());
        }

        public long nextLong() {
            return Long.parseLong(nextToken());
        }

    }
    public static void main(String[] args){
        FastScanner input = new FastScanner();
        int N, BUFFERING_SIZE;
        String outputFileName;

        N = input.nextInt();
        BUFFERING_SIZE = input.nextInt();
        outputFileName = input.nextToken();

        dataQueue data = new dataQueue(N, BUFFERING_SIZE);
        data.generatePrimes();
        Producer producer = new Producer(data);
        Consumer consumer = new Consumer(data, outputFileName);
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
    }

}