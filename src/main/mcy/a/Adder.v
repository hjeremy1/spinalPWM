// Generator : SpinalHDL v1.7.3    git head : aeaeece704fe43c766e0d36a93f2ecbb8a9f2003
// Component : Adder
// Git hash  : 8e8b22f6ec3191f0378aeced536ffcd14739dd33

`timescale 1ns/1ps

module Adder (
  input      [7:0]    io_a,
  input      [7:0]    io_b,
  output     [7:0]    io_result,
  input               clk,
  input               reset
);

  reg        [7:0]    _zz_io_result;

  assign io_result = _zz_io_result;
  always @(posedge clk or posedge reset) begin
    if(reset) begin
      _zz_io_result <= 8'h0;
    end else begin
      _zz_io_result <= (io_a + io_b);
    end
  end


endmodule
