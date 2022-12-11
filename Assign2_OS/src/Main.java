import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
//        System.out.println("1- SJF");
//        System.out.println("2- Round Robin");
//        System.out.println("3- Priority Scheduling");
//        System.out.println("4- AG Scheduling");
        int type = input.nextInt();
//        System.out.print("Enter the number of processes : ");
        int processesNumber;
        processesNumber = input.nextInt();
//        System.out.println("Enter the data for each process");
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

            System.out.println("Enter Quantum :");
            int quantum = input.nextInt();

            ////////////
            for (int i = 0; i < processesNumber; i++) {
                String processName;
                int burstTime, arrivalTime;
//                System.out.print("Enter the name : ");
                processName = input.next();
//                System.out.print("Enter the arrival time : ");
                arrivalTime = input.nextInt();
//                System.out.print("Enter the burst time : ");
                burstTime = input.nextInt();
                process process = new process(processName, arrivalTime, burstTime);
                processesList.add(process);

            }
            RoundRobin roundRobin = new RoundRobin(processesList);
            roundRobin.roundRobin(quantum);

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
            for (int i = 0; i < processesNumber; i++) {
                String processName;
                int burstTime, arrivalTime, priority, quantum;
//                System.out.print("Enter the name : ");
                processName = input.next();
//                System.out.print("Enter the arrival time : ");
                arrivalTime = input.nextInt();
//                System.out.print("Enter the burst time : ");
                burstTime = input.nextInt();
//                System.out.print("Enter the priority time : ");
                priority = input.nextInt();
//                System.out.print("Enter the Quantum time : ");
                quantum = input.nextInt();
                process process = new process(processName, arrivalTime, burstTime, priority, quantum);
                processesList.add(process);
            }
            AGScheduling agScheduling = new AGScheduling(processesList);
            agScheduling.AGSchedulingProcessing();
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












/*
RR
processes: 5
quantum
3
p1
0
4
p2
1
8
p3
3
2
p4
10
6
p5
12
5
*/

}



//4
//4
//p1
//0
//17
//4
//7
//p2
//2
//6
//7
//9
//p3
//5
//11
//3
//4
//p4
//15
//4
//6
//6
