
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.util.*


fun main(params: Array<String>) {
	val dataset = "me_at_the_zoo"
    val p = readProblem(File ("data/${dataset}.in"))
    val s1 = SimpleSolver()
    val s = s1.getSolution (p)
    writeSolution (File ("data/${dataset}.out"), s)
}

fun readProblem (f: File) : Problem {
    val sc = Scanner(BufferedInputStream(FileInputStream(f)));
    var p = Problem()
	p.videos = sc.nextInt()
	p.endpointCount = sc.nextInt()
	p.requests = sc.nextInt()
	p.caches = sc.nextInt()
	p.cacheCapacity = sc.nextInt()
	sc.nextLine()

	p.videoSize = IntArray(p.videos)
    val row = sc.nextLine().split(" ")
    row.forEachIndexed { j, c ->
        p.videoSize!![j] = c.toInt()
    }
	
	p.endpointLatency = IntArray(p.endpointCount)
	p.endpointCacheLatency = Array<MutableMap<Int, Int>>(p.endpointCount) { mutableMapOf() }
    (0.. p.endpointCount-1).forEach { i ->
    	p.endpointLatency!![i] = sc.nextInt()
		
		val connectedCacheCount = sc.nextInt()
		(0.. connectedCacheCount-1).forEach { j ->
			val cache = sc.nextInt()
			val latency = sc.nextInt()
			sc.nextLine()
			p.endpointCacheLatency!![i][cache] = latency
		}
    }
	
	p.endpointVideoRequests = Array<MutableMap<Int,Int>>(p.endpointCount) { mutableMapOf() }
	
    (0.. p.requests-1).forEach { i ->
		val video = sc.nextInt()
		val endpoint = sc.nextInt()
		val latency = sc.nextInt()
		sc.nextLine()
		
		p.endpointVideoRequests!![endpoint].put(video, latency)
    }
	
    return p
}


fun writeSolution (f: File, s: Solution) {
    val fOut = FileWriter(f)
    fOut.write("${s.slices.size}\n")
    s.slices.forEach { slice ->
        fOut.write ("${slice.r1} ${slice.c1} ${slice.r2} ${slice.c2}\n")
    }
    fOut.flush()
}
