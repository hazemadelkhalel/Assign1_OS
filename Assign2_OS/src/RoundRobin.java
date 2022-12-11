import java.util.*;

public class RoundRobin {

    ArrayList<process> processesList;
    ArrayList<Integer> arrivalTime;
    //    PriorityQueue<process> sortedProcess;
    HashMap<Integer, ArrayList<process>> compressedProcess;

    ArrayList<String>Sequence;


    RoundRobin(ArrayList<process> processesList) {
        this.processesList = processesList;
        arrivalTime = new ArrayList<>();
        Sequence = new ArrayList<>();
//        sortedProcess = new PriorityQueue<process>(processesList.size(), new ProcessComparator());
        compressedProcess = new HashMap<>();
        //fill the map
        for(int i = 0; i < processesList.size(); i++){
            if(!compressedProcess.containsKey(processesList.get(i).arrivalTime)){
                compressedProcess.put(processesList.get(i).arrivalTime, new ArrayList<>());
            }
            compressedProcess.get(processesList.get(i).arrivalTime).add(processesList.get(i));
        }
        //fill the arrival time array list
        for(Map.Entry<Integer, ArrayList<process>> entry: compressedProcess.entrySet()){
            arrivalTime.add(entry.getKey());
        }
        Collections.sort(arrivalTime);
    }


    public void roundRobin(int quantum, int context)
    {
        Queue<process>WaitingProcesses = new LinkedList<process>();
        Queue<process>ArrivedProcesses = new LinkedList<process>(); //who came
//        int current_time=0;
        int currentTime = arrivalTime.get(0);
        ArrayList<process> processList = compressedProcess.get(currentTime);
        int contextSwitching = 0;
        boolean waiting = false;
        int i=1;
        int lastTime = 0;
        System.out.println("Order of Execution:");
        while( ! processList.isEmpty() || !WaitingProcesses.isEmpty()) //veryyyy big loop
        {
            for(int j=0 ; j<processList.size() ; j++)
            {
                //check if its execution time is greater than quantum or not
                boolean ok = false;
                if( processList.get(j).burstTime != 0 )
                {
                    ok = true;
                    if(processList.get(j).burstTime <= quantum) {


                        currentTime += processList.get(j).burstTime;
                        currentTime += context; //for the contexttt
                        processList.get(j).burstTime = 0;

                        processList.get(j).completeTime = currentTime;
                        processList.get(j).waitingTime = processList.get(j).completeTime - processList.get(j).arrivalTime - processList.get(j).originalExecution;
                        processList.get(j).turnAroundTime = processList.get(j).completeTime - processList.get(j).arrivalTime;
                        System.out.println("Process " + processList.get(j).processName + ": " + lastTime + " to " + currentTime);
                        System.out.println("Context Switch from " + currentTime + " to " + (currentTime + context));
                        for(int k = 0; k < processesList.size();k++){
                            if(processesList.get(k).processName.equals(processList.get(j).processName)){
                                processesList.set(k, processList.get(j));
                                break;
                            }
                        }

                    }
                    else{
                        currentTime += quantum;
                        processList.get(j).burstTime -= quantum;
                        WaitingProcesses.add(processList.get(j));
                        System.out.println("Process " + processList.get(j).processName + ": " + lastTime + " to " + currentTime);
                        System.out.println("Context Switch from " + currentTime + " to " + (currentTime + context));

                        currentTime += context; //for the contexttt
                        processList.get(j).completeTime = currentTime;
                        processList.get(j).waitingTime = processList.get(j).completeTime - processList.get(j).arrivalTime - processList.get(j).originalExecution;
                        processList.get(j).turnAroundTime = processList.get(j).completeTime - processList.get(j).arrivalTime;
                        for(int k = 0; k < processesList.size();k++){
                            if(processesList.get(k).processName.equals(processList.get(j).processName)){
                                processesList.set(k, processList.get(j));
                                break;
                            }
                        }
                    }
                    lastTime = currentTime;
                }
                if(processList.size() == 1 && WaitingProcesses.size() == 0 && !ok){
                    System.out.println("Process " + processList.get(j).processName + ": " + lastTime + " to " + currentTime);
                    System.out.println("Context Switch from " + currentTime + " to " + (currentTime + context));
                    currentTime += context;
                    processList.get(j).completeTime = currentTime;
                    processList.get(j).waitingTime = processList.get(j).completeTime - processList.get(j).arrivalTime - processList.get(j).originalExecution;
                    processList.get(j).turnAroundTime = processList.get(j).completeTime - processList.get(j).arrivalTime;
                    for(int k = 0; k < processesList.size();k++){
                        if(processesList.get(k).processName.equals(processList.get(j).processName)){
                            processesList.set(k, processList.get(j));
                            break;
                        }
                    }
                }
                Sequence.add(processList.get(j).processName);
                processList.remove(j);
                j--;
            }
            if(!waiting) {
                waiting=true;

                //to see who came
                while (i < arrivalTime.size() && arrivalTime.get(i) <= currentTime) {
                    ArrayList<process> p = compressedProcess.get(arrivalTime.get(i));
                    processList.addAll(p);
                    i++;
                }
            }
            else{
                while( !WaitingProcesses.isEmpty() ) {
                    process head = WaitingProcesses.poll();
                    processList.add(head);
                }
                waiting= false;
            }

        }
        System.out.println("\n Answer: ");
        int sumTurnAroundTime = 0, sumWaitingTime = 0;
        for(int j = 0; j < processesList.size(); j++){
            System.out.println("Process Name: " + processesList.get(j).processName);
            System.out.println("Waiting Time: " + processesList.get(j).waitingTime);
            System.out.println("Turnaround Time: " + processesList.get(j).turnAroundTime);
            sumWaitingTime += processesList.get(j).waitingTime;
            sumTurnAroundTime += processesList.get(j).turnAroundTime;
            System.out.println();
        }
        double averageTurnAroundTime = (double) sumTurnAroundTime / processesList.size();
        double averageWaitingTime = (double) sumWaitingTime / processesList.size();
        System.out.println("Current time(Finished at time) in RR : " + currentTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
    }

}

//2
//5
//3
//1
//p1
//0
//4
//1
//p2
//1
//8
//1
//p3
//3
//2
//1
//p4
//10
//6
//1
//p5
//12
//5
//1




//2
//    1
//    2
//    1
//    p1
//    0
//    20