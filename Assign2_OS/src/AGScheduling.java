import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Queue;

public class AGScheduling {
    int mode;
    ArrayList<process> processesList, queue;
    HashMap<Integer, ArrayList<process>> compressedProcess;
    AGScheduling(ArrayList<process> processesList){
        this.processesList = processesList;
        queue = new ArrayList<>();
        mode = 0;
        compressedProcess = new HashMap<>();
        for(int i = 0; i < processesList.size(); i++){
            if(!compressedProcess.containsKey(processesList.get(i).arrivalTime)){
                compressedProcess.put(processesList.get(i).arrivalTime, new ArrayList<>());
            }
            compressedProcess.get(processesList.get(i).arrivalTime).add(processesList.get(i));
        }
    }
    void AGSchedulingProcessing(){
        int curTime = 0;
        process curProcess = null;
        int timeTaken = 0;
        int totalTime = 0;
        int priorityIndex = -1;
        while (!compressedProcess.isEmpty() || !queue.isEmpty() || (curProcess != null && curProcess.burstTime > 0)){
            if(compressedProcess.containsKey(curTime)){
                for(int i = 0; i < compressedProcess.get(curTime).size(); i++){
                    queue.add(compressedProcess.get(curTime).get(i));
                }
                compressedProcess.remove(curTime);
            }
            if(curProcess == null){
                if(!queue.isEmpty()){
                    curProcess = queue.get(0);
                    queue.remove(0);
                    mode = 0;
                }
            }
            if(curProcess != null){
                if(curProcess.burstTime == 0){
                    // senario 4
                    curProcess.quantum = 0;
                    curProcess.quarterQuantum = 0;
                    for(int i = 0; i < processesList.size();i++){
                        if(processesList.get(i).processName.equals(curProcess.processName)){
                            processesList.set(i, curProcess);
                            break;
                        }
                    }
                    if(!queue.isEmpty()){
                        curProcess = queue.get(0);
                        queue.remove(0);
                        timeTaken = 0;
                        totalTime = 0;
                        priorityIndex = -1;
                        mode = 0;
                        continue;
                    }
                }
                else if(curProcess.quantum - totalTime == 0){
                    // senario 1
                    curProcess.quantum += 2;
                    curProcess.quarterQuantum = (curProcess.quantum + 3)/4;
                    queue.add(curProcess);

                    curProcess = queue.get(0);
                    queue.remove(0);
                    timeTaken = 0;
                    totalTime = 0;
                    priorityIndex = -1;
                    mode = 0;
                    continue;
                }
                else{
                    if(curProcess.quarterQuantum == timeTaken){
                        mode++;
                        if(mode == 2){
                            if(priorityIndex != -1){
                                // senario 2
                                curProcess.quantum += (curProcess.quantum - totalTime + 1) / 2;
                                curProcess.quarterQuantum = (curProcess.quantum + 3) / 4;
                                queue.add(curProcess);
                                curProcess = queue.get(priorityIndex);
                                queue.remove(priorityIndex);
                                timeTaken = 0;
                                totalTime = 0;
                                priorityIndex = -1;
                                mode = 0;
                                continue;
                            }
                        }
                    }
                    if(mode == 0){
                        // First come first serve just continue processing
                    }
                    else if(mode == 1){
                        int tempPriority = curProcess.priority;
                        for (int i = 0; i < queue.size(); i++) {
                            if (queue.get(i).priority < tempPriority) {
                                priorityIndex = i;
                            }
                        }
                    }
                    else{
                        int index = -1, minBurstTime = curProcess.burstTime;
                        for (int i = 0; i < queue.size(); i++) {
                            if (queue.get(i).burstTime < minBurstTime) {
                                index = i;
                            }
                        }
                        if(index != -1){
                            // senario 3
                            curProcess.quantum += (curProcess.quantum - totalTime);
                            curProcess.quarterQuantum = (curProcess.quantum + 3)/4;
                            queue.add(curProcess);
                            curProcess = queue.get(index);
                            queue.remove(index);
                            timeTaken = 0;
                            totalTime = 0;
                            priorityIndex = -1;
                            mode = 0;
                            continue;
                        }
                    }
                    curProcess.burstTime--;
                    totalTime++;
                    timeTaken++;
                }
            }
            curTime++;
        }
        System.out.println(curTime);
    }
}
