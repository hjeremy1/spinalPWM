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

module miter (
	input [7:0] ref_io_a,
	input [7:0] ref_io_b,
	input [7:0] uut_io_a,
	input [7:0] uut_io_b,
	input clk,
	input reset,
);
	wire [7:0] ref_io_result;
	wire [7:0] uut_io_result;

	Adder ref (
		.mutsel    (1'b 0),
		.io_a  (ref_io_a),
		.io_b  (ref_io_b),
		.io_result (ref_io_result),
		.clk(clk),
		.reset(reset)
	);

	Adder uut (
		.mutsel    (1'b 1),
		.io_a  (uut_io_a),
		.io_b  (uut_io_b),
		.io_result (uut_io_result),
		.clk(clk),
		.reset(reset)
	);

	always @* begin
		assume (ref_io_a[7:0] == uut_io_a[7:0]);
		assume (ref_io_b[7:0] == uut_io_b[7:0]);
		assert (ref_io_result == uut_io_result);
	end
endmodule
