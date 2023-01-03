package myPWM

import spinal.core.SpinalConfig
import spinal.core.sim.SimConfig
import spinal.core.ClockDomain
import spinal.core._

object PWMSimConfig {
    def apply() = SimConfig.withWave.withConfig(
        SpinalConfig(defaultClockDomainFrequency = ClockDomain.FixedFrequency(490 Hz))
    ).workspacePath("waves")
}
