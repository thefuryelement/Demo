import math
import pandas as pd
import os, sys
from dateutil.parser import parse
from dateutil.relativedelta import relativedelta
import matplotlib.pyplot as plt
from builtins import print


class TimeSeries():

    #-----------------------------------------------------------------------------------------------------------
    # MOVING AVERAGE CALCULATOR
    #-----------------------------------------------------------------------------------------------------------
    def calculateMovingAverage(self, movementValue, dataFile):
        '''
        Evluates the trend equation of the data and predicts the value for the specified time value.
        Args:
            movementValue:
            colList:

        Returns:

        '''
        timeVal, colList = zip(*dataFile)
        columnLength = len(colList)
        movementSumList =[]
        for colIter in range(0, columnLength):
            movementSum = 0
            try:
                for sumIter in range(0, movementValue):
                    movementSum = movementSum+colList[colIter+sumIter]
                movementSumList.append(movementSum)
            except IndexError:
                pass

        averageList = []
        for val in movementSumList:
            averageList.append(val/movementValue)
        with open("C:\\Users\\nitinpan\\Desktop\\movingAvgFile.txt", 'w') as f:
            for l in averageList:
                f.write(str(l))
                f.write("\n")

    #-----------------------------------------------------------------------------------------------------------
    # TREND CALCULATOR
    #-----------------------------------------------------------------------------------------------------------
    def trendEvaluator(self, numOfObservations, dataFile, predictionTimeVal):
        '''

        Args:
            numOfObservations:
            dataFile:
            predictionTimeVal:

        Returns:

        '''
        print("\ndef trendEvaluator(numOfObservations, dataFile, predictionTimeVal):")
        print("-----------------------------------------------------------------------------------------------------------")
        timeVal, colList = zip(*dataFile)
        print(timeVal[-1])
        xList = []
        if numOfObservations%2==0:
            subtractor = 2
            for i in range(0, numOfObservations):
                xList.append(-numOfObservations-1+subtractor)
                subtractor = subtractor+2
        else:
            centerIndex = math.ceil(numOfObservations/2)
            for x in range(0, numOfObservations):
                xList.append(-centerIndex+x+1)
        xSquare = [i**2 for i in xList]
        xy = [x*y for x, y in zip(xList, colList)]
        a = sum(colList)/numOfObservations
        b = sum(xy)/sum(xSquare)
        print("x = {} \n".format(xList))
        print("xsquare = {}\n".format(sum(xSquare)))
        print("xy = {}\n".format(sum(xy)))
        print("Trend equation is : y = {} + {}x\n".format(round(a, 3), round(b, 3)))
        for diff in range(1, predictionTimeVal+1):
            print("Predicted Value for {} is = {}\n".format(diff, round(a,3)+(round(b,3)*xList[-1]+diff)))

    #-----------------------------------------------------------------------------------------------------------
    # TCSI CALCULATOR
    #-----------------------------------------------------------------------------------------------------------
    def getTrendCyleSIPrediction(self, DataRecord, timeVariation, predictionVal):
        '''
        Generated the Trend Cycle Season Irregularities of the data.
        '''
    #    dataList = [10.2,12.4,14.8,15.0,11.2,14.3,18.4,18.0]
    #    print("\ndef getTCSI(DataRecord):")
    #    print("-----------------------------------------------------------------------------------------------------------")
        if len(DataRecord)<timeVariation:
            print("Data len doesn't match ")
            raise Warning
            exit()
        dataList = DataRecord
        t = []
        yt = []
        tsquare = []
        n = len(dataList)
        for timeLength in range(1, n+1):
            t.append(timeLength)
            yt.append(timeLength*dataList[timeLength-1])
            tsquare.append(timeLength**2)

        tSummation = sum(t)
        tSqSummation = sum([i**2 for i in t])
        ySummation = sum(dataList)
        ytSummation = sum(yt)
        b1Numerator = (n*ytSummation)-(tSummation*ySummation)
        b1Denominator = (n*tSqSummation)-(sum(t)**2)

        b1 = (b1Numerator)/(b1Denominator)
        b0 = sum(dataList)/n -b1*sum(t)/n

        T = [b0+b1*x for x in t]
        SeasonIrregulaties = [x/y for x,y in zip(dataList, T)]
        averagingFactors = []
        listOfaverages = []
    #    print("Sesonla list : ", SeasonIrregulaties)
        for i in range(0, timeVariation):
            #print("Calculating average for month ", i)
            currIndex = i
            currMonthTotal = 0
            countOfValues = 0
            while currIndex<len(SeasonIrregulaties):
                #print("Adding ",SeasonIrregulaties[currIndex])
                currMonthTotal+=SeasonIrregulaties[currIndex]
                countOfValues+=1
                currIndex+=timeVariation
            try:
                average = currMonthTotal/countOfValues
            except:
                average = 0
            listOfaverages.append(average)
    #    print("List of Averages ", listOfaverages)
        '''
        print(t, yt, tsquare)
        print(b0, b1, T)
        print(SeasonIrregulaties)
        '''
        monthArray = ['jan', 'feb', 'mar', 'apr', 'may','jun', 'jul', 'aug', 'sep', 'octo', 'nov', 'dec']
        predictedList = []
        for predictShift in range(len(DataRecord), len(DataRecord)+predictionVal):
    #        print(predictShift%12)
    #        print("evaluating for th emonth ", monthArray[predictShift%12])
            calculatedPrediction = (b0+b1*predictShift)*listOfaverages[predictShift%timeVariation]
            predictedList.append(calculatedPrediction)
    #        print(calculatedPrediction)
        return predictedList

    #-----------------------------------------------------------------------------------------------------------
    # ARIMA EVALUATOR
    #-----------------------------------------------------------------------------------------------------------
    def evaluateARIMA(self, dataList):
        '''

        Returns:

        '''
    #-----------------------------------------------------------------------------------------------------------
    # PLOT DATA
    #-----------------------------------------------------------------------------------------------------------
    def plotInfo(self, stockVal, rangeValue):
        '''

        Args:
            stockVal:
            rangeValue:

        Returns:

        '''
        y = stockVal
        x = rangeValue
    #    print(len(x), len(y))
        plt.scatter(x,y)
        plt.title("title")
        plt.ylabel("stockValues")
        plt.xlabel("Time Variable")
        plt.show()


    #CCAR= [58,62,68,64,66,66,64,66,70,66,64,65,63,52,46,49,54,58,62]
    #CCAR= [58,62,68,64,58,62]
    #TrendCyleSeasonalIrregularities(CCAR,12, 8)

    def filegenerator(self, inputFile, colname, predrange):
        try:
            inputcsv = pd.read_csv(inputFile, index_col = False)
            lastDate = parse(inputcsv.Date[-1:].values[0])
            print("Last date according to file is -> ", lastDate)
            forecastedOutput = self.getTrendCyleSIPrediction(inputcsv[colname].values, 12, predrange)
            location = os.getcwd()
            opDF = pd.DataFrame()
            dateList = []
            for values in range(0,predrange):
                lastDate = lastDate+relativedelta(months=1)
                dateList.append(lastDate)
            opDF['Date'] = dateList
            opDF[colname] = forecastedOutput
            opDF.to_csv(location+'/output.csv', sep=',', index=False)
            print("output file has been created at "+location+'/output.csv')
        except KeyError as kerror:
            print("Wrong key for index or Forecasting parameter.", kerror.args)
        except Exception as e:
            print("Exception caused due to -> ", e.args)
if __name__== '__main__':
    timeclassObject = TimeSeries()
    inputFile = str(sys.argv[1])
    predictionColumn = str(sys.argv[2])
    predictionRange = int(sys.argv[3])
    timeclassObject.filegenerator(inputFile, predictionColumn, predictionRange)
    #timVar.filegenerator(str(sys.argv[1]), str(sys.argv[2]), int(sys.argv[3]), timVar)