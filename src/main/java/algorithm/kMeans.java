package algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * Created by kp on 16/9/24.
 * Kmeans算法是一种无监督的机器学习方法，难点在于选取k个聚类中心，选取方法是随机选取一个点，然后选取距离该点最远的距离的点，
 * 然后选取距离前两个点最近的点距离最远的点作为第三个点，依次类推。。
 */
public class kMeans {

    /**
     * @param set 用来产生不同的聚类点
     * @param a 用来装载需要分类的数据
     * @param n 一共数据的个数。
     * @param spot 用来存储聚类的中心点
     * */
    private HashSet<Integer>  set = new HashSet<Integer>();
    private int a[][] = null;
    private int[][] spot = null;
    private ArrayList<Integer>[] result = null;
    private ArrayList<int[]>[] resultSpot = null;
    private int n = 0;

    private ArrayList<Integer> yRecord = new ArrayList<Integer>();
        /**
         * kmeans构造函数
         * */
    public kMeans(int n,ArrayList<Integer> list){

        int k = 0;
        this.a = new int[n][2];
        this.n = n;

        int yMin = list.get(1);

        int yMinIndex = 0;

        for (int i  = 0; i < n;i++){
           for(int j = 0; j< 2; j++){
            a[i][j] = list.get(k++);
               //寻找出处于最左下角的点
               if ( j % 2 == 1 ){
                   if(yMin > a[i][j]){
                       yMin = a[i][j];
                       yMinIndex = i;
                   }else {
                       if(yMin == a[i][j]){
                            if(a[yMinIndex][0] > a[i][0]){
                               yMinIndex = i;
                           }
                       }
                   }
               }
           }
        }

        this.yRecord.add(yMin);
        this.yRecord.add(yMinIndex);
    }

    /**
     * 用来产生随机数据；
     * @param k 产生聚类的个数
     *
     *  随机产生数据的结果，使得分类不精确，因为当数据点之间的距离相差不大并且数据点的数量不多的时候，
     *  会造成数据点按照第一次的分类进行聚类，造成聚类中心不会改变。
     * */
    public void method(int k) throws Exception{
        /**
         * 运行的时候，二选一
         * */
        //随机产生聚类中心点
       // generateNum(k,this.set);

        //按照最大距离产生聚类中心点
        generateNumFar(k);

        int spotNum = 0;
        this.spot = new int[k][2];
        this.result = new ArrayList[k];
        this.resultSpot = new ArrayList[k];
        for(int j = 0; j < k ; j++){
            this.result[j] = new ArrayList<Integer>();
            this.resultSpot[j] = new ArrayList<int[]>();
        }
        for (Integer dot : set){
            spot[spotNum] = a[dot];
            spotNum++;
        }

        getResult(k);
    }

    /**
     * @param set 里面存储产生随机数据的个数，要求是1~一共数据的个数m，产生聚类中心
     * */
    public void generateNum(int m,HashSet<Integer> set) throws Exception{
        if (m > n){
           throw new Exception("分类的数目不能大于数据的数目");
        }else {
            int temp;
            for (int i = 0; i < m; i++) {
                temp = new Random().nextInt(this.n);
                set.add(temp);
            }
            int size = set.size();
            if (size <= m)
                generateNum(m - size, set);
            else
                this.set = set;
            System.out.println("the set is: "+set);
        }
    }

