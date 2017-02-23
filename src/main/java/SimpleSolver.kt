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
        while (size <= p.cacheCapacity) {
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


class SmallVideos : ISolver {

    //Silly solution: put smaller videos fitting in caches in all caches
    override fun getSolution(p: Problem): Solution {
        val sortedVideos = p.videoSize!!.mapIndexed { i: Int, size: Int -> Pair (i,size) }.sortedByDescending { it.second }
        val selectedVideoIds = mutableListOf<Int>()

        var size = 0
        sortedVideos.forEach { pair ->
            if (size + pair.second > p.cacheCapacity)
                return@forEach
            size += pair.second
            selectedVideoIds.add (pair.first)
        }
        val s = Solution()
        for (j in selectedVideoIds) {
            for (i in 0..p.caches-1)
                s.videoInCache!![j][i] = true
        }
        return s
    }

}


fun writeSolution (f: File, s: Solution, p: Problem) {
    val fOut = FileWriter(f)
    fOut.write("${p.caches}\n")
    (0..p.caches-1).forEach { cacheId ->
        fOut.write ("$cacheId")
        (0..p.videos-1).forEach { videoId ->
            if (s.videoInCache!![videoId][cacheId]) {
                fOut.write (" $videoId")
            }
        }
    }
    fOut.flush()
}