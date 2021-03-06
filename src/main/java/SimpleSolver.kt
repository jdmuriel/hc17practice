import java.io.File
import java.io.FileWriter

/**
 * Created by jesus on 21/02/2017.
 */


class SimpleSolver : ISolver {

    //Silly solution: put first videos fitting in caches in all caches
    override fun getSolution(p: Problem): Solution {
        var maxVideo = 0
        var size = 0
        while (maxVideo < p.videos && size <= p.cacheCapacity) {
            maxVideo++
            size+=p.videoSize!![maxVideo-1]
        }
        val s = Solution()
        for (j in 0..maxVideo) {
            for (i in 0..p.caches-1)
            s.videoInCache!![j][i] = true
        }
        return s
    }

}


class SmallVideosSolver : ISolver {

    //Silly solution: put smaller videos fitting in caches in all caches
    override fun getSolution(p: Problem): Solution {
        val sortedVideos = p.videoSize!!.mapIndexed { i: Int, size: Int -> Pair (i,size) }.sortedBy { it.second }
        val selectedVideoIds = mutableListOf<Int>()

        var size = 0
        sortedVideos.forEach { pair ->
            if (size + pair.second > p.cacheCapacity)
                return@forEach
            size += pair.second
            selectedVideoIds.add (pair.first)
        }
        val s = Solution()
        s.videoInCache = Array(p.videos) { BooleanArray(p.caches)}
        for (j in selectedVideoIds) {
            for (i in 0..p.caches-1)
                s.videoInCache!![j][i] = true
        }
        return s
    }

}

