from __future__ import division

# 1.2

# main dictionary that initialized to keep count of number of lines (total documents)
mainDictionary = {"totalAmountOfLabels": 0}

file = open("corpus.txt", "r").read().splitlines()

# add count unique labels and total number of labels 
# count words for each class dont count repeated words in one line
# also count total number of tokens in each class 
for i in file:
	mainDictionary["totalAmountOfLabels"] += 1
	wordsInLine = i.split()
	label = wordsInLine[len(wordsInLine)-1]
	if mainDictionary.has_key(label):
		mainDictionary[label]["countOfLabel"] += 1
	else:
		mainDictionary[label] = {}
		mainDictionary[label]["countOfLabel"] = 1
		mainDictionary[label]["countOfTotal"] = 0
	end = len(wordsInLine) - 1
	spot = 0
	distinct = {}
	for j in wordsInLine:
		if spot == end:	
			break
		if mainDictionary[label].has_key(j):
			if distinct.has_key(j):
				spot +=1
				continue
			mainDictionary[label][j] += 1
			mainDictionary[label]["countOfTotal"] += 1
			distinct[j] = 0
		else:
			mainDictionary[label][j] = 1
			mainDictionary[label]["countOfTotal"] += 1
			distinct[j] = 0
		spot +=1


# create vocabulary 
# get all distinct words for smoothing
dictionaryOfAllWords = {}
for label in mainDictionary:
	# print label
	if label == "totalAmountOfLabels":
		continue
	for w in mainDictionary[label]:
		if dictionaryOfAllWords.has_key(w):
			dictionaryOfAllWords[w] += mainDictionary[label][w]
		else:
			dictionaryOfAllWords[w] = mainDictionary[label][w]

# adjust dictionary for smoothing
for label in mainDictionary:
	if label == "totalAmountOfLabels":
		continue
	mainDictionary["totalAmountOfLabels"] += 1
	mainDictionary[label]["countOfLabel"] += 1
	for item in dictionaryOfAllWords:
		if item == "countOfTotal" or item == "countOfLabel":
			continue
		if mainDictionary[label].has_key(item):
			mainDictionary[label][item] += 1
			mainDictionary[label]["countOfTotal"] += 1
		else:
			mainDictionary[label][item] = 1
			mainDictionary[label]["countOfTotal"] += 1

# compute probabilities 
for label in mainDictionary:
	if label == "totalAmountOfLabels":
		continue
	mainDictionary[label]["priorProbability"] = mainDictionary[label]["countOfLabel"] / mainDictionary["totalAmountOfLabels"]
	for j in mainDictionary[label]:
		if j == "countOfLabel" or j == "countOfTotal" or j == "priorProbability":
			continue
		temp = mainDictionary[label][j]
		numerator = temp
		denominator = mainDictionary[label]["countOfLabel"]
		mainDictionary[label][j] = numerator/denominator

result = open("movie-review.NB", "w+")

# output parameters to file
for label in mainDictionary:
	if label == "totalAmountOfLabels":
		continue	
	result.write("\n" + label + " priorProbability is " + str(mainDictionary[label]["priorProbability"]) + "\n")
	for word in mainDictionary[label]:
		if word == "countOfLabel" or word == "countOfTotal" or word == "priorProbability":
			continue
		result.write(word + " " + str(mainDictionary[label][word]) + "\n")


# test case with new document 
test = open("new-document.txt", "r").read().splitlines()
testDictionary = {}
for line in test:
	newDocument = line.split()
	for item in newDocument:
		for label in mainDictionary:
			if label == "totalAmountOfLabels":
				continue
			if mainDictionary[label].has_key(item) == False:
				break
			if testDictionary.has_key(label):
				testDictionary[label] *= mainDictionary[label][item]
			else:
				testDictionary[label] = mainDictionary[label]["priorProbability"]
				testDictionary[label] *= mainDictionary[label][item]

# out put answer to test case
output = open("1_2_Answer.txt", "w+")
for label in testDictionary:
	output.write(label + " " + str(testDictionary[label]) + "\n")
output.write(str(max(testDictionary, key=testDictionary.get)) + " is the answer")








