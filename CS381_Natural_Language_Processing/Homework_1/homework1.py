from __future__ import division
import math


brownTrainUnigramDictionary = {}
brownTrainBigramDictionary = {}
brownTrainBigramSmoothingDictionary = {}
countsTokens = {}
brownTrainTotalNumberOfTokens = 0

countsDictionary = {}

# This function add <s> to the beginning of a line
# and adds </s> to the end of the line
def padText(inputFile, outputFile):
	inputFileString = open(inputFile, "r").read().splitlines()
	outputFileString = open(outputFile, "w+")

	for i in inputFileString:
		i = i.lower()
		i = "<s> " + i + " </s>"
		outputFileString.write(i + "\n")

# This function replaces the words seen once with <unk>
def replaceUnknown(inputFile, outputFile):
	
	inputFileString = open(inputFile, "r").read().splitlines()
	outputFileString = open(outputFile, "w+")

	for i in inputFileString:
		string = i.split()
		for j in string:
			if countsDictionary.has_key(j) == False:
				countsDictionary[j] = 0
			else:
				countsDictionary[j] += 1
	countsDictionary["<unk>"] = 0
	count = 0
	for i in inputFileString:
		string = i.split()
		newString = ""
		for j in string:
			if countsDictionary.get(j) == 0:
				newString = newString + "<unk> "
				countsDictionary["<unk>"] += 1
			elif countsDictionary.get(j) == 0 and count == len(string):
				newString = newString + "<unk>"
				countsDictionary["<unk>"] += 1
			else:
				newString = newString + j + " "
			count += 1
		count = 0
		newString = newString + "\n"
		outputFileString.write(newString)

	outputFileString.close()

# Calls to replace words seen once. makes new file with it replaced 
# replaceUnknown("imLin2.txt", "imLin3.txt")
# replaceUnknown("brown-train-temp.txt", "brown-train-after-preprocessing.txt")


# Creates a file of unique words with probablities 
def computeUnigram(inputFile, outputFile):
	inputFileString = open(inputFile, "r").read().splitlines()
	outputFileString = open(outputFile, "w+")

	totalNumberOfWords = 0
	
	for i in inputFileString:
		string = i.split()
		for j in string:
			totalNumberOfWords += 1

	# fileString = open(inputFile, "r").read().splitlines()

	myDictionary = {}

	for i in inputFileString:
		string = i.split()
		for j in string:
			if myDictionary.has_key(j) == False:
				myDictionary[j] = 1
			else:
				myDictionary[j] += 1
	
	for key in myDictionary:
		# outputFileString.write(key + " " + str(value/totalNumberOfWords) + "\n")
		# outputFileString.write("count of " + key + ": " + str(myDictionary[key]) + "\n" + "totalNumberOfWords: " + str(totalNumberOfWords) + "\n")
		answer = myDictionary[key]/totalNumberOfWords
		outputFileString.write(key + " " + str(answer) + "\n")
	


# Calls to create a file of a unigram model
# 
# computeUnigram("brown-train-after-preprocessing.txt", "unigram.txt")

