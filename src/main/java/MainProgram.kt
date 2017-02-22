
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.util.*

/**
 * Created by jesus on 21/02/2017.
 */

fun main(params: Array<String>) {
    val p = readProblem(File ("D:\\proj\\jdmuriel.com\\hashcode2017\\hc17practice\\small.in"))
    val s1 = SimpleSolver()
    val s = s1.getSolution (p)
    writeSolution (File ("D:\\proj\\jdmuriel.com\\hashcode2017\\hc17practice\\small.out"), s)

}

fun readProblem (f: File) : Problem {
    val sc = Scanner (BufferedInputStream(FileInputStream(f)));
    var p = Problem()
    p.rows = sc.nextInt();
    p.cols = sc.nextInt();
    p.minIng  = sc.nextInt();
    p.maxCells = sc.nextInt();
    // Read
    p.cells = Array(p.rows) { CharArray(p.cols)}
    (0..p.rows-1).forEach { i ->
        val row = sc.nextLine().toCharArray()
        row.forEachIndexed { j, c ->
            p.cells[i][j] = c
        }
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