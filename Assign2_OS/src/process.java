import java.util.Comparator;

class ProcessComparator implements Comparator<process> {
    public int compare(process s1, process s2) {
        if (s1.burstTime > s2.burstTime)
            return 1;
        else if (s1.burstTime == s2.burstTime){
            if(s1.arrivalTime > s2.arrivalTime){
                return 1;
            }
            else if(s1.arrivalTime < s2.arrivalTime){
                return -1;
            }
        }
        else{
            return -1;
        }
        return 0;
    }
}
public class process {
    String processName;
    int burstTime;
    int arrivalTime;
    int priority;

    process(String processName , int arrivalTime , int burstTime)
    {
        this.processName = processName;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }
    process(String processName , int arrivalTime , int burstTime, int priority)
    {
        this.processName = processName;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }
}
