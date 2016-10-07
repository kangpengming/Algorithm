package algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by kp on 16/10/7.
 */
public class Search {


    /**
     * bfs算法的简介：首先会有一个顶点加入队列中，然后跟这个起始顶点相连
     * 的顶点加入到队列中，分别加入与这些顶点相连的顶点到队列中。
     *
     * 在这里面主要的难点是如果使用java去创建图队列，但是我经历过的最深刻的图队列是spider网络链接，
     *
     * bfs算法：主要是寻找最短路径，
     * dfs算法：主要用于寻找能不能达到最终的目的，解决连通性问题
     *
     * @param graph 存储的是链表中的中符号位与相对应的链接到一起的值
     * @param dist 用来存储node节点与起始节点直接的符号与对应的值
     * @param start 定义起始节点
     * */
    public void BFS(HashMap<Character, LinkedList<Character>> graph,
                    HashMap<Character, Integer> dist, char start){
        Queue<Character> q = new LinkedList<Character>();

        q.add(start);
        dist.put(start, 0);

        while (!q.isEmpty()){

            Character character = q.poll();

            //这里面起始我使用的是距离进行自加distance++，网络上的代码使用的是:这样使用会更加精确，可以保证每一次执行程序的时候完成数据的相加
            int distance = dist.get(character)+1;

            for (Character c : graph.get(character)){
            //检测键值有没有存在已经存在的键值对中。
                if(!dist.containsKey(c)){

                    q.add(c);
                    dist.put(c,distance);

                }
            }
        }

    }


    public void DFS(HashMap<Character , LinkedList<Character>> graph,HashMap<Character, Boolean> visited){
        visit(graph, visited, 's');
    }

    /**
     * @param graph  表示的是数据的node节点图
     * @param visited 表示已经访问过的节点
     * @param character 表示起始节点
     * */
    public void visit(HashMap<Character , LinkedList<Character>> graph,HashMap<Character, Boolean> visited,Character character){

        if(!visited.containsKey(character)){
            //这里面将优先搜索的数据放入创建好的队列里面，后面的true表示已经访问过
            visited.put(character,true);
            //循环相对应链接节点的数据进行遍历
            for(Character character1 : graph.get(character)){
                //遍历里面的数据，如果没有遍历，继续遍历。
                if(!visited.containsKey(character1)){

                    visit(graph,visited,character1);
                }
            }
        }
    }

    //测试数据的方法：
    /*
       Test bb = new Test();
        // s顶点的邻接表
        LinkedList<Character> list_s = new LinkedList<Character>();
        list_s.add('w');
        list_s.add('r');
        LinkedList<Character> list_w = new LinkedList<Character>();
        list_w.add('s');
        list_w.add('x');
        list_w.add('i');
        LinkedList<Character> list_r = new LinkedList<Character>();
        list_r.add('s');
        list_r.add('v');
        LinkedList<Character> list_x = new LinkedList<Character>();
        list_x.add('w');
        list_x.add('y');
        list_x.add('u');
        LinkedList<Character> list_v = new LinkedList<Character>();
        list_v.add('r');
        LinkedList<Character> list_i = new LinkedList<Character>();
        list_i.add('w');
        LinkedList<Character> list_u = new LinkedList<Character>();
        list_u.add('x');
        LinkedList<Character> list_y = new LinkedList<Character>();
        list_y.add('x');
        HashMap<Character, LinkedList<Character>> graph = new HashMap<Character, LinkedList<Character>>();
        graph.put('s', list_s);
        graph.put('w', list_w);
        graph.put('r', list_r);
        graph.put('x', list_x);
        graph.put('v', list_v);
        graph.put('i', list_i);
        graph.put('y', list_y);
        graph.put('u', list_u);
        System.out.println("BFS starts:");
        HashMap<Character, Integer> dist = new HashMap<Character, Integer>();
        char start = 's';
        bb.bfs(graph, dist, start);
        System.out.println("DFS starts:");
        HashMap<Character, Boolean> visited=new HashMap<Character, Boolean>();
        bb.dfs(graph, visited);
     */
}
