package algorithms.graph;

// 对图G随机生成边,边的个数的期望值为E
// 这里生成最大边数用高斯公式计算，并不是V ^ 2,而是(V ^ 2) / 2,所以期望生成E条边的概率大概为 E * 2 / V ^ 2， 即 p 的值
public class RandomGraph {

    public RandomGraph( Graph graph, int V, int E){

        double p = (double)2*E / (V*(V-1));

        for( int i = 0 ; i < V ; i ++ )
            for( int j = i+1 ; j < V ; j ++ ){

                double randomValue = Math.random();
                if( randomValue < p ){
                    graph.addEdge(i,j);
                }
            }
    }
}
