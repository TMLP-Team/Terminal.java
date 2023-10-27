import os
os.chdir(os.path.abspath(os.path.dirname(__file__)))#解析进入当前目录

def transf(x):
	return "\"" + str(x).replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\""

if os.path.isfile("input.txt"):
	with open("input.txt", "r", encoding = "utf-8") as f:
		content = f.read()
	with open("output.txt", "w", encoding = "utf-8") as f:
		f.write(transf(content))
else:
	with open("output.txt", "w", encoding = "utf-8") as f:
		f.write(transf(input()))