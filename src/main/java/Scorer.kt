/**
 * Created by jesus on 23/02/2017.
 */

var debug = false

fun scoreSolution (s:Solution, p: Problem) : Long {
    //take requests
    var saving = 0L
    var requestCount = 0L

    if (debug) printSolution (s, p)

    p.endpointVideoRequests!!.forEachIndexed { endpoint, requestsByVideo ->
        //Iterate en request
        requestsByVideo.entries.forEach { it ->
            val video = it.key
            val requests = it.value
            requestCount += requests
            if (debug) println ("Endpoint $endpoint requesting $requests x video $video")

            //find lower latency cache
            var minLatency = Int.MAX_VALUE
            var selectedCache = -1
            if (debug) println (p.endpointCacheLatency!![endpoint])
            p.endpointCacheLatency!![endpoint].forEach { it ->
                val cache = it.key
                val latency = it.value
                if (s.videoInCache!![video][cache] && latency < minLatency) {
                    minLatency  = latency
                    selectedCache = cache
                }
            }
            val savingLatency = p.endpointLatency!![endpoint] - minLatency
            if (savingLatency>0) {
                if (debug) println ("Use cache $selectedCache, saving $savingLatency over ${p.endpointLatency!![endpoint]}")
                saving += savingLatency * requests
                if (debug) println ("Saving total... $saving")
            } else {
                if (debug) println ("No savings")
            }
        }
    }
    if (debug) println ("Request count: $requestCount. Final score... $saving * 1000L / requestCount")
    return saving * 1000L / requestCount
}

fun printSolution (s: Solution, p: Problem) {
    (0..p.caches-1).forEach { cacheId ->
        println ("Cache $cacheId. Videos: ")
        var aux = ""
        (0..p.videos-1).forEach { videoId ->
            if (s.videoInCache!![videoId][cacheId]) {
                aux += " $videoId"
            }
        }
        println (aux)
    }
}