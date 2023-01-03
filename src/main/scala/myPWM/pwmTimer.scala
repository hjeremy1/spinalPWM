package myPWM
import spinal.lib.misc.Timer
import spinal.lib.misc.Prescaler
import spinal.core._
import spinal.lib._

case class PWMTImer(timerWidth: Int, prescalerWidth : Int) extends Component {
    val io = new Bundle {
        val divideBy = in UInt (prescalerWidth bits)
        val limit = in UInt (timerWidth bits)
        val clear = in Bool()
        val enable = in Bool()

        val value = out UInt (timerWidth bits)
        val full = out Bool()         
    }
    
    // offset by 1. with limit = 1 we'd have divideBy 2 (1 step to reset is needed)
    //val divideByReg = Reg(UInt(prescalerWidth bits)).init(io.divideBy - 1).allowUnsetRegToAvoidLatch soll die Benutzung von dem vermieden werden?
    val divideByReg = RegNextWhen(io.divideBy - 1, !io.enable)

    val prescaler = Prescaler(prescalerWidth)
    val timer = Timer(timerWidth)
    val a = RegNextWhen(io.divideBy, !io.enable)
    
    prescaler.io.limit := divideByReg
    prescaler.io.clear := io.clear || !io.enable

    timer.io.tick := prescaler.io.overflow && io.enable
    timer.io.limit := io.limit
    timer.io.clear := io.clear || !io.enable

    when(timer.io.full) {
        timer.io.clear := True
    } 

    when(io.enable) {
        io.value := timer.io.value
        io.full := timer.io.full
    } otherwise {
        io.value := 0
        io.full := False
    }
}