    /**
     *利用聚类点相距的最大距离去构造聚类点
     * @param m 产生m个聚类中心
     * */
    public void generateNumFar(int m) throws Exception{
        int[] firstSpot = new int[2];
        int[] recordIndex = new int[m];

        boolean mflag = true;
        if (m > n){
            throw new Exception("分类的数目不能大于数据的数目");
        }else {

            firstSpot[0] = a[this.yRecord.get(1)][0];
            firstSpot[1] = a[this.yRecord.get(1)][1];
            recordIndex[0] = this.yRecord.get(1);
            /**
             * 利用迭代的方法找出聚类中心点，
             * */
            for (int i = 1; i < m ; i++) {
            double maxDis = 0;
            int temp = 0;
                /**
                 * 第一次寻找另一个中心点，因为寻找两个以上的中心点的时候，需要求聚类中心的坐标，此时需要将各个中心点的坐标
                 * 进行相加求和，其实是可以放在一起的，但是有些偷懒了<!  !>，所以不用脑力，就要多体力
                 * */
            if(mflag){
            for (int j = 0; j < this.a.length; j++) {
                    if(maxDis < Math.sqrt( Math.pow( this.a[j][0] - firstSpot[0],2)+Math.pow(this.a[j][1] - firstSpot[1],2))){
                    maxDis = Math.sqrt( Math.pow( this.a[j][0] - firstSpot[0],2)+Math.pow(this.a[j][1] - firstSpot[1],2));
                        temp = j;
                    }
                }
                recordIndex[i] = temp;
                mflag = false;
            }else{
                double[] tempIndex = new double[2];
                double sumX = 0;
                double sumY = 0;

                for (int j = 0; j < i ; j++) {
                    sumX = sumX + this.a[recordIndex[j]][0];
                    sumY = sumY + this.a[recordIndex[j]][1];
                }

                tempIndex[0] = sumX / i;
                tempIndex[1] = sumY / i;

                for (int j = 0; j < this.a.length; j++) {

                    //判断所检测的数据点是否在聚类中心的数组中，如果在进行忽略。
                    /**
                     * 但是这里面有一个bug，如果没有在聚类中心数组中点，但是距离聚类中心各个点求出中心点距离很远并且又在聚类中心附近，这里面就很蛋疼了。
                     * 以后有待更新
                     * */
                    boolean recordFlag = true;
                    for(int ktemp = 0; ktemp < i ; ktemp++){
                        if(j == recordIndex[ktemp]){
                            recordFlag = false;
                        }
                    }

                    if(recordFlag){
                    if(maxDis < Math.sqrt( Math.pow( this.a[j][0] - tempIndex[0],2)+Math.pow(this.a[j][1] - tempIndex[1],2))){
                        maxDis = Math.sqrt( Math.pow( this.a[j][0] - tempIndex[0],2)+Math.pow(this.a[j][1] - tempIndex[1],2));
                        temp = j;
                        }
                    }
                }
                recordIndex[i] = temp;
            }
            }
        }
        for (int i = 0; i < m; i++) {
            this.set.add(recordIndex[i]);
        }
        System.out.println(this.set);
    }

    public int[][] getA() {
        return a;
    }

    /**
     * 获取最终的聚类中心点，在第一次已经获取到分类，在这个过程中进行迭代，每次迭代更新聚类中心，
     * 聚类中心不一定是实点，也可以是几个点之间的中心点
     * */
    private void getResult(int k){

        boolean flag = true;
        for (int i = 0; i < a.length; i++) {
            getMin(a[i],i);
        }

        for (int i = 0; i < k; i++) {
            int sumOne = 0;
            int sumTwo = 0;
            for (int j = 0; j < this.resultSpot[i].size(); j++) {
                sumOne = sumOne + this.resultSpot[i].get(j)[0];
                sumTwo = sumTwo + this.resultSpot[i].get(j)[1];
            }
            //更新聚类节点，spot，因为出现虚的聚类中心，所以在这里定义了一个聚类中心的误差
            if(this.spot[i][0] - sumOne/this.resultSpot[i].size() > 0.01 || this.spot[i][1] - sumTwo/this.resultSpot[i].size()> 0.01) {
                flag = false;
                this.spot[i][0] = sumOne/this.resultSpot[i].size();
                this.spot[i][1] = sumTwo/this.resultSpot[i].size();
            }
        }
        if(!flag){
            for (int i = 0; i < k ; i++) {
                this.result[i].clear();
                this.resultSpot[i].clear();
            }
            getResult(k);
        }else{
            for (int i = 0; i < this.result.length; i++) {
                System.out.println(this.result[i]);
            }

        }
    }
        /**
         *分类之后，每个点到聚类中心的距离
         * */
    private void getMin(int[] aSpot, int aNum){
        int k = 0;
        double min = Math.sqrt(Math.pow((this.spot[0][0]- this.a[aNum][0]),2)+Math.pow((this.spot[0][1] - this.a[aNum][1]),2));
        for (int i = 1; i < spot.length; i++) {
            if( min >  Math.sqrt(Math.pow((this.spot[i][0]- this.a[aNum][0]),2)+Math.pow((this.spot[i][1] - this.a[aNum][1]),2))){
                k = i;
                min = Math.sqrt(Math.pow((this.spot[i][0]- this.a[aNum][0]),2)+Math.pow((this.spot[i][1] - this.a[aNum][1]),2));
            }
        }
        this.result[k].add(aNum);
        this.resultSpot[k].add(aSpot);
    }
}
