import java.util.*

/**
 * Created by jesus on 21/02/2017.
 */

data class MixerEntry (var endpoint: Int, var video: Int, var requests: Int, var score: Int )
data class CacheEntry (var cache: Int, var latency: Int, var capacity: Int)

class MixerSolver2 : ISolver {

    //Generar por cada petición el nº de milisegundos ahorrable.
    //Endpoint - ahorro máximo - nº peticiones
    //Ahorro máximo depende del endpoint sólo -> Cálculo inicial
    //Ordenar entradas por ahorro máximo * peticiones
    //Coger cada petición más valiosa y ponerla

    override fun getSolution(p: Problem): Solution {

        //Calcular ahorromaximo por endpoint
        //Clave: endpoint, valor: par <cache, ahorro>
        val cachesByEndpoint : MutableMap<Int, List<CacheEntry>> = mutableMapOf()
        p.endpointCacheLatency!!.forEachIndexed { endpoint, map ->
            var minLatency = Int.MAX_VALUE
            var selCache = -1

            val l = map.entries.map { CacheEntry(it.key, it.value, p.cacheCapacity) }
            cachesByEndpoint.put (endpoint, l.sortedBy { it.latency })
        }

        //Endpoint - ahorro máximo - nº peticiones
        //Ordenar entradas por ahorro máximo * peticiones * C * size
        //precalc map de Es por C
        val entries : ArrayList<MixerEntry> = arrayListOf()
        p.endpointVideoRequests!!.forEachIndexed { endpoint, mutableMap ->
            val cache = cachesByEndpoint[endpoint]
            mutableMap.entries.forEach { videoEntry ->
                val video = videoEntry.key
                val requests = videoEntry.value
                if (cache!!.size == 0)
                    entries.add (MixerEntry(endpoint, video, requests, Int.MAX_VALUE))
                else
                    entries.add (MixerEntry(endpoint, video, requests, cache?.get(0)?.latency?: Int.MAX_VALUE))
            }
        }
        entries.sortedByDescending { entry -> entry.score * entry.requests }

        val s = Solution()
        s.videoInCache = Array(p.videos) {BooleanArray (p.caches)}
        val fullCaches = IntArray(p.caches) { p.cacheCapacity }

        entries.forEach {
            val cache = cachesByEndpoint[it.endpoint]
            cache!!.forEach { cacheEntry ->
                if (cacheEntry.capacity > p.videoSize!![it.video]) {
                    cacheEntry.capacity -= p.videoSize!![it.video]
                    s.videoInCache!![it.video][cacheEntry.cache] = true
                    return@forEach
                }

            }
        }
        return s
    }


    //Seleccionar los endpoints más valiosos: los que combinan mayores peticiones y más ahorros al datacenter
    //Requests
    //Por cada endpoint
    //Miro el vídeo más pedido
    //Selecciono el cache más cercano.

    //Veo el vídeo

    //We get a score for each combination video - cache
    //Score CV = A * sum en Es conectados a C de los A1 * requests Ev * tiempo ahorrado de E a C
    // - B * tamaño v


}


class MixerSolver : ISolver {

    //Generar por cada petición el nº de milisegundos ahorrable.
    //Endpoint - ahorro máximo - nº peticiones
    //Ahorro máximo depende del endpoint sólo -> Cálculo inicial
    //Ordenar entradas por ahorro máximo * peticiones
    //Coger cada petición más valiosa y ponerla

    override fun getSolution(p: Problem): Solution {

        //Calcular ahorromaximo por endpoint
        //Clave: endpoint, valor: par <cache, ahorro>
        val maxSavingByEndpoint : MutableMap<Int, Pair<Int, Int>> = mutableMapOf()
        p.endpointCacheLatency!!.forEachIndexed { endpoint, map ->
            var minLatency = Int.MAX_VALUE
            var selCache = -1
            map.entries.forEach { cacheEntry ->
                val cache = cacheEntry.key
                val latency = cacheEntry.value
                if (latency< minLatency) {
                    minLatency = latency
                    selCache = cache
                }
            }
            maxSavingByEndpoint.put (endpoint, Pair(selCache, minLatency))
        }

        //Endpoint - ahorro máximo - nº peticiones
        //Ordenar entradas por ahorro máximo * peticiones * C * size
        //precalc map de Es por C
        val entries : ArrayList<MixerEntry> = arrayListOf()
        p.endpointVideoRequests!!.forEachIndexed { endpoint, mutableMap ->
            val cache = maxSavingByEndpoint[endpoint]
            mutableMap.entries.forEach { videoEntry ->
                val video = videoEntry.key
                val requests = videoEntry.value
                entries.add (MixerEntry(endpoint, video, requests, cache!!.second))
            }
        }
        entries.sortedByDescending { entry -> entry.score * entry.requests }

        val s = Solution()
        s.videoInCache = Array(p.videos) {BooleanArray (p.caches)}
        val fullCaches = IntArray(p.caches) { p.cacheCapacity }

        entries.forEach {
            val cache = maxSavingByEndpoint[it.endpoint]!!.first
            if (cache>=0) {
                if (fullCaches[cache] > p.videoSize!![it.video]) {
                    fullCaches[cache] -= p.videoSize!![it.video]
                    s.videoInCache!![it.video][cache] = true
                }
            }
        }
        return s
    }


    //Seleccionar los endpoints más valiosos: los que combinan mayores peticiones y más ahorros al datacenter
    //Requests
    //Por cada endpoint
    //Miro el vídeo más pedido
    //Selecciono el cache más cercano.

    //Veo el vídeo

    //We get a score for each combination video - cache
    //Score CV = A * sum en Es conectados a C de los A1 * requests Ev * tiempo ahorrado de E a C
    // - B * tamaño v


}

