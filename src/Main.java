import javax.print.attribute.standard.PrinterURI;


class Printer{
    Printer(){}
     void print(int id){
        for (int i = 1; i <= 10; i++){
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Printer " + id + " #" + i);
        }
    }
}
class myThread1 extends Thread{
    Printer printer;
    int id;
    myThread1(Printer p, int i){
        printer = p;
        id = i;
    }
    @Override
    public void run(){
        synchronized (printer){
            printer.print(1);
        }
    }
}


class myThread2 extends Thread{
    Printer printer;
    int id;
    myThread2(Printer p, int i){
        printer = p;
        id = i;
    }
    @Override
    public void run(){
        synchronized (printer){
            printer.print(2);
        }
    }
}

class myThread3 extends Thread{
    Printer printer;
    int id;
    
    
    myThread3(Printer p, int i){
        printer = p;
        id = i;
    }
    @Override
    public void run(){
        printer.print(id);
    }
}
public class Main {

    public static void main(String[] args){
        System.out.println("====== Hello app ======");
        Printer printer = new Printer();
        myThread1 mythread = new myThread1(printer, 1);
        mythread.start();
        myThread2 t = new myThread2(printer, 2);
        t.start();
        myThread3 s = new myThread3(printer, 3);
        s.start();
        System.out.println("====== END app ======");

    }
}