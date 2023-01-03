package myPWM
import spinal.lib.misc.Timer
import spinal.lib.misc.Prescaler
import spinal.core._
import spinal.lib._


object PWMMode extends SpinalEnum {
    val oneShot, multiShot, freeRunning = newElement()
}

case class PWMController() extends Component {
    val io = new Bundle {
        val enablePWM = in Bool()
        val cmpValue = in Bool()
        val pwmMode = in(PWMMode())
        val multiShotRepeat = in UInt(8 bits)
        val cycleFinished = in Bool()

        val outPWM = out Bool()
        val pwmModeFinished = out Bool()
    }

    val cycle = Reg(UInt(8 bits)).init(0)
    val repeat = RegNextWhen(io.multiShotRepeat, !io.enablePWM)
    val enable = Bool()
    enable := io.enablePWM

    switch(io.pwmMode) {
        is(PWMMode.multiShot) {
            //report(Seq("MODE: MULTISHOT"))
            when(io.cycleFinished && cycle < repeat) {
                cycle := cycle + 1
                enable := io.enablePWM
            } elsewhen(cycle === repeat) {
                enable := False
            }
        }

        is(PWMMode.oneShot) {
            enable := !io.cycleFinished
        }

        default {
            enable := io.enablePWM
        }
    }

    //Wie kann ich dies anders lösen, z.B. das irgendwie in dem switch zuzuweisen
    io.pwmModeFinished := io.pwmMode === PWMMode.oneShot && io.cycleFinished || io.pwmMode === PWMMode.freeRunning && cycle === repeat
    io.outPWM := enable && io.cmpValue
}