# This creates a file of bigrams with their respective probabilities
# third parameter determines wheter or not to use add-one smoothing
# True proceeds with add-one smoothing
# False does not proceed with add-one smoothing
def computeBigram(inputFile, outputFile, addOne):
	numberOfUniqueWords = 0
	inputFileString = open(inputFile, "r").read().splitlines()
	outputFileString = open(outputFile, "w+")

	dictionaryForFirstWord = {}
	dictionaryForFirstAndSecond = {}

	if addOne:
		for i in inputFileString:
			string = i.split()
			for j in string:
				if dictionaryForFirstWord.has_key(j) == True:
					dictionaryForFirstWord[j] += 1
				else:
					dictionaryForFirstWord[j] = 1
					numberOfUniqueWords += 1

	else:
		for i in inputFileString:
			string = i.split()
			for j in string:
				if dictionaryForFirstWord.has_key(j):
					dictionaryForFirstWord[j] += 1
				else:
					dictionaryForFirstWord[j] = 1
	
	# inputFileString = open(inputFile, "r").read().splitlines()

	for i in inputFileString:
		string = i.split()
		stringIndex = len(i.split())
		for j in range(0,stringIndex-1):
			firstAndSecond = string[j] + " " + string[j+1]
			if dictionaryForFirstAndSecond.has_key(firstAndSecond) == False:
				dictionaryForFirstAndSecond[firstAndSecond] = 1
			else:
				dictionaryForFirstAndSecond[firstAndSecond] += 1

	if addOne:
		for key in dictionaryForFirstAndSecond:
			string = key.split()
			addVToFirstCount = dictionaryForFirstWord[string[0]] + numberOfUniqueWords
			probabilityOfFirstAndSecond = (dictionaryForFirstAndSecond[key] + 1)/(addVToFirstCount)
			# outputFileString.write("countOfFirstTwo: " + str(dictionaryForFirstAndSecond[key] + 1) + "\n" + "countOfFirst: " + str(dictionaryForFirstWord[string[0]]) + "\n" + "countOfV: " + str(numberOfUniqueWords) + "\n" + "CountOfVPlusFirst: " + str(addVToFirstCount) + "\n")
			# outputFileString.write("count(" + key + ")/count(" +string[0]+ "): " + str(probabilityOfFirstAndSecond) + "\n" + "\n")
			outputFileString.write(str(key) + " " + str(probabilityOfFirstAndSecond) + "\n")
		outputFileString.close()
	else:
		for key in dictionaryForFirstAndSecond:
			string = key.split()
			probabilityOfFirstAndSecond = dictionaryForFirstAndSecond[key]/dictionaryForFirstWord[string[0]]
			# outputFileString.write("countOfFirstTwo: " + str(dictionaryForFirstAndSecond[key]) + "\n" + "countOfFirst: " + str(dictionaryForFirstWord[string[0]]) + "\n")
			# outputFileString.write("count(" + key + ")/count(" +string[0]+ "): " + str(probabilityOfFirstAndSecond) + "\n" + "\n")
			outputFileString.write(str(key) + " " + str(probabilityOfFirstAndSecond) + "\n")
		outputFileString.close()


# function to find unique words in a file
def findUniqueType(inputFile, outputFile, isBigram):
	inputFileString = open(inputFile, "r").read().splitlines()
	outputFileString = open(outputFile, "w+")
	count = 0
	dictionary = {}
	if isBigram == True:
		for i in inputFileString:
			string = i.split()
			stringIndex = len(i.split())
			for j in range(0, stringIndex-1):
				firstAndSecond = string[j] + " " + string[j+1]
				if dictionary.has_key(firstAndSecond) == False:
					count += 1
					dictionary[firstAndSecond] = 1
	else:
		for i in inputFileString:
			string = i.split()		
			for j in string:
				if dictionary.has_key(j) == False:
					dictionary[j] = 1
					count += 1
				else:
					dictionary[j] += 1

	outputFileString.write(str(count))
	outputFileString.close()

# findUniqueType("brown-train-after-preprocessing.txt", "unique.txt")
# padText("imLin.txt", "imLin2.txt")
# replaceUnknown("imLin2.txt", "imLin3.txt")
# findUniqueType("imLin3.txt", "imLin4.txt")
# findUniqueType("imLin2.txt", "imLin3.txt")

def findTotalTokens(inputFile, outputFile, isBigram):
	inputFileString = open(inputFile, "r").read().splitlines()
	outputFileString = open(outputFile, "w+")
	totalNumberOfTokens = 0
	if isBigram:
		for i in inputFileString:
			string = i.split()
			stringIndex = len(i.split())
			for j in range(0, stringIndex-1):
				totalNumberOfTokens += 1
	else:
		for i in inputFileString:
			string = i.split()
			for j in string:
				totalNumberOfTokens += 1
	outputFileString.write(str(totalNumberOfTokens)) 


