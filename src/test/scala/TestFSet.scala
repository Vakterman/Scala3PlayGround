import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.*
import seroglazov.org.FTest._

class TestFSet extends AnyFunSuite {
    test("FTest contains item") {
        val fset = FSet[Int](3)
        assert(fset.contains(3))
    }
}
