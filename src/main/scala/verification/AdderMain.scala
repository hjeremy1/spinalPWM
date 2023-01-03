package verification

import spinal.core._

//Run this main to generate the RTL
object AdderMain{
  def main(args: Array[String]) {
    SpinalConfig(targetDirectory = "rtl").generateVerilog(Adder())
  }
}
