import java.util.*;

public class RoundRobin {

    //    public ArrayList<process> processesList;
    ArrayList<Integer> arrivalTime;
    //    PriorityQueue<process> sortedProcess;
    HashMap<Integer, ArrayList<process>> compressedProcess;

    ArrayList<String>Sequence;


    RoundRobin(ArrayList<process> processesList) {
//        this.processesList = processesList;
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
        arrivalTime.add(1000000000);
        Collections.sort(arrivalTime);
    }


    public void roundRobin(int quantum)
    {
        Queue<process>WaitingProcesses = new LinkedList<process>();
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
                Sequence.add(processList.get(j).processName);
                process pp = processList.remove(j);
                j--;
            }
            if(which_arrived==true)
            {
                while (arrivalTime.get(i) <= currentTime && arrivalTime.get(i)!=1000000000) //to see who came
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
        System.out.println("Current time(Finished at time) in RR : " + currentTime);

    }

    public void printProcessesSequence()
    {
        System.out.print("The Sequence: ");
        for(int i=0 ; i<Sequence.size() ; i++)
        {
            System.out.print(Sequence.get(i) + "  ");
        }
        System.out.println();
    }



}
