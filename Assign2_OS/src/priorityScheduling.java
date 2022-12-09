import java.util.*;


public class priorityScheduling {
    ArrayList<process> sortedProcess;
    HashMap<Integer, ArrayList<process>> compressedProcess;


    priorityScheduling(ArrayList<process> processesList){
        sortedProcess = new ArrayList<>();
        compressedProcess = new HashMap<>();
        for(int i = 0; i < processesList.size(); i++){
            if(!compressedProcess.containsKey(processesList.get(i).arrivalTime)){
                compressedProcess.put(processesList.get(i).arrivalTime, new ArrayList<>());
            }
            compressedProcess.get(processesList.get(i).arrivalTime).add(processesList.get(i));
        }
    }
    void priorityScheduling(){
        int curTime = 0;
        while (!compressedProcess.isEmpty() || !sortedProcess.isEmpty()){
            if(compressedProcess.containsKey(curTime)){
                for(int i = 0; i < compressedProcess.get(curTime).size(); i++){
                    sortedProcess.add(compressedProcess.get(curTime).get(i));
                }
                compressedProcess.remove(curTime);
            }
            if(!sortedProcess.isEmpty()) {
                sortedProcess.sort(new ProcessComparator2());
                sortedProcess.get(0).burstTime--;
                if (sortedProcess.get(0).burstTime == 0) {
                    sortedProcess.remove(0);
                }
            }
            if(!sortedProcess.isEmpty()) {
                sortedProcess.get((int) sortedProcess.size() - 1).priority = Math.max(sortedProcess.get((int) (sortedProcess.size() - 1)).priority - 1, 0);
                sortedProcess.sort(new ProcessComparator2());
            }
            curTime++;
        }
        System.out.println(curTime);
    }
}
