import java.util.ArrayList;
import java.util.HashMap;

public class FirstFit {
//    ArrayList<String>allocation; //hold process names that are allocated
    HashMap<Pair , String> allocation;
    FirstFit(){
        allocation = new HashMap<>();
    }
    void firstFit(ArrayList<Pair> partitions , ArrayList<Pair> processes){
        int lastIndex = partitions.size();
        for(int i=0 ; i<partitions.size() ; i++){
            allocation.put(partitions.get(i) , ""); //no process yettt
        }
        for(int i=0 ; i<processes.size() ; i++){
            String processName = processes.get(i).name;
            int processSize = processes.get(i).size;
            int j;
            for(j=0 ; j<partitions.size() ; j++){
                if(j == partitions.size()-1 && processSize>partitions.get(j).size){
                    processes.get(i).size = -1; //can not be allocated
                }
                else if(allocation.get( partitions.get(j) ) != "" || processSize>partitions.get(j).size){
                    //not possible , continue
                    continue;
                }
                else if(processSize==partitions.get(j).size){
//                    allocation.add(j , processName);
                    allocation.put(partitions.get(j) , processName); //Allocation
                    processes.get(i).size = 1; // indication that process is allocated
                    break;
                }
                else if(processSize<partitions.get(j).size){
                    int remain = partitions.get(j).size - processSize;
                    partitions.get(j).size = processSize;
//                    allocation.add(j , processName);
                    allocation.put(partitions.get(j) , processName);
                    //make a new partition
                    Pair partition = new Pair();
                    partition.name = "partition"+lastIndex;
                    partition.size = remain;
                    lastIndex++; //3shan ely gyy b3d kda
                    partitions.add(j+1 , partition);
                    allocation.put(partition , "");
                    processes.get(i).size = 1; // indication that process is allocated
                    break;
                }
            }

        }

        //printing
        for(int i=0 ; i<partitions.size() ; i++){
            if( allocation.get( partitions.get(i) )  != "") //process inside
                System.out.println(partitions.get(i).name + '(' + partitions.get(i).size + " KB) => " + allocation.get( partitions.get(i) )  );
            else{
                System.out.println(partitions.get(i).name + '(' + partitions.get(i).size + " KB) => External Fragment");
            }
        }
        System.out.println();
        for(int i=0 ;i<processes.size() ; i++){
            if(processes.get(i).size == -1){
                System.out.println(processes.get(i).name + " can not be allocated.");
            }
        }

    }
}