def computePercentageOfWordTokenTypeNotSeenBigram(inputFile, outputFile, totalNumber, isType):
	inputFileString = open(inputFile, "r").read().splitlines()
	outputFileString = open(outputFile, "w+")
	totalNumberString = open(totalNumber, "r").read()
	countNotInDictionary = 0

	if isType == True:
		unseenDictionary = {}
		for i in inputFileString:
			string = i.split()
			stringIndex = len(i.split())
			for j in range(0, stringIndex-1):
				firstAndSecond = string[j] + " " + string[j+1]
				if brownTrainBigramDictionary.has_key(firstAndSecond) == False and unseenDictionary.has_key(firstAndSecond) == False:
					countNotInDictionary += 1
					unseenDictionary[firstAndSecond] = 1
	else:
		for i in inputFileString:
			string = i.split()
			stringIndex = len(i.split())
			for j in range(0, stringIndex-1):
				firstAndSecond = string[j] + " " + string[j+1]
				if brownTrainBigramDictionary.has_key(firstAndSecond) == False:
					countNotInDictionary += 1
	

	answer = (countNotInDictionary / int(totalNumberString)) * 100 

	outputFileString.write(str(answer))
	

# This function determines the percentage of tokens or types not found in the test data.
# First parameter is meant for the test data
# Second meant for output
# Third is a file containing either number of type or number or tokens
# Fourth is the model used to build the list of unique words in the training data
# Fifth is meant for specifying if we are looking for type or token
# 		True is for type False is for token
def computePercentageOfWordTokenTypeNotSeen(inputFile, outputFile, totalNumber, languageModel, isType):
	# this file that contains test corpus for which we are looking for percentage of type/token not seen
	inputFileString = open(inputFile, "r").read().splitlines()
	# output of percentage
	outputFileString = open(outputFile, "w+")
	# the total numer of tokens/type in the given test corpus
	totalNumberFileString = open(totalNumber, "r").read()
	# the model 
	languageModelFileString = open(languageModel, "r").read().splitlines()

	total = int(totalNumberFileString)
	# count = 0
	countNotInDictionary = 0
	trainingDictionary = {}
	testDictionary = {}
	# build dictionary of training data out of unigram
	for i in languageModelFileString:
		string = i.split()
		if trainingDictionary.has_key(string[0]) == False:
			trainingDictionary[string[0]] = 1

	# If True we are looking for type
	if isType == True:
		# look at each line in test corpus
		for i in inputFileString:
			string = i.split()
			# look at unigram for each line
			for j in string:
				# if token doesn't exist in testDictionary make a key value pair
				if testDictionary.has_key(j) == False:
					testDictionary[j] = 1
		# Search through unique list of tokens from training data
		# See if unique words from test is in training
		for i in testDictionary:
			if trainingDictionary.has_key(i) == False:
				countNotInDictionary += 1
	#Look for tokens 
	else:
		for i in inputFileString:
			string = i.split()
			for j in string:
				# count += 1
				if trainingDictionary.has_key(j) == False:
					countNotInDictionary += 1
	answer = (countNotInDictionary/int(totalNumberFileString)) * 100
	outputFileString.write(str(answer))

def setExampleDictionary(inputFile):
	inputFileString = open(inputFile, "r").read().splitlines()
	for i in inputFileString:
		string = i.split()
		for j in string:
			if exampleDictionary.has_key(j) == False:
				exampleDictionary[j] = 1


def setBrownTrainUnigramDictionary(unigramFile):
	unigramFileString = open(unigramFile, "r").read().splitlines()
	for i in unigramFileString:
		string = i.split()
		for j in string:
			brownTrainUnigramDictionary[string[0]] = string[1]

def setBrownTrainBigramDictionary(bigramFile):
	bigramFileString = open(bigramFile, "r").read().splitlines()
	for i in bigramFileString:
		string = i.split()
		firstAndSecond = string[0] + " " + string[1]
		brownTrainBigramDictionary[firstAndSecond] = string[2]

def setBrownTrainBigramSmoothingDictionary(bigramSmoothingFile):
	bigramSmoothingFileString = open(bigramSmoothingFile, "r").read().splitlines()
	for i in bigramSmoothingFileString:
		string = i.split()
		firstAndSecond = string[0] + " " + string[1]
		brownTrainBigramSmoothingDictionary[firstAndSecond] = string[2]



