/*
 *  Copyright (C) 2020  Claire Wolf <claire@yosyshq.com>
 *
 *  Permission to use, copy, modify, and/or distribute this software for any
 *  purpose with or without fee is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

// Example usage:
// iverilog -s testbench -o bitcnt_tb bitcnt_tb.v bitcnt.v && vvp -N bitcnt_tb

module testbench;
	reg  [7:0] io_a;
    reg  [7:0] io_b;
	wire [7:0] io_result;
    reg [13:0] testvectors [0:8];
    reg clk;
    reg reset;
    integer i;

    Adder uut (
		.io_a  (io_a ),
		.io_b  (io_b ),
		.io_result (io_result),
        .clk(clk),
        .reset(reset)
	);

    task test;
		input [7:0] a;
		input [7:0] b;
		input [7:0] result;
		begin
            reset = 0;
			io_a = a;
			io_b = b;
            clk = 1;
            #10 clk = 0;
            if (result === io_result) begin
				$display("OK a=%x b=%x result(ref)=result(uut)=%x", a, b, result);
			end else begin
				$display("ERROR a=%x b=%x result(ref)=%x result(uut)=%x", a, b, result, io_result);
				$stop;
			end
            $display("%x, %x, %x", io_a, io_b, result);
		end
	endtask

    initial begin
        //Filled in By Template Files based on test vectors
        test(1, 5, 6);
        test(2, 5, 7);
        $display("PASS");
    end

    
endmodule
