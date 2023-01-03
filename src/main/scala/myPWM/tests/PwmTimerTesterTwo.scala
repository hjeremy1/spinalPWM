package myPWM
import org.scalatest.funsuite.AnyFunSuite
import spinal.core.sim.SimCompiled
import spinal.core._
import spinal.sim._
import spinal.core.sim._


//MyTopLevel's testbench
object PWMTimerSim {
  def main(args: Array[String]) {
    val compiled = SimConfig.withWave.compile(new PWMTImer(8, 8))

    compiled.doSim("Timer Test") { dut => 
        dut.clockDomain.forkStimulus(period = 41)
        dut.io.enable #= false
        dut.io.divideBy #= 2
        dut.io.limit #= 5
        dut.clockDomain.waitRisingEdge()
        dut.io.enable #= true
        dut.clockDomain.waitRisingEdge()

        // reset clock
        dut.io.clear #= true
        dut.clockDomain.waitRisingEdge(2)
        assert(dut.io.value.toInt == 0) // ensure is timer is reset
        dut.io.clear #= false
        dut.clockDomain.waitRisingEdge()
        
        // normal case
        dut.clockDomain.waitRisingEdge(2)
        assert(dut.io.value.toInt == 1)
        dut.clockDomain.waitRisingEdge(2)
        assert(dut.io.value.toInt == 2)

        // hit limit
        dut.clockDomain.waitRisingEdge(6)
        assert(dut.io.value.toInt == 5)

        // reset case
        dut.clockDomain.waitRisingEdge(2)
        assert(dut.io.value.toInt == 0)

        // reset by disabling 
        dut.clockDomain.waitRisingEdge(4)
        assert(dut.io.value.toInt == 2)
        dut.io.enable #= false
        dut.clockDomain.waitRisingEdge(2)
        assert(dut.io.value.toInt == 0)
        dut.clockDomain.waitRisingEdge(2)
        assert(dut.io.value.toInt == 0)

        // re-enable 
        dut.io.enable #= true
        dut.clockDomain.waitRisingEdge()

        // start counting again
        dut.clockDomain.waitRisingEdge(2)
        assert(dut.io.value.toInt == 1)

        // changing of divideBy without disabling
        dut.io.divideBy #= 3
        dut.clockDomain.waitRisingEdge(4)
        assert(dut.io.value.toInt == 3)
        
        // changing of divideBy with disabling
        dut.io.divideBy #= 3
        dut.io.enable #= false
        dut.clockDomain.waitRisingEdge()
        assert(dut.io.value.toInt == 0)
        dut.io.enable #= true
        dut.clockDomain.waitRisingEdge()
        
        // sample with divideBy 3
        dut.clockDomain.waitRisingEdge(6)
        assert(dut.io.value.toInt == 2)
    }

  }
}
