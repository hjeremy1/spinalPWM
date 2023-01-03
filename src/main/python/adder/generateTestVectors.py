from bs4 import BeautifulSoup
import ast

# TODO as command arg and customizable
DATA_WIDTHS = [4, 4]

FILENAME = "test"

def convertIntToBinary(n):
    return "{0:04b}".format(n)

with open("results.xml", "r") as f:
    data = f.read()

Bs_data = BeautifulSoup(data, "xml")
test_cases = Bs_data.find_all("testcase")

with open("{}.tv".format(FILENAME), "a") as f:
    for test_case in test_cases:
        input = ast.literal_eval(test_case["input"])

        index = 0
        testvector = "_".join(map(convertIntToBinary, input.values()))
        f.write(testvector)
        f.write("\n")
            


