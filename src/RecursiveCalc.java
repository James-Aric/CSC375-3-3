import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.RecursiveAction;

public class RecursiveCalc extends RecursiveAction{
    int id;
    int height, width;
    Bar bar;
    int workLoad;
    double startX, startY, endX, endY;
    Phaser phaser;
    int iterations;

    public RecursiveCalc(Bar bar, int workLoad, double startX, double endX, double startY, double endY, Phaser phaser){
        this.width = height*2;
        this.bar = bar;
        this.workLoad = workLoad;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.phaser = phaser;
        this.iterations = 0;

    }

    @Override
    protected void compute() {

        double heightTot = endY-startY;
        double widthTot = endX-startX;
        
        if(heightTot * widthTot > 40000){
            double midHeight = Math.floor((endY - startY)/2 + startY);
            double midWidth = Math.floor((endX - startX)/2 + startX);

            new RecursiveCalc(bar, workLoad, startX, midWidth, startY, midHeight, phaser).fork();
            new RecursiveCalc(bar, workLoad, midWidth, endX, startY, midHeight, phaser).fork();
            new RecursiveCalc(bar, workLoad, startX, midWidth, midHeight, endY, phaser).fork();
            new RecursiveCalc(bar, workLoad, midWidth, endX, midHeight, endY, phaser).compute();
        }
        else{
            phaser.register();
            System.out.println("Registered Thread: " + this.toString());
            while(iterations < 5000){
                phaser.arriveAndAwaitAdvance();
                copyThisToBar();
                phaser.arriveAndAwaitAdvance();

                calcTemps();
                //System.out.println("Thread: " + this.toString() +  "      Phase: " + phaser.getPhase() + "     Iterations: " + this.iterations);
                this.iterations++;
            }
            System.out.println("Stopped: " + this.toString());
            phaser.arriveAndDeregister();
        }
    }


    public void calcTemps(){
        for (double i = startX; i < endX; i++) {
            for (double j = startY; j < endY; j++) {
                bar.calcTemp((int)i, (int)j);
            }
        }
    }

    public void copyThisToBar(){
        for(double i = startX; i < endX; i++){
            for(double j = startY; j < endY; j++){
                bar.oldAlloys[(int)i][(int)j] = new Alloy(bar.newAlloys[(int)i][(int)j]);
            }
        }
    }

}
