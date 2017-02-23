
class Problem {
    var videos: Int = 0
    var endpointCount: Int = 0
    var requests: Int = 0     //Minimum quantity of each ingredient
    var caches: Int = 0   //Maximum cells in a slice
    var cacheCapacity: Int = 0

    var videoSize: IntArray? = null
    var endpointLatency: IntArray? = null //Latency to data center
    var endpointCacheLatency: Array<MutableMap<Int, Int>>? = null  //endpointCacheLatency[i][j] is latency from endpoint i to cache j
    var endpointVideoRequests: Array<MutableMap<Int, Int>>? = null //endpointVideoRequests[i][j] is number of expected requests from endpoint i for video j

}

class Solution {
    //Max size = 10000 videos x 1000 caches
    var videoInCache: Array<BooleanArray>? = null   //videoInCache[i][j] true if video i in cache j

}
