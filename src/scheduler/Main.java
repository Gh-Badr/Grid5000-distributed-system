package scheduler;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        scheduler.LocalScheduler scheduler = new scheduler.LocalScheduler();

        Task t1 = new Task("T1");
        Task t2 = new Task("T2");
        Task t3 = new Task("T3");
        Task t4 = new Task("T4");
        Task t5 = new Task("T5");
        Task t6 = new Task("T6");
        Task t7 = new Task("T7");
        Task t8 = new Task("T8");
        Task t9 = new Task("T9");

        t1.addDependency("T2");
        t1.addDependency("T3");
        t1.addDependency("T4");
        t2.addDependency("T5");
        t3.addDependency("T6");
        t3.addDependency("T7");
        t4.addDependency("T8");
        t8.addDependency("T9");
        t5.addDependency("T9");
        t6.addDependency("T9");
        t7.addDependency("T9");

        scheduler.addTask(t1);
        scheduler.addTask(t2);
        scheduler.addTask(t3);
        scheduler.addTask(t4);
        scheduler.addTask(t5);
        scheduler.addTask(t6);
        scheduler.addTask(t7);
        scheduler.addTask(t8);
        scheduler.addTask(t9);

        scheduler.executeTasks();
    }
}
