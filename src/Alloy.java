public class Alloy {
    volatile double m1, m2, m3;
    volatile double temp;
    public Alloy(double m1, double m2, double m3, double temp){
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.temp = temp;
    }

    public Alloy(Alloy old, double temp){
        this.m1 = old.m1;
        this.m2 = old.m2;
        this.m3 = old.m3;
        this.temp = temp;
    }

    public Alloy(Alloy old){
        this.m1 = old.m1;
        this.m2 = old.m2;
        this.m3 = old.m3;
        this.temp = old.temp;
    }

    public double getPercent(int p){
        if(p == 0){
            return m1;
        }
        else if(p == 1){
            return m2;
        }
        else{
            return m3;
        }
    }

    public void setTemp(double temp){
        this.temp = temp;
    }

    public double getTemp(){
        return temp;
    }

    public void printPercents(){
        System.out.println("1: " + m1 + "   2: " + m2 + "   3: " + m3);
    }
}
