package verification

import spinal.sim._
import spinal.core._
import spinal.core.sim._

import scala.util.Random


case class Adder() extends Component {
    val io = new Bundle {
        val a, b= in UInt (8 bits)
        val result = out UInt (8 bits)
    }
    io.result := RegNext(io.a + io.b) init(0)
}

