import algorithm.kMeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by kp on 16/8/15.
 */
public class Test {
    static String[]  strkp = null;
    public static void main(String args[]) throws Exception{
        ArrayList<Integer> list = new ArrayList<Integer>();
        int[] a = {13, 9, 8, 24, 0, 29, 3, 14, 25, 3, 11, 19, 4, 28, 0, 6};
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            //int it = random.nextInt(30);
            list.add(a[i]);
        }
        System.out.println("the origin number is: "+list);
        new kMeans(8,list).method(3);
    }
}