def computeLogProb(outputFile, model, string, outputBigramProb):
	outputBigramProbString = open(outputBigramProb, "w+")
	outputFileString = open(outputFile, "w+")
	totalProbability = 1
	# unigram log probability
	if model == 1:
		outputBigramProbString.write("Unigram log probability: \n\n")
		tokens = string.split()
		stringIndex = len(string.split())
		for i in range(1, stringIndex):
			if brownTrainUnigramDictionary.has_key(tokens[i]) == True:
				outputBigramProbString.write("p(" + tokens[i] + ") = " + str(brownTrainUnigramDictionary[tokens[i]]) + "\n")
				totalProbability *= float(brownTrainUnigramDictionary[tokens[i]])
				outputBigramProbString.write("Total probability = " + str(totalProbability) + "\n\n")
			else:
				outputBigramProbString.write("p(" + tokens[i] + ") = " + str(brownTrainUnigramDictionary["<unk>"]) + "\n" + " (used p(<unk>)) \n")
				totalProbability *= float(brownTrainUnigramDictionary["<unk>"])
				outputBigramProbString.write("Total probability = " + str(totalProbability) + "\n\n")
	# bigram log probability
	elif model == 2:
		outputBigramProbString.write("Bigram log probability: \n\n")
		tokens = string.split()
		stringIndex = len(string.split())
		for i in range(0, stringIndex-1):
			firstAndSecond = tokens[i] + " " + tokens[i+1]
			# if the bigram exist use that probability
			if brownTrainBigramDictionary.has_key(firstAndSecond) == True:
				outputBigramProbString.write("p(" + firstAndSecond + ") = " + str(brownTrainBigramDictionary[firstAndSecond]) + "\n")
				totalProbability *= float(brownTrainBigramDictionary[firstAndSecond])
				outputBigramProbString.write("Total probability = " + str(totalProbability) + "\n\n")
			else: 
				# brownTrainUnigramDictionary.has_key(tokens[i]) == True and brownTrainUnigramDictionary.has_key(tokens[i+1]) == True:
				outputBigramProbString.write("p(" + firstAndSecond + ") = " + "0")
				totalProbability *= 0

	# bigram smoothing log probability
	else:
		outputBigramProbString.write("Bigram smoothing log probability: \n\n")
		tokens = string.split()
		stringIndex = len(string.split())
		for i in range(0, stringIndex-1):
			firstAndSecond = tokens[i] + " " + tokens[i+1]
			if brownTrainBigramSmoothingDictionary.has_key(firstAndSecond) == True:
				outputBigramProbString.write("p(" + firstAndSecond + ") = " + str(brownTrainBigramSmoothingDictionary[firstAndSecond]) + "\n")
				totalProbability *= float(brownTrainBigramSmoothingDictionary[firstAndSecond])
				outputBigramProbString.write("totalProbability = " + str(totalProbability) + "\n\n")
			else: 
				# brownTrainUnigramDictionary.has_key(tokens[i]) == True and brownTrainUnigramDictionary.has_key(tokens[i+1]) == True:
				outputBigramProbString.write("p(" + firstAndSecond + ") = " + "0")
				countOfFirstWord = countsDictionary[tokens[i]]
				totalProbability *= (1/( countOfFirstWord + len(brownTrainUnigramDictionary)))
	# outputing to file 
	if totalProbability == 0:
		outputFileString.write("undefined")
		outputBigramProbString.write("this sentence is undefined " + "\n")
	else:
		outputFileString.write(str(math.log(totalProbability, 2)))
		outputBigramProbString.write("Total probability for sentence = " + str(totalProbability) + " log probability is " +  str(math.log(totalProbability, 2)))


def replaceUnseenWords(inputFile, outputFile):
	inputFileString = open(inputFile, "r")
	outputFileString = open(outputFile, "w+")

	for i in inputFileString:
		string = i.split()
		newString = ""
		for j in string:
			if brownTrainUnigramDictionary.has_key(j) == False:
				newString = newString + "<unk>"
			else:
				newString = newString + j
			if j != "</s>":
				newString = newString + " "
			else:
				newString = newString + "\n"
		outputFileString.write(newString)

def computePerplexityForSentence(logProb, outputFile, testString):
	logString = open(logProb, "r").read()
	outputFileString = open(outputFile, "w+")
	if logString == "undefined":
		outputFileString.write("undefined")
		return
	log = float(logString)
	count = 0

	for i in testString.split():
		# string = i.split()
			count += 1	
	perplexity = math.pow(2, -(log/count))

	outputFileString.write(str(perplexity))

