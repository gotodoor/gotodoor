#!/usr/bin/python

import json
import sys
import urllib
import os
import pprint
import yaml

usage = """
Usage: ./tweet_search.py 'keyword'
e.g ./tweet_search.py pythonforbeginners

Use "+" to replace whitespace"
e.g ./tweet_search.py "python+for+beginners"
"""

# Check that the user puts in an argument, else print the usage variable, then quit.
if len(sys.argv)!=2:
    print (usage)
    sys.exit(0)
inputfile = open('./input.txt')
filer = open ('./output.txt','w+');
for line in inputfile:
	pinCode = line
	screen = sys.argv[1]

	# Open the twitter search URL the result will be shown in json format
	url = urllib.urlopen("https://api.mapmyindia.com/v3?fun=geocode&lic_key=dt25dv1l4hl56hn1ip16vqrv7qrmmhbb&q="+pinCode)
	data = yaml.safe_load(url)
	print pinCode
	print >> filer, data
inputfile.close()
