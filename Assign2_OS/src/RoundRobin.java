import java.util.*;

public class RoundRobin {

    //    public ArrayList<process> processesList;
    ArrayList<Integer> arrivalTime;
    //    PriorityQueue<process> sortedProcess;
    HashMap<Integer, ArrayList<process>> compressedProcess;




    RoundRobin(ArrayList<process> processesList) {
//        this.processesList = processesList;
        arrivalTime = new ArrayList<>();

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
        arrivalTime.add(1000000000);
        Collections.sort(arrivalTime);
    }


    public void roundRobin(int quantum)
    {
        Queue<process> WaitingProcesses = new LinkedList<process>();
        Queue<process>ArrivedProcesses = new LinkedList<process>(); //who came
//        int current_time=0;
        int currentTime = arrivalTime.get(0);
        ArrayList<process> processList = compressedProcess.get(currentTime);
        int contextSwitching = 0;
        boolean which_arrived=true; //who came
        boolean waiting = false;
        int i=1;
        while( ! processList.isEmpty() ) //veryyyy big loop
        {
            for(int j=0 ; j<processList.size() ; j++)
            {
                System.out.println("The process now is : " + processList.get(j).processName);
                //check if its execution time is greater than quantum or not
                if( processList.get(j).burstTime != 0 )
                {
                    if(processList.get(j).burstTime <= quantum)
                    {
                        currentTime += processList.get(j).burstTime;
                        currentTime++; //for the contexttt
                        processList.get(j).burstTime = 0;

                    }
                    else if(processList.get(j).burstTime > quantum){
                        currentTime += quantum;
                        currentTime++; //for the contexttt
                        processList.get(j).burstTime -= quantum;
                        WaitingProcesses.add(processList.get(j));
                    }
                }

                processList.remove(j);
            }
            if(which_arrived==true)
            {
                while (arrivalTime.get(i) <= currentTime && i<arrivalTime.size()) //to see who came
                {
                    ArrayList<process> p = compressedProcess.get(arrivalTime.get(i));
                    for(int k=0 ; k<p.size() ; k++)
                    {
                        processList.add(p.get(k));
                    }
                    i++;
                }
                which_arrived=false;
                waiting=true;
                continue;

            }
            else if(waiting == true)
            {
                while( !WaitingProcesses.isEmpty() )
                {
                    process head = WaitingProcesses.peek();
                    processList.add(head);
                    WaitingProcesses.poll();
                }
                waiting=false;
                which_arrived = true;
            }

        }
        System.out.println("Current in RR : " + currentTime);

    }


}