def computePerplexityForCorpus(inputFile, outputFile, model):
	inputFileString = open(inputFile, "r").read().splitlines()
	outputFileString = open(outputFile, "w+")
	M = 0
	logProbOfCorpus = 0
	for i in inputFileString:
		computeLogProb("log-prob.txt", model, i, "Nothing.txt")
		logPString = open("log-prob.txt", "r").read()
		string = i.split()
		for j in string:
			M += 1
		if logPString == "undefined":
			continue
		logP = float(logPString)
		logProbOfCorpus += logP
	L = logProbOfCorpus/M 
	perplexity = math.pow(2, -(L))
	outputFileString.write(str(perplexity))

def setBrownTrainTotalTokens(inputFile):
	inputFileString = open(inputFile, "r").read()
	brownTrainTotalNumberOfTokens = int(inputFileString)


# original txt files are converted to padded
padText("brown-train.txt", "brown-train-padded.txt")
padText("brown-test.txt", "brown-test-padded.txt")
padText("learner-test.txt", "learner-test-padded.txt")


# replace words seen once to <unk>
replaceUnknown("brown-train-padded.txt", "brown-train-processed.txt")
computeUnigram("brown-train-processed.txt", "brown-train-unigram-with-unk.txt")
computeBigram("brown-train-processed.txt", "brown-train-bigram.txt", False)
computeBigram("brown-train-processed.txt", "brown-train-bigram-smoothing.txt", True)

# Set Dictionary 
setBrownTrainUnigramDictionary("brown-train-unigram-with-unk.txt")
setBrownTrainBigramDictionary("brown-train-bigram.txt")
setBrownTrainBigramSmoothingDictionary("brown-train-bigram-smoothing.txt")


# Answers Question 1
findUniqueType("brown-train-processed.txt", "brown-train-types.txt", False)


# Answers Question 2
findTotalTokens("brown-train-processed.txt", "brown-train-tokens.txt", False)


# Answers Question 3
computeUnigram("brown-train-padded.txt", "brown-train-unigram-no-unk.txt")

# brown-test
findTotalTokens("brown-test-padded.txt", "total-brown-test-tokens.txt", False)
computePercentageOfWordTokenTypeNotSeen("brown-test-padded.txt", "brown-test-percent-tokens-not-seen.txt", "total-brown-test-tokens.txt", "brown-train-unigram-no-unk.txt", False)
findUniqueType("brown-test-padded.txt", "total-brown-test-types.txt", False)
computePercentageOfWordTokenTypeNotSeen("brown-test-padded.txt", "brown-test-percent-type-not-seen.txt", "total-brown-test-types.txt", "brown-train-unigram-no-unk.txt", True)

# learner-test
findTotalTokens("learner-test-padded.txt", "total-learner-test-tokens.txt", False)
computePercentageOfWordTokenTypeNotSeen("learner-test-padded.txt", "learner-test-percent-tokens-not-seen.txt", "total-learner-test-tokens.txt", "brown-train-unigram-no-unk.txt", False)
findUniqueType("learner-test-padded.txt", "total-learner-test-types.txt", False)
computePercentageOfWordTokenTypeNotSeen("learner-test-padded.txt", "learner-test-percent-type-not-seen.txt", "total-learner-test-types.txt", "brown-train-unigram-no-unk.txt", True)



# Answer to Question 4

# brown-test
replaceUnseenWords("brown-test-padded.txt", "brown-test-with-unk.txt")
findTotalTokens("brown-test-with-unk.txt", "total-brown-test-tokens-bigrams.txt", True)
findUniqueType("brown-test-with-unk.txt", "total-brown-test-types-bigrams.txt", True)
computePercentageOfWordTokenTypeNotSeenBigram("brown-test-with-unk.txt", "brown-test-percentage-type-not-seen-bigram.txt", "total-brown-test-types-bigrams.txt", True)
computePercentageOfWordTokenTypeNotSeenBigram("brown-test-with-unk.txt", "brown-test-percentage-tokens-not-seen-bigram.txt", "total-brown-test-tokens-bigrams.txt", False)

