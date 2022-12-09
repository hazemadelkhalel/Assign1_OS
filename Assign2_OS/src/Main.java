import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("1- SJF");
        System.out.println("2- Round Robin");
        System.out.println("3- Priority Scheduling");
        System.out.println("4- AG Scheduling");
        int type = input.nextInt();
        System.out.print("Enter the number of processes : ");
        int processesNumber;
        processesNumber = input.nextInt();
        System.out.println("Enter the data for each process");
        ArrayList<process> processesList = new ArrayList<>();
        if(type == 1){
            for (int i = 0; i < processesNumber; i++) {
                String processName;
                int burstTime, arrivalTime;
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
            for (int i = 0; i < processesNumber; i++) {
                String processName;
                int burstTime, arrivalTime, priority;
                System.out.print("Enter the name : ");
                processName = input.next();
                System.out.print("Enter the arrival time : ");
                arrivalTime = input.nextInt();
                System.out.print("Enter the burst time : ");
                burstTime = input.nextInt();
                System.out.print("Enter the priority time : ");
                priority = input.nextInt();
                process process = new process(processName, arrivalTime, burstTime, priority);
                processesList.add(process);
            }
            priorityScheduling priorityScheduling = new priorityScheduling(processesList);
            priorityScheduling.priorityScheduling();
        }
        else{

        }
//        for(int i=0 ; i<processesList.size() ; i++)
//        {
//            System.out.println(processesList.get(i).processName + "  " + processesList.get(i).arrivalTime + "  " + processesList.get(i).burstTime + "  " + processesList.get(i).priority);
//        }


    }
//3
//5
//p1
//0
//8
//3
//p2
//1
//1
//1
//p3
//2
//3
//2
//p4
//3
//2
//3
//p5
//4
//6
//4

}
