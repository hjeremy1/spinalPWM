package myPWM
import spinal.lib.misc.Timer
import spinal.lib.misc.Prescaler
import spinal.core._
import spinal.lib._

/**
  * TODO: 
    - wie mit floating point umgegangen wird, z.B.bei einer Division, damit dutyCycle skalierend berechnet werden kann
    - In vielen Klassen sind jetzt enable mit dabei. Ist das dann beim PWMController enable redundant ? Später wahrscheinlich
    irgendwo Zentral das enable gemanaged. Und wenn das enable False ist dann ist compareValue ja automatisch disabled (wenn zu cmpValue noch die Bedingung
    ' && io.enable' dazu kommt, dadurch muss der controller dies nicht entscheiden)
  *
  * @param dutyCycleWidth
  * @param limitWidth
  */
case class PWMCompare(dutyCycleWidth : Int, limitWidth : Int) extends Component {
    val io = new Bundle {
        val dutyCycle = in UInt(dutyCycleWidth bits) 
        val limit = in UInt(limitWidth bits) 
        val timerValue = in UInt(limitWidth bits)
        val enable = in Bool()

        val cmpValue = out Bool()
    }

    val dutyCycle = RegNextWhen(io.dutyCycle, !io.enable)
    io.cmpValue := io.timerValue < dutyCycle
}
