/**
 * Created by jesus on 21/02/2017.
 */

data class CheckResult (var ok : Boolean, var description: String)

class SolutionChecker {
    fun checkSolution (p: Problem, s: Solution) : CheckResult {
        //check slices at most p.maxCells
        //check all slices have at least min ingredients
        //check slices do not overlap (there can be cells in no slice)
        return CheckResult (false, "Always wrong")
    }
}