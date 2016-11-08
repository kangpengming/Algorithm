import algorithm.Astar;
import algorithm.kMeans;

import java.awt.image.BufferedImage;
import java.util.*;


/**
 * Created by kp on 16/8/15.
 */
public class Test {
    static String[]  strkp = null;
    public static void main(String args[]) throws Exception{
    /*
        ArrayList<Integer> list = new ArrayList<Integer>();
        int[] a = {13, 9, 8, 24, 0, 29, 3, 14, 25, 3, 11, 19, 4, 28, 0, 6};
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            //int it = random.nextInt(30);
            list.add(a[i]);
        }
        System.out.println("the origin number is: "+list);
        new kMeans(8,list).method(3);
*/
        new Test().test();
    }


    public void test(){

        // s顶点的邻接表
        HashMap<Character,Integer>  map_s = new HashMap<Character, Integer>();
        map_s.put('w',2);
        map_s.put('r', 3);
        //list_s.add(map_s);


        HashMap<Character,Integer>  map_w = new HashMap<Character, Integer>();
        map_w.put('s',2);
        map_w.put('x',7);
        map_w.put('i',3);
        //list_w.add(map_w);

        HashMap<Character,Integer>  map_r = new HashMap<Character, Integer>();
        map_r.put('s',3);
        map_r.put('v',5);
        //list_r.add(map_r);

        HashMap<Character,Integer>  map_x = new HashMap<Character, Integer>();
        map_x.put('w',7);
        map_x.put('y',4);
        map_x.put('u',1);
        //list_x.add(map_x);


        HashMap<Character,Integer>  map_v = new HashMap<Character, Integer>();
        map_v.put('r',5);
       // list_v.add(map_v);

        HashMap<Character,Integer>  map_i = new HashMap<Character, Integer>();
        map_i.put('w',3);
        //list_i.add(map_i);


        HashMap<Character,Integer>  map_u = new HashMap<Character, Integer>();
        map_u.put('x',1);
        //list_u.add(map_u);


        HashMap<Character,Integer>  map_y = new HashMap<Character, Integer>();
        map_y.put('x',4);
        //list_y.add(map_y);

        HashMap<Character, HashMap<Character,Integer>> graph = new HashMap<Character, HashMap<Character,Integer>>();
        graph.put('s', map_s);
        graph.put('w', map_w);
        graph.put('r', map_r);
        graph.put('x', map_x);
        graph.put('v', map_v);
        graph.put('i', map_i);
        graph.put('y', map_y);
        graph.put('u', map_u);

       new Astar().method(graph,'s','y');
    }
}
