package algorithm;

import java.util.*;

/**
 * Created by kp on 16/10/7.
 */

/**
 * Astar算法解析：路径算法（带有坐标的），根据起始点首先将起始点放到一个存放路径的列表中，，将周围的八个点放到另一个列表中，然后计算八个点到目标点
 * 所需要花费的代价，选择最小的，然后下一个点就选择该点，接下来选择剩余的八个点进行检测，同样去选择最短距离，如果遇到障碍物，则选择旁边的点，但是如果
 * 旁边的点同样在将要选择的点中，以上一个坐标点为基准，然后进行代价的对比，选择最优的。
 *  树的算法：依次选择花费最小的路径直至达到目标点，每次将最短的路径依次放在前面
 * */
public class Astar {

    public Astar(){

    }

    public void method(HashMap<Character, HashMap<Character,Integer>> graph , Character startSpot,Character endSpot){

        String route_final = "";
        Integer cost_final = null;
        Queue<Character> queue = new LinkedList<Character>();
        queue.add(startSpot);

        AstarData astarData = new AstarData();

        astarData.setRoutelist(startSpot.toString());
        astarData.setNode(startSpot);
        astarData.setCost(0);
        astarData.setFlag(true);

        boolean flagend = false;
        //用来存储最后的结果
        ArrayList<AstarData> hashMapFinal = new ArrayList<AstarData>();

        hashMapFinal.add(astarData);

        while(!queue.isEmpty()){
            Character top = queue.poll();

            for (Map.Entry<Character,Integer> map : graph.get(top).entrySet()){
                AstarData astarData1 = hashMapFinal.get(0)
                        .generateData(map.getKey(), map.getValue(),true);
                hashMapFinal.add(astarData1);
               //检测每次是否会得到最终的目标点
                if(astarData1.getRoutelist().contains(endSpot.toString())){
                    //得到最终的数值
                    route_final = astarData1.getRoutelist();
                    cost_final = astarData1.getCost();
                    //得到最终数据之后，更新flagend用来跳出while循环
                   flagend = true;
                   break;
               }

            }
            //跳出最终的循环
            if(flagend){
                break;
            }
            //去掉更新之前的数据值
            for (int i = 0;i < hashMapFinal.size(); i++){
                if(hashMapFinal.get(i).isFlag()==false){
                    hashMapFinal.remove(i);
                }
            }
            //每次清空queue从而可以重新装入数值
            queue.clear();
            //更新数值使之按照路径最短的顺序进行排列
            Collections.sort(hashMapFinal);
            //将里面的数值放入queue中以用来前面循环的取出，这个东西似乎没有作用。
            for (int j = 0;j < hashMapFinal.size();j++){
                queue.add(hashMapFinal.get(j).getNode());
            }
        }
        System.out.println(route_final+cost_final);

    }



    /**
     * 需要存储的数据：到达该节点节点，一共花费的代价，到达该节点所经过的节点
     * */
    public class AstarData implements Comparable{

        boolean flag ;
        Character node = null;
        Integer cost = 0;
        String routelist = null;

        public AstarData(){

        }

        public AstarData(Character node, Integer cost,String routelist, boolean flag){
            this.flag = flag;
            this.node = node;
            this.cost = cost;
            this.routelist = routelist;
        }
        /**
         * 重新生成一个AstarData对象，去记录后面的值
         * */
        public AstarData generateData(Character node, Integer cost,boolean flag){

            Character nodeupdate = node;
            Integer costupdata = this.cost + cost;
            if(this.routelist.contains(node.toString()))
                flag = false;
            String routelistupdata = this.routelist + node;
            this.flag = false;
            return new AstarData(nodeupdate,costupdata,routelistupdata,flag);
        }

        public Integer getCost() {
            return cost;
        }

        public void setCost(Integer cost) {
                this.cost = cost;
        }

        public Character getNode() {
            return node;
        }

        public void setNode(Character node) {
            this.node = node;
        }

        public String getRoutelist() {
            return routelist;
        }

        public void setRoutelist(String routelist) {
            this.routelist = routelist;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public int compareTo(Object o) {

                AstarData astarData = (AstarData)o;

                if ( this.cost < astarData.cost ) return -1;
                if ( this.cost > astarData.cost ) return 1;

            return 0;
        }
    }
}
