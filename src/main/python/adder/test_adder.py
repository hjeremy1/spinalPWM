# This file is public domain, it can be freely copied without restrictions.
# SPDX-License-Identifier: CC0-1.0
# Simple tests for an adder module
import cocotb
from cocotb.triggers import Timer
from adder_model import adder_model
import random
from cocotb.regression import TestFactory

"""
@cocotb.test()
async def adder_basic_test(dut):
    #Test for 5 + 10

    A = 5
    B = 10

    dut.A.value = A
    dut.B.value = B

    await Timer(2, units='ns')

    assert dut.X.value == adder_model(A, B), f"Adder result is incorrect: {dut.X.value} != 15"


@cocotb.test()
async def adder_randomised_test(dut):
    #est for adding 2 random numbers multiple times

    for i in range(10):

        A = random.randint(0, 15)
        B = random.randint(0, 15)

        dut.A.value = A
        dut.B.value = B

        await Timer(2, units='ns')

        assert dut.X.value == adder_model(A, B), "Randomised test failed with: {A} + {B} = {X}".format(
            A=dut.A.value, B=dut.B.value, X=dut.X.value)
"""

async def run_test(dut, a, b):
    dut.A.value = a
    dut.B.value = b

    await Timer(2, units="ns")

    assert dut.X.value == adder_model(a, b), "Randomised test failed with: {a} + {b} = {x}".format(
            a=dut.A.value, b=dut.B.value, x=dut.X.value)

adder_test_factory = TestFactory(run_test)
adder_test_factory.add_option("a", [1, 2, 3])
adder_test_factory.add_option("b", [4, 5, 6])
adder_test_factory.generate_tests()