import java.util.ArrayList;
import java.util.Random;

public class Bar {

    Random rand = new Random();
    final double constants[] = {.75, 1.0, 1.25};

    double sTemp;
    double tTemp;

    int width;
    int height;
    double threshold;
    volatile Alloy[][] oldAlloys, newAlloys;

    double constantSum;

    public Bar(double sTemp, double tTemp, int height, double threshold){
        this.sTemp = sTemp;
        this.tTemp = tTemp;
        this.height = height;
        this.width = height * 2;
        this.threshold = threshold;
        this.oldAlloys = new Alloy[this.width][this.height];
        this.newAlloys = new Alloy[this.width][this.height];
        constantSum = constants[0] + constants[1] + constants[2];
        initializeBar();
    }
    public Bar(Bar b){
        this.sTemp = b.sTemp;
        this.tTemp = b.tTemp;
        this.height = b.height;
        this.width = b.width;
        this.threshold = b.threshold;
        this.oldAlloys = b.oldAlloys;
        this.newAlloys = b.newAlloys;
    }

    public void initializeBar(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                //create new alloy with varying % of metal
                double t1 = rand.nextInt(6) + 1;
                double t2 = rand.nextInt(6) + 1;
                double t3 = rand.nextInt(6) + 1;
                double sum = t1+t2+t3;
                oldAlloys[i][j] = new Alloy(t1/sum,t2/sum,t3/sum, 0);//rand.nextInt(125));
                //oldAlloys[i][j] = new Alloy(.4, .4, .2, 0);
                newAlloys[i][j] = new Alloy(oldAlloys[i][j]);//System.out.println(oldAlloys[i][j].temp);
            }
        }
        oldAlloys[0][0].setTemp(sTemp);
        oldAlloys[width-1][height-1].setTemp(tTemp);
        newAlloys[0][0] = new Alloy(oldAlloys[0][0]);
        newAlloys[width-1][height-1] = new Alloy(oldAlloys[width-1][height-1]);
    }

    public void calcTemp(int row, int col){
        double temp = 0;
        ArrayList<Alloy> neighbors = getNeighbors(row, col);
        double size = neighbors.size();
        for(int i = 0; i < 3; i++){
            double insideJunk = 0;
            for(int j = 0; j < neighbors.size(); j++){
                insideJunk += (neighbors.get(j).temp * neighbors.get(j).getPercent(i));
            }
            temp += constants[i] * (insideJunk/(size*constantSum));
        }
        if((row == 0 && col == 0) || (row == width-1 && col == height-1)){
            newAlloys[row][col] = new Alloy(oldAlloys[row][col], 250);
        }
        else{
            newAlloys[row][col] = new Alloy(oldAlloys[row][col], temp);
        }
        /*else if(row == width-1 && col == height-1){
            if(temp > 250){
                newAlloys[row][col] = new Alloy(oldAlloys[row][col], 250);
            }
            else if(temp > oldAlloys[row][col].temp){
                newAlloys[row][col] = new Alloy(oldAlloys[row][col], temp);
            }
        }
        else if(temp > 250){
            newAlloys[row][col] = new Alloy(oldAlloys[row][col], 250);
        }
        else{
            newAlloys[row][col] = new Alloy(oldAlloys[row][col], temp);
        }*/
    }


    public ArrayList<Alloy> getNeighbors(int r, int c){
        ArrayList<Alloy> temp = new ArrayList<>();
        int[] colCombos = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] rowCombos = {-1, 1, 0, 1, -1, -1, 0, 1};
        for(int i = 0; i < 8; i++){
            try{
                temp.add(oldAlloys[r+rowCombos[i]][c+colCombos[i]]);
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }
        return temp;
    }

    public void printOldAlloys(){
        for(int i = 0; i < width; i++){
            System.out.println();
            for(int j = 0; j < height; j++){
                System.out.printf("\t%f\t",(double)oldAlloys[i][j].temp);
            }
        }
    }
    public void printNewAlloys(){
        for(int i = 0; i < width; i++){
            System.out.println();
            for(int j = 0; j < height; j++){
                System.out.printf("\t%f\t",(double)newAlloys[i][j].temp);
            }
        }
    }

    public void combine(int id, Bar bar, int totalNum){
        int startX, endX, startY, endY;
        if(id == 0){
            startX = 0;
        }
        else{
            startX = id * (width/totalNum) - 1;
        }
        startY = 0;
        endX = (id+1) * (width/totalNum) - 1;
        endY = height-1;
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if(bar.newAlloys[i][j].temp != 0) {
                    newAlloys[i][j] = new Alloy(bar.newAlloys[i][j]);
                }//System.out.println(bar.newAlloys[i][j].temp);
            }
        }
    }

    public void copyBars(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(newAlloys[i][j].temp != 0) {
                    oldAlloys[i][j] = new Alloy(newAlloys[i][j]);
                }
            }
        }
    }

}