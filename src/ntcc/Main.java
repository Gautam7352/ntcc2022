package ntcc;

public class Main {

    static int time = 0;
    //calculating largest arrival time in tasklist

    static int lat(Tasks tasklist[]) {
        int lat = 0;
        for (int i = 0; i < 5; i++) {
            if (tasklist[i].arrival_time > lat) {
                lat = tasklist[i].arrival_time;
            }
        }
        return lat;
    }

    //calculating deadline closeness
    static double deadline_closeness(double time, double deadline) {
        return time / deadline;
    }

    public static void main(String[] args) {

//      Create an array for tasklist
        System.out.println("There are 5 tasks in the tasklist");
        Tasks[] tasklist = new Tasks[5];
        System.out.println("");
        System.out.println("Info of tasklist is:");
//      Create an array for queue
        Tasks[] queue = new Tasks[5];
        int queuefill = -1;

//      Fill tasklist array with 
        for (int i = 0; i < 5; i++) {
//          initialize the array with objects
            tasklist[i] = new Tasks();
        }

//      fill in values of burst time, arrival time, deadline time and membership type
        tasklist[0].burst_time = 5;
        tasklist[1].burst_time = 2;
        tasklist[2].burst_time = 6;
        tasklist[3].burst_time = 7;
        tasklist[4].burst_time = 4;

        tasklist[0].arrival_time = 1;
        tasklist[1].arrival_time = 2;
        tasklist[2].arrival_time = 3;
        tasklist[3].arrival_time = 4;
        tasklist[4].arrival_time = 6;
        
        tasklist[0].deadline = 8;
        tasklist[1].deadline = 10;
        tasklist[2].deadline = 10;
        tasklist[3].deadline = 15;
        tasklist[4].deadline = 20;

        
        tasklist[0].membership_type = 0.5f;
        tasklist[1].membership_type = 1.0f;
        tasklist[2].membership_type = 0.0f;
        tasklist[3].membership_type = 0.0f;
        tasklist[4].membership_type = 0.5f;

        Tasks obj = new Tasks();
    System.out.println("Membership Type\t\tArrival time\t\tBurst time\t\tDeadline at T=?");
        for (int i = 0; i < 5; i++) {
            System.out.println(tasklist[i].membership_type+"\t\t\t\t"+tasklist[i].arrival_time+"\t\t\t"+tasklist[i].burst_time+"\t\t\t"+tasklist[i].deadline);
        }
    System.out.println("");
        System.out.println("timer starts");
        System.out.println("");
//      Timer starts:        
        for (;time<=24; ++time) {
            
            System.out.println("At time T="+time);
//        if arrival time of tasks in tasklist is equal to time then task is inserted to the queue
            for (int k = 0; k < 5; k++) {
                if (tasklist[k].arrival_time == time) {
                    System.out.println(queuefill);
                    queuefill++;
                    queue[queuefill] = tasklist[k];
                }
            }

            int i = 0;//variable to store index of highest priority
//        	priority is calculated
            if (queuefill > -1) {
                i = priority(queue);

//			updating burst time and waiting time
                for (int j = 0; j < 5; j++) {//          	for all elements in queue
                    if (j == i)//          			if(priority element)
                    {
                        queue[j].burst_time--;//          	burst time--
                    } else {
                        if (queue[j] != null) {
                            queue[j].waiting_time++;//          else waiting time++
                        }
                    }
                }

                //removing unnecessary tasks from queue
                for (int j = 0; j < 5; j++) {//        	for all elements in queue
                    if (queue[j] != null) {
                        if (queue[j].burst_time == 0 || queue[j].deadline == time) {//        	if(burst time = 0 || element deadline = time)
//     		remove item from queue
//     		shift items after the element to empty slot
//              make sure elements are inserted after the removed object in the queue
                            queuefill--;
                            if (j == 4) {
                                queue[j] = obj;
                            } else {
                                for (int l = j; l < 4; l++) {
                                    queue[l] = queue[l + 1];
                                    if (l == 4) {
                                        queue[l] = obj;
                                    }
                                }
                            }
                        }
                    }
                }
            }

//		end program if no task left in tasklist
            if (lat(tasklist) > 10 && queuefill == -1) {
                return;
            }
            //   System.out.println("Time t =" + time);
            for (int t = 0; t < 5; t++) {
                System.out.println(tasklist[t].membership_type + "		" + tasklist[t].arrival_time + "		" + tasklist[t].waiting_time + "		" + tasklist[t].burst_time);
            }

        }
    }

    public static int priority(Tasks[] queue) {
        //returning if queue is empty
        if (queue[0] == null) {
            return -1;

        }

        double priority[] = new double[5];//array to store priority values
        double temp = 0;//temporary variable to store max bt then to find max priority
        int maxpriority = -1;//variable to store max priority index

        int maxweight = queue[0].burst_time;//variable to store max burst time

        //finding max burst time
        for (int i = 0; i < 5; i++) {
            if (queue[i] != null) {
                maxweight = queue[i].burst_time;
            }
        }
        //calculate and store priority values
        for (int i = 0; i < 5; i++) {
            if (queue[i] != null) {
                priority[i] = (queue[i].membership_type + queue[i].waiting_time + ((double) queue[i].burst_time / (double) maxweight) + deadline_closeness(time, queue[i].deadline)) / 4;
            }

        }
        //finding index with max priority
        temp = 0;
        for (int i = 0; i < 5; i++) {
            if (temp < priority[i]) {
                temp = priority[i];
                maxpriority = i;
            }
        }
        //return highest priority index in the queue
        return maxpriority;
    }
}