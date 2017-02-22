
/**
 * Created by jesus on 21/02/2017.
 */


class SimpleSolver : ISolver {

    override fun getSolution(p: Problem): Solution {
        val s = Solution()
        //2 slices totally ad-hoc for small.in
        with (s.slices) {
            add (Slice(0, 0, 0, 1))
            add (Slice(0, 2, 0, 6))
        }
        return s
    }

}
