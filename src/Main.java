/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author DELL
 */
import java.util.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.Duration;
import java.time.Instant;



class dataQueue {
    private final int []queue;
    private final Object FULL_STATE = new Object();
    private final Object EMPTY_STATE = new Object();
    public final int BUFFERING_SIZE, N;
    public int lastPosition, numOfPrimes, maxPrime = -1;
    private int curIndex, prevIndex, counter;

    public dataQueue(int n, int bufferSize) {
        N = n;
        BUFFERING_SIZE = bufferSize;
        queue = new int[BUFFERING_SIZE];
        curIndex = -1;
        prevIndex = 0;
        counter = 0;
    }

    public int getMaxPrime()
    {
        return maxPrime;
    }
    public int getNumOfPrimes()
    {
        return numOfPrimes;
    }
    public int getLastPosition()
    {
        return lastPosition;
    }
    public void add(int value){
        synchronized (queue){
            curIndex++;
            curIndex %= BUFFERING_SIZE;
            counter++;
            queue[curIndex] = value;
        }
    }
    public int remove() {
        synchronized (queue) {
            int prime = queue[prevIndex];
            prevIndex++;
            prevIndex %= BUFFERING_SIZE;
            counter--;
            return prime;
        }
    }
    boolean isFull(){
        return counter == BUFFERING_SIZE;
    }
    boolean isEmpty(){
        return counter == 0;
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
}

class Producer implements Runnable{
    private dataQueue data;
    Producer(dataQueue d){
        data = d;
    }
    @Override
    public void run(){
        // call produce method
        int primes = 0;
        for (int number = 2; number <= data.N; number++) {
            if(!MillerRabin(number))continue;
            produce(number);
            primes++;
            data.maxPrime = number;
        }
        data.numOfPrimes = primes;
        produce(-1);
    }
    public void produce(int prime){
        while (data.isFull()) {
            try {
                data.waitingFullState();
            } catch (InterruptedException e) {
                break;
            }
        }
        data.add(prime);
        data.notifyEmptyState();
    }
    long binpower(long base, long e, long mod) {
        long result = 1;
        base %= mod;
        while (e > 0) {
            if (e % 2 == 1)
                result = (long)result * base % mod;
            base = (long)base * base % mod;
            e >>= 1;
        }
        return result;
    }

    boolean check_composite(long n, long a, long d, long s) {
        long x = binpower(a, d, n);
        if (x == 1 || x == n - 1)
            return false;
        for (int r = 1; r < s; r++) {
            x = (long)x * x % n;
            if (x == n - 1)
                return false;
        }
        return true;
    };
    boolean MillerRabin(long n) { // returns true if n is prime, else returns false.
        if (n < 2)
            return false;

        int r = 0;
        long d = n - 1;
        while ((d & 1) == 0) {
            d >>= 1;
            r++;
        }

        for (long a : new long []{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}) {
            if (n == a)
                return true;
            if (check_composite(n, a, d, r))
                return false;
        }
        return true;
    }
}

class Consumer implements Runnable{
    dataQueue data;
    FileWriter out;
    static PrintWriter output;
    private volatile boolean isRunning;
    private int counter;

    Consumer(dataQueue d, String outputFileName)throws IOException{
        data = d;
        isRunning = true;
        counter = 0;
        try {
            out = new FileWriter(outputFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(){
        try {
            // print file
            consume();
        } catch (IOException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        isRunning = false;
        data.notifyEmptyState();
    }
    public void consume()throws IOException{
        while (isRunning){

            if (data.isEmpty()) {
                try {
                    data.waitEmptyState();
                } catch (InterruptedException e) {
                    break;
                }
            }

            int prime = data.remove();
            if(prime == -1){
                stop();
                out.close();
                return;
            }
            if(counter > 0)
            {
                out.write(", ");
            }
            data.notifyFullState();
            try {
                out.write("\""+Integer.toString(prime)+"\"");
                counter++;
            } catch (IOException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setBackground(new java.awt.Color(153, 204, 255));
        jButton1.setText("Start Producer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("N");

        jLabel2.setText("Buffer Size");

        jLabel3.setText("Output File");

        jLabel4.setText("The Largest prime number");

        jLabel5.setText("#of elements (prime number) generated");

        jLabel6.setText("Time elapsed since the start of processing");

        jLabel7.setText("#####");

        jLabel8.setText("#####");

        jLabel9.setText("#####");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(211, 211, 211))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addComponent(jButton1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(48, 48, 48)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                                        .addComponent(jTextField3))
                                                .addGap(49, 49, 49)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3))))
                                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addGap(26, 26, 26)
                                .addComponent(jButton1)
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel7))
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel9))
                                .addContainerGap(134, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        long start1 = System.currentTimeMillis();
        int N, BUFFERING_SIZE;
        String outputFileName;
        N = Integer.parseInt(jTextField2.getText());
        BUFFERING_SIZE = Integer.parseInt(jTextField1.getText());
        outputFileName = jTextField3.getText();

        dataQueue data = new dataQueue(N, BUFFERING_SIZE);

        Producer producer = new Producer(data);
        Consumer consumer = null;
        try {
            consumer = new Consumer(data, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        jLabel7.setText(Integer.toString(data.getMaxPrime()));
        jLabel8.setText(Integer.toString(data.getNumOfPrimes()));
        long end1 = System.currentTimeMillis();
        jLabel9.setText(Long.toString(end1-start1)+ " ms");


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration
}
