import java.util.LinkedList

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

class SolutionSet(
	val problem: Problem) {
	
	var entries: MutableMap<Pair<Int, Int>, SolutionEntry> = mutableMapOf()
	
	fun addRequest(endpoint: Int, video: Int, requests: Int) {
		(0..problem.caches-1).forEach { addCacheRequest(endpoint, video, requests, it) }
	}

	fun addCacheRequest(endpoint: Int, video: Int, requests: Int, cache: Int) {
		var entry = entries[Pair(video, cache)]
		if (entry == null) {
			entry = SolutionEntry(video, cache, problem.videoSize!![video])
			entries[Pair(video, cache)] = entry
		}
		var cacheLatency = problem.endpointCacheLatency!![endpoint]!!.get(cache)
		if (cacheLatency != null) {
			entry.addEndpointScore(requests, problem.endpointLatency!![endpoint], problem.endpointCacheLatency!![endpoint]!!.get(cache)!!)
		}
	}
	
	fun sort(): List<SolutionEntry> {
		var entriesList = LinkedList<SolutionEntry>()
		entriesList.addAll(entries.values)
		entriesList.sortBy { -it.getScore() }
		return entriesList
	}
}

class SolutionEntry(
	var video: Int,
	var cache: Int,
	
	var size: Int
	) {
	
	var timeGained: Int = 0

	fun addEndpointScore(requests: Int, datacenterLatency: Int, cacheLatency: Int) {
		timeGained += (datacenterLatency - cacheLatency) * requests
	}
	
	fun getScore(): Int {
		return timeGained / size
	}
}
