package myPWM
import org.scalatest.funsuite.AnyFunSuite
import spinal.core.sim.SimCompiled
import spinal.core._
import spinal.sim._
import spinal.core.sim._


class PWMControllerTester extends AnyFunSuite { 
    var dut: SimCompiled[PWMController] = null

    test("compile") {
        dut = SimConfig.withWave.withConfig(
            SpinalConfig(defaultClockDomainFrequency = ClockDomain.FixedFrequency(490 Hz))
        ).workspacePath("waves").compile(PWMController())
    }

    test("controller test one shot") {
        dut.doSim { dut =>
            dut.clockDomain.forkStimulus(period = 41)

            // starting state
            dut.io.enablePWM #= false
            dut.io.multiShotRepeat #= 5
            dut.clockDomain.waitRisingEdge()

            //pwm enabled and compareValue is true
            dut.io.enablePWM #= true
            dut.io.cmpValue #= true
            dut.io.pwmMode #= PWMMode.oneShot
            dut.io.cycleFinished #= false
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === true)

            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === true)
        }
    }

    test("controller test multi shot") {
        dut.doSim { dut =>
            dut.clockDomain.forkStimulus(period = 41)
            
            // starting state
            dut.io.enablePWM #= false
            dut.io.multiShotRepeat #= 5
            dut.clockDomain.waitRisingEdge()

            //pwm enabled and compareValue is true
            dut.io.enablePWM #= true
            dut.io.cmpValue #= true
            dut.io.pwmMode #= PWMMode.multiShot
            dut.io.cycleFinished #= true
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === true)

            dut.io.cmpValue #= false
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === false)

            dut.io.cmpValue #= false
            dut.io.enablePWM #= false
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === false)

            dut.io.cmpValue #= true
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === false)

            dut.io.enablePWM #= true
            dut.io.cmpValue #= true
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === true)

            // limit reached, outPWM should be 0
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === false)
        }
    }

    test("controller test free running") {
        dut.doSim { dut =>
            dut.clockDomain.forkStimulus(period = 41)
            //pwm enabled and compareValue is true
            dut.io.enablePWM #= true
            dut.io.cmpValue #= true
            dut.io.pwmMode #= PWMMode.freeRunning
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === true)

            //pwm enabled and compareValue is false
            dut.io.cmpValue #= false
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === false)

            //pwm disabled and compareValue is true
            dut.io.enablePWM #= false
            dut.io.cmpValue #= true
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === false)

            //pwm disabled and compareValue is false
            dut.io.enablePWM #= false
            dut.io.cmpValue #= false
            dut.clockDomain.waitRisingEdge()
            assert(dut.io.outPWM.toBoolean === false)

        }
    }
}