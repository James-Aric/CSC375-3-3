public class ThreadTest implements Runnable{
    int id;
    int height, width;
    Bar oldBar, currentBar;
    int workLoad, totalNum;
    int startX, startY, endX, endY;
    public ThreadTest(int id, int height, Bar bar, int totalNum){
        this.id = id;
        this.height = height;
        this.width = height*2;
        this.oldBar = bar;
        this.totalNum = totalNum;
        if(id == 0){
            this.startX = 0;
            this.startY = 0;
            this.endX = (width/totalNum) - 1;
            this.endY = height-1;
        }
        else{
            this.startX = id * (width/totalNum) - 1;
            this.startY = 0;
            this.endX = (id+1) * (width/totalNum) - 1;
            this.endY = height-1;
        }
    }

 /*   @Override
    protected void compute() {
        System.out.println(startX + "  " + endX + "   " + startY + "   " + endY);
        if(this.workLoad == 1) {
            for (int i = startY; i < endY; i++) {
                for (int j = startX; j < endX; j++) {
                    bar.calcTemp(i, j);
                }
            }
            System.out.println(id);
        }
        else{
            List<RecursiveCalc> subtasks = new ArrayList<>();

            subtasks.addAll(createSubtasks());
            for(RecursiveCalc subtask: subtasks){
                subtask.fork();
            }
            for(RecursiveCalc subtask: subtasks){
                subtask.join();
            }
        }
        //System.out.println("Thread ID: " + id + " DONE");
        return;
    }

    public List<RecursiveCalc> createSubtasks(){
        List<RecursiveCalc> subtasks = new ArrayList<>();
*//*

        RecursiveCalc subtask1 = new RecursiveCalc(id, height, bar, workLoad/2);
        RecursiveCalc subtask2 = new RecursiveCalc(id-1, height, bar, workLoad/2);

        subtasks.add(subtask1);
        subtasks.add(subtask2);
*//*
        for(int i = 0; i < workLoad; i++){
            subtasks.add(new RecursiveCalc(i, height, bar, 1 , workLoad));
        }
        return subtasks;
    }*/


    @Override
    public void run() {
        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                oldBar.calcTemp(i, j);
                //System.out.println(bar.newAlloys[i][j].temp);
            }
        }
        //System.out.println(id);
    }
}