# learner-test
replaceUnseenWords("learner-test-padded.txt", "learner-test-with-unk.txt")
findTotalTokens("learner-test-with-unk.txt", "total-learner-test-tokens-bigram.txt", True)
findUniqueType("learner-test-with-unk.txt", "total-learner-test-types-bigram.txt", True)
computePercentageOfWordTokenTypeNotSeenBigram("learner-test-with-unk.txt", "learner-test-percent-type-not-seen-bigram.txt", "total-learner-test-types-bigram.txt", True)
computePercentageOfWordTokenTypeNotSeenBigram("learner-test-with-unk.txt", "learner-test-percent-tokens-not-seen-bigram.txt", "total-learner-test-tokens-bigram.txt", False)


# Answer to question 5

# Set total token for brown train
setBrownTrainTotalTokens("brown-train-tokens.txt")

# sentence 1 = he was laughed off the screen .
sentence1 = "<s> he was laughed off the screen . </s>"
# sentence 2 = "there was no compulsion behind them ."
sentence2 = "<s> there was no <unk> behind them . </s>"
# sentence 3 = "I look forward to hearing your reply."
sentence3 = "<s> i look forward to hearing your reply . </s>"


computeLogProb("sentence1-unigram-log-probability.txt", 1, sentence1, "s1ulp.txt")
computeLogProb("sentence2-unigram-log-probability.txt", 1, sentence2, "s2ulp.txt")
computeLogProb("sentence3-unigram-log-probability.txt", 1, sentence3, "s3ulp.txt")

computeLogProb("sentence1-bigram-log-probability.txt", 2, sentence1, "s1blp.txt")
computeLogProb("sentence2-bigram-log-probability.txt", 2, sentence2, "s2blp.txt")
computeLogProb("sentence3-bigram-log-probability.txt", 2, sentence3, "s3blp.txt")

computeLogProb("sentence1-bigram-smoothing-log-probability.txt", 3, sentence1, "s1bslp.txt")
computeLogProb("sentence2-bigram-smoothing-log-probability.txt", 3, sentence2, "s2bslp.txt")
computeLogProb("sentence3-bigram-smoothing-log-probability.txt", 3, sentence3, "s3bslp.txt")

# Answer to 6

computePerplexityForSentence("sentence1-unigram-log-probability.txt", "sentence1-unigram-perplexity.txt", sentence1)
computePerplexityForSentence("sentence2-unigram-log-probability.txt", "sentence2-unigram-perplexity.txt", sentence2)
computePerplexityForSentence("sentence3-unigram-log-probability.txt", "sentence3-unigram-perplexity.txt", sentence3)

computePerplexityForSentence("sentence1-bigram-log-probability.txt", "sentence1-bigram-perplexity.txt", sentence1)
computePerplexityForSentence("sentence2-bigram-log-probability.txt", "sentence2-bigram-perplexity.txt", sentence2)
computePerplexityForSentence("sentence3-bigram-log-probability.txt", "sentence3-bigram-perplexity.txt", sentence3)

computePerplexityForSentence("sentence1-bigram-smoothing-log-probability.txt", "sentence1-bigram-smoothing-perplexity.txt", sentence1)
computePerplexityForSentence("sentence2-bigram-smoothing-log-probability.txt", "sentence2-bigram-smoothing-perplexity.txt", sentence2)
computePerplexityForSentence("sentence3-bigram-smoothing-log-probability.txt", "sentence3-bigram-smoothing-perplexity.txt", sentence3)

# Answer to 7
computePerplexityForCorpus("brown-test-with-unk.txt", "brown-test-unigram-perplexity.txt", 1)
computePerplexityForCorpus("brown-test-with-unk.txt", "brown-test-bigram-perplexity.txt", 2)
computePerplexityForCorpus("brown-test-with-unk.txt", "brown-test-bigram-smoothing-perplexity.txt", 3)

computePerplexityForCorpus("learner-test-with-unk.txt", "learner-test-unigram-perplexity.txt", 1)
computePerplexityForCorpus("learner-test-with-unk.txt", "learner-test-bigram-perplexity.txt", 2)
computePerplexityForCorpus("learner-test-with-unk.txt", "learner-test-bigram-smoothing-perplexity.txt", 3)




