/**
 * Created by jesus on 21/02/2017.
 */

public class Problem {
    int videos;
    int endpointCount;
    int requests;     //Minimum quantity of each ingredient
    int caches;   //Maximum cells in a slice
    int cacheCapacity;

    int [] videoSize;
    int [] endpointLatency; //Latency to data center
    int [][] endpointCacheLatency;  //endpointCacheLatency[i][j] is latency from endpoint i to cache j
    int [][] endpointVideoRequests; //endpointVideoRequests[i][j] is number of expected requests from endpoint i for video j

}
