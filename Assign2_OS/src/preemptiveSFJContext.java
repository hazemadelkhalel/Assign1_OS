import java.util.*;

public class preemptiveSFJContext {
    public ArrayList<process> processesList;
    ArrayList<Integer> arrivalTime;
    PriorityQueue<process> sortedProcess;
    HashMap<Integer, ArrayList<process>> compressedProcess;
    preemptiveSFJContext(ArrayList<process> processesList) {
        this.processesList = processesList;
        arrivalTime = new ArrayList<>();
        sortedProcess = new PriorityQueue<process>(processesList.size(), new ProcessComparator());
        compressedProcess = new HashMap<>();
        for(int i = 0; i < processesList.size(); i++){
            if(!compressedProcess.containsKey(processesList.get(i).arrivalTime)){
                compressedProcess.put(processesList.get(i).arrivalTime, new ArrayList<>());
            }
            compressedProcess.get(processesList.get(i).arrivalTime).add(processesList.get(i));
        }
        for(Map.Entry<Integer, ArrayList<process>> entry: compressedProcess.entrySet()){
            arrivalTime.add(entry.getKey());
        }
        arrivalTime.add(1000000000);
        Collections.sort(arrivalTime);
    }

    public void preemptiveSFJ() {
        int curTime = arrivalTime.get(0);
        int contextSwitching = 0;
        ArrayList<process> initialProcess = compressedProcess.get(curTime);
        for (int i = 0; i < initialProcess.size(); i++) {
            sortedProcess.add(initialProcess.get(i));
        }
        int nextIndex = 1;
        process lastProcess = sortedProcess.peek();
        while (!sortedProcess.isEmpty()) {
            process top = sortedProcess.poll();
            if(!lastProcess.processName.equals(top.processName))
                contextSwitching++;
            lastProcess = top;
            if (curTime + top.burstTime > arrivalTime.get(nextIndex)) {
                int difference = (arrivalTime.get(nextIndex) - arrivalTime.get(nextIndex - 1));
                top.burstTime -= difference;
                curTime += difference;
                nextIndex++;
            }
            else{
                curTime += top.burstTime;
                top.burstTime = 0;
            }
            if (top.burstTime > 0) {
                sortedProcess.add(top);
            }
            if (compressedProcess.containsKey(curTime) && compressedProcess.get(curTime).size() > 0) {
                ArrayList<process> currentProcess = compressedProcess.get(curTime);
                for (int i = 0; i < currentProcess.size(); i++) {
                    sortedProcess.add(currentProcess.get(i));
                }
            }
        }
        System.out.println(curTime + contextSwitching);

    }
}

