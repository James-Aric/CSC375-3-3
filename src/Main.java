import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Phaser;


//HAVE TO HAVE EACH THREAD ACTING ON ITS OWN INSTANCE OF BAR


public class Main {
    static final int totalNum = 8;
    static final int height = 900;
    static final int threshold = 5000;
    static BufferedImage image;
    public static void main(String[]args) throws Exception{
        long start = System.currentTimeMillis();
        Bar b = new Bar(250, 250, height, threshold);
        JFrame frame = new JFrame();
        Thread[] threads = new Thread[totalNum];
        Bar[] bars = new Bar[totalNum];

        displayGraph(b, frame);
        ForkJoinPool pool = new ForkJoinPool();
        Phaser p = new Phaser();
        RecursiveCalc test = new RecursiveCalc(b, 4, 0, height*2, 0, height, p);
        int count  = 0;
        ExecutorService es = Executors.newFixedThreadPool(totalNum);
        //pool.invoke(test);
        /*while(count != threshold){
            //System.out.println(p.getUnarrivedParties());
            while(!p.isTerminated()){
                pool.invoke(test);
            }
            b.copyBars();
            count++;
        }*/
        pool.invoke(test);
        Thread.sleep(1000);
        while(p.getRegisteredParties() > 0){

        }
        //pool.shutdown();
        displayGraph(b, frame);
        long end = System.currentTimeMillis();
        System.out.println(end-start);

        test = new RecursiveCalc(b, 4, 0, height*2, 0, height, p);

        pool.invoke(test);
        Thread.sleep(1000);
        while(p.getRegisteredParties() > 0){

        }
        displayGraph(b, frame);
        end = System.currentTimeMillis();
        System.out.println(end-start);
        //b.printNewAlloys();

        /*int count = 0;
        while(count != b.threshold){
            for(int i = 0; i < height; i++){
                for(int j = 0; j < height; j++){
                    b.calcTemp(i,j);
                }
            }

            for(int i = 0; i < height; i++) {
                for(int j = 0; j < height; j++){
                    b.oldAlloys[i][j] = new Alloy(b.newAlloys[i][j], b.newAlloys[i][j].temp);
                }
            }

            count++;
            System.out.println(count);
        }
        displayGraph(b, frame);
        System.out.println("done");
        b.printNewAlloys();*/
    }



    public static void displayGraph(Bar bar, JFrame frame){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        try {
            if (image == null) {
            /*frame = new JFrame("Testing");*/
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            /*TestPane testPane = new TestPane(bar);
            frame.add(testPane);*/
                frame.getContentPane().setLayout(new FlowLayout());
                frame.getContentPane().add(new JLabel(new ImageIcon(makeGraph(bar))));
                //System.out.println("test -1");
            /*frame.pack();*/
                frame.pack();
                //System.out.println("test");
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            /*Thread.sleep(1000);*/
                //System.out.println("test 2");
                //frame.add(new TestPane(bar));
            } else {
                frame.getContentPane().removeAll();
                frame.getContentPane().setLayout(new FlowLayout());
                frame.getContentPane().add(new JLabel(new ImageIcon(makeGraph(bar))));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static BufferedImage makeGraph(Bar bar){
        Color c;
        image = new BufferedImage(height*2, height,  BufferedImage.TYPE_4BYTE_ABGR);
        for(int i = 0; i < height*2; i++){
            for(int j = 0; j < height; j++){
                c = new Color((int)bar.newAlloys[i][j].temp, 0, 0);
                image.setRGB(i, j, c.getRGB());
            }
        }
        return image;
    }




/*    public static class TestPane extends JPanel {

        public TestPane(Bar bar) {
            setLayout(new GridLayout(bar.height, bar.width, 0, 0));
            Random rnd = new Random();
            Color c;
            int color;
            JPanel cell;
            Color[] colors = new Color[]{Color.GREEN, Color.BLUE, Color.RED, Color.MAGENTA};
            for (int row = 0; row < bar.height; row++) {
                for (int col = 0; col < bar.height; col++) {
                    cell = new JPanel() {
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(1, 1);
                        }
                    };
                    //color = rnd.nextInt(4);
                    c = new Color((int)bar.newAlloys[row][col].temp,0,0);
                    //System.out.println(c.getRed());
                    cell.setBackground(c);
                    add(cell);
                }
                System.out.println(row);
            }
        }
    }*/
}
