import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("1- SJF");
        System.out.println("2- Round Robin");
        System.out.println("3- Priority Scheduling");
        System.out.println("4- AG Scheduling");
        int type = input.nextInt();
        if(type == 1){
            int processesNumber;
            System.out.print("Enter the number of processes : ");
            processesNumber = input.nextInt();
            ArrayList<process> processesList = new ArrayList<>(processesNumber);
            for (int i = 0; i < processesNumber; i++) {
                String processName;
                int burstTime, arrivalTime, priority;
                System.out.println("Enter the data for each process");
                System.out.print("Enter the name : ");
                processName = input.next();
                System.out.print("Enter the arrival time : ");
                arrivalTime = input.nextInt();
                System.out.print("Enter the burst time : ");
                burstTime = input.nextInt();
                process process = new process(processName, arrivalTime, burstTime);
                processesList.add(process);

            }
            preemptiveSFJContext preemptiveSFJContext = new preemptiveSFJContext(processesList);
            preemptiveSFJContext.preemptiveSFJ();
        }
        else if(type == 2){

        }
        else if (type == 3){

        }
        else{

        }
//        for(int i=0 ; i<processesList.size() ; i++)
//        {
//            System.out.println(processesList.get(i).processName + "  " + processesList.get(i).arrivalTime + "  " + processesList.get(i).burstTime + "  " + processesList.get(i).priority);
//        }


    }


}
