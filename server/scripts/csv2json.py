#!/usr/bin/python3

import sys
import csv
import json

if len(sys.argv) != 3:
  raise ValueError("Error need 2 args, got "+ str(len(sys.argv)-1))

filetoconvert=sys.argv[1]
print ("File to convert:",filetoconvert)
outputfile=sys.argv[2]
print ("Output file:", outputfile)


columnheadings=("Version Code", "Review Submit Millis Since Epoch", "Star Rating", "Review Text")
csvfile = open (filetoconvert, 'r', encoding='utf-16')
jsonfile = open (outputfile, 'w')

jsonfile.write('[')
reader = csv.DictReader( csvfile)
comma=''
for row in reader:
  jsonfile.write(comma)
  comma=','
  json.dump(row, jsonfile)
  jsonfile.write('\n')
jsonfile.write(']')


 
