#!/usr/bin/python
from collections import defaultdict
import operator
#from sys import argv

#script, filename1, filename = argv

file=open("output.csv","r+")
target = open("keywords.csv", 'w')

wordcount= defaultdict(int)


for word in file.read().split():
       wordcount[word] += 1

for i in wordcount:
	print(i)

sorted_x = sorted(wordcount.items(), key=operator.itemgetter(1), reverse=True)

for k, v in sorted_x:
    #print k, v
    target.write(k+" : %d" % v)
    target.write("\n")



