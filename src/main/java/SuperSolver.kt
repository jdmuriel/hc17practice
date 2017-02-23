
class SuperSolver : ISolver {
    override fun getSolution(p: Problem): Solution {
		var sset = SolutionSet(p)
		p.endpointVideoRequests!!.forEachIndexed { endpoint, map ->
			map.entries.forEach { entry ->
				sset.addRequest(endpoint, entry.key, entry.value)
			}
		}
		
		var entries = sset.sort()
		val sol = Solution()
		sol.videoInCache = Array(p.videos) { BooleanArray(p.caches) }
		
		val cachesRemaining = IntArray(p.caches) { p.cacheCapacity }
		entries.forEach { entry ->
			if (entry.size <= cachesRemaining[entry.cache]) {
				sol.videoInCache!![entry.video]!![entry.cache] = true
				cachesRemaining[entry.cache] -= entry.size
			}
		}
		
		return sol
	}
}
