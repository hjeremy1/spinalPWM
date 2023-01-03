package myPWM
import org.scalatest.funsuite.AnyFunSuite
import spinal.core.sim.SimCompiled
import spinal.core._
import spinal.sim._
import spinal.core.sim._

//MyTopLevel's testbench
object PWMCompareSim {
  def main(args: Array[String]) {
    val compiled = SimConfig.withWave.compile(new PWMCompare(8, 8))

    compiled.doSim("Compare Test", seed = 42) { dut => 
        dut.clockDomain.forkStimulus(period = 41)

        dut.io.dutyCycle #= 15
        dut.io.limit #= 20
        dut.io.timerValue #= 1
        dut.io.enable #= false
        dut.clockDomain.waitRisingEdge()

        dut.io.enable #= true
        dut.clockDomain.waitRisingEdge()
        assert(dut.io.cmpValue.toBoolean == true)

        dut.io.timerValue #= 14
        dut.clockDomain.waitRisingEdge()
        assert(dut.io.cmpValue.toBoolean == true)

        dut.io.timerValue #= 16
        dut.clockDomain.waitRisingEdge()
        assert(dut.io.cmpValue.toBoolean == false)

        //Change of Duty Value without disabling
        dut.io.dutyCycle #= 20
        dut.clockDomain.waitRisingEdge()
        assert(dut.io.cmpValue.toBoolean == false)

        dut.io.enable #= false
        dut.clockDomain.waitRisingEdge()
        dut.io.enable #= true
        dut.clockDomain.waitRisingEdge()
        assert(dut.io.cmpValue.toBoolean == true)        
    }
  }
}
