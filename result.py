import os
import matplotlib.pyplot as plt
import matplotlib
import pandas as pd
import pymysql
import sys
import shutil

matplotlib.use('TkAgg')


def processCSV(info, sessionID, myc):
    df = pd.DataFrame(info,
                      columns=['1', '2', '3', 'Survey Number', "Question 1", "Question 2", "Question 3", "Question 4",
                               "Question 5"])
    tnList = []
    for item in df["2"].tolist():
        myc.execute(f"SELECT TEAMNAME from team where TMID ={item}")
        data = myc.fetchone()
        tnList.append(data[0])
    df.insert(2, 'Team Name', tnList)
    df = df.drop(columns=['1', '2', '3'])
    df.to_csv(f"{sessionID}/SurveyResults.csv", index=False)
    return df


def graph1(info, sessionID):
    noChange1 = 0
    moreChange1 = 0
    mostChange1 = 0
    noChange2 = 0
    moreChange2 = 0
    mostChange2 = 0
    count1 = 0
    count2 = 0
    for item in info:
        if item[3] == 1:
            count1 += 1
            if item[4] == "Some changes were made by modifying one or two points and then keeping the remaining " \
                          "elements the same":
                moreChange1 += 1
            elif item[4] == "Major changes were made in taking the original plan into a new direction":
                mostChange1 += 1
            else:
                noChange1 += 1
        else:
            count2 += 1
            if item[
                4] == "Some changes were made by modifying one or two points and then keeping the remaining elements " \
                      "the same":
                moreChange2 += 1
            elif item[4] == "Major changes were made in taking the original plan into a new direction":
                mostChange2 += 1
            else:
                noChange2 += 1
    plt.title('Product changes?')
    ind = [0, 1]
    plt.xticks(ind, ('Survey 1', 'Survey 2'))
    plt.yticks(list(range(0, 110, 10)), ['0%', '10%', '20%', '30%', '40%', '50%', '60%', '70%', '80%', '90%', '100%'])
    if count1 == 0:
        count1 = 1
    if count2 == 0:
        count2 = 1
    Bottom = (
        noChange1 / count1 * 100, noChange2 / count2 * 100)
    Center = (moreChange1 / count1 * 100,
              moreChange2 / count2 * 100)
    Top = (mostChange1 / count1 * 100,
           mostChange2 / count2 * 100)

    d = []
    for i in range(0, len(Bottom)):
        tempsum = Bottom[i] + Center[i]
        d.append(tempsum)

    width = 0.35
    p1 = plt.bar(ind, Bottom, width, color='red')
    p2 = plt.bar(ind, Center, width, bottom=Bottom, color='yellow')
    p3 = plt.bar(ind, Top, width, bottom=d, color='green')
    plt.legend((p1[0], p2[0], p3[0]), ('No Change', 'Some Change', 'Major Change'), loc=9)
    plt.savefig(f'{sessionID}/product.png')


# df, 3, 1
# df, 3, 2
# df, 4, 1
# df, 4, 2
def calculatePer(df, questionNum, surveyNum):
    SurveyList = df['Survey Number'].tolist()
    Q1List = df['Question 1'].tolist()
    Q2List = df['Question ' + str(questionNum)].tolist()
    Some = 0
    No = 0
    All = 0
    NoNo = 0
    NoSome = 0
    NoAll = 0
    SomeNo = 0
    SomeSome = 0
    SomeAll = 0
    AllNo = 0
    AllSome = 0
    AllAll = 0
    if questionNum == 3:
        for i in range(len(SurveyList)):
            if SurveyList[i] == surveyNum:
                if Q1List[
                    i] == "Some changes were made by modifying one or two points and then keeping the remaining " \
                          "elements the same":
                    Some += 1
                    if Q2List[i] == "No":
                        SomeNo += 1
                    elif Q2List[i] == "Yes":
                        SomeAll += 1
                    else:
                        SomeSome += 1
                elif Q1List[i] == "Major changes were made in taking the original plan into a new direction":
                    All += 1
                    if Q2List[i] == "No":
                        AllNo += 1
                    elif Q2List[i] == "Yes":
                        AllAll += 1
                    else:
                        AllSome += 1
                else:
                    No += 1
                    if Q2List[i] == "No":
                        NoNo += 1
                    elif Q2List[i] == "Yes":
                        NoAll += 1
                    else:
                        NoSome += 1

    else:
        for i in range(len(SurveyList)):
            if SurveyList[i] == surveyNum:
                if Q1List[
                    i] == "Some changes were made by modifying one or two points and then keeping the remaining " \
                          "elements the same":
                    Some += 1
                    if Q2List[i] == "No":
                        SomeNo += 1
                    else:
                        SomeAll += 1
                elif Q1List[i] == "Major changes were made in taking the original plan into a new direction":
                    All += 1
                    if Q2List[i] == "No":
                        AllNo += 1
                    else:
                        AllAll += 1
                else:
                    No += 1
                    if Q2List[i] == "No":
                        NoNo += 1
                        print("here")
                    else:
                        NoAll += 1
    if No == 0:
        No = 1
    if Some == 0:
        Some = 1
    if All == 0:
        All = 1
    return [No, Some, All, NoNo, NoSome, NoAll, SomeNo, SomeSome, SomeAll, AllNo, AllSome, AllAll]


def graph2(df, sessionID):
    resList = calculatePer(df, 3, 1)
    fig, ax = plt.subplots()
    labels = ['No pivot', 'Small pivot', 'Big pivot']
    Bottom = [
        resList[3] / resList[0] * 100, resList[6] / resList[1] * 100, resList[9] / resList[2] * 100
    ]
    Center = [resList[4] / resList[0] * 100, resList[7] / resList[1] * 100, resList[10] / resList[2] * 100
              ]
    Top = [resList[5] / resList[0] * 100, resList[8] / resList[1] * 100, resList[11] / resList[2] * 100
           ]
    d = []
    for i in range(len(Bottom)):
        tempsum = Bottom[i] + Center[i]
        d.append(tempsum)
    width = 0.35
    ax.bar(labels, Bottom, width, color='red', label="No")
    ax.bar(labels, Center, width, bottom=Bottom, color='yellow', label="Hesitantly")
    ax.bar(labels, Top, width, bottom=d, color='green', label="Yes")
    plt.yticks(list(range(0, 110, 10)), ['0%', '10%', '20%', '30%', '40%', '50%', '60%', '70%', '80%', '90%', '100%'])
    ax.set_title('Pursue investor? (Survey 1)')
    ax.legend()
    plt.savefig(f'{sessionID}/investorS1.png')


def graph3(df, sessionID):
    resList = calculatePer(df, 3, 2)
    fig, ax = plt.subplots()
    labels = ['No pivot', 'Small pivot', 'Big pivot']
    Bottom = [
        resList[3] / resList[0] * 100, resList[6] / resList[1] * 100, resList[9] / resList[2] * 100
    ]
    Center = [resList[4] / resList[0] * 100, resList[7] / resList[1] * 100, resList[10] / resList[2] * 100
              ]
    Top = [resList[5] / resList[0] * 100, resList[8] / resList[1] * 100, resList[11] / resList[2] * 100
           ]
    d = []
    for i in range(len(Bottom)):
        tempsum = Bottom[i] + Center[i]
        d.append(tempsum)
    width = 0.35
    ax.bar(labels, Bottom, width, color='red', label="No")
    ax.bar(labels, Center, width, bottom=Bottom, color='yellow', label="Hesitantly")
    ax.bar(labels, Top, width, bottom=d, color='green', label="Yes")
    plt.yticks(list(range(0, 110, 10)), ['0%', '10%', '20%', '30%', '40%', '50%', '60%', '70%', '80%', '90%', '100%'])
    ax.set_title('Pursue investor? (Survey 2)')
    ax.legend()
    plt.savefig(f'{sessionID}/investorS2.png')


def graph4(df, sessionID):
    resList = calculatePer(df, 4, 1)
    fig, ax = plt.subplots()
    labels = ['No pivot', 'Small pivot', 'Big pivot']
    Bottom = [
        resList[3] / resList[0] * 100, resList[6] / resList[1] * 100, resList[9] / resList[2] * 100
    ]
    Top = [resList[5] / resList[0] * 100, resList[8] / resList[1] * 100, resList[11] / resList[2] * 100
           ]
    width = 0.35
    ax.bar(labels, Bottom, width, color='red', label="No people change")
    ax.bar(labels, Top, width, bottom=Bottom, color='green', label="People change")
    plt.yticks(list(range(0, 110, 10)), ['0%', '10%', '20%', '30%', '40%', '50%', '60%', '70%', '80%', '90%', '100%'])
    ax.set_title('People changes? (Survey 1)')
    ax.legend()
    plt.savefig(f'{sessionID}/peopleS1.png')


def graph5(df, sessionID):
    resList = calculatePer(df, 4, 2)
    fig, ax = plt.subplots()
    labels = ['No pivot', 'Small pivot', 'Big pivot']
    Bottom = [
        resList[3] / resList[0] * 100, resList[6] / resList[1] * 100, resList[9] / resList[2] * 100
    ]
    Top = [resList[5] / resList[0] * 100, resList[8] / resList[1] * 100, resList[11] / resList[2] * 100
           ]
    width = 0.35
    ax.bar(labels, Bottom, width, color='red', label="No people change")
    ax.bar(labels, Top, width, bottom=Bottom, color='green', label="People change")
    plt.yticks(list(range(0, 110, 10)), ['0%', '10%', '20%', '30%', '40%', '50%', '60%', '70%', '80%', '90%', '100%'])
    ax.set_title('People changes? (Survey 2)')
    ax.legend()
    plt.savefig(f'{sessionID}/peopleS2.png')


def main():
    db = pymysql.connect(host='localhost',
                         port=3306,
                         user='centralis',
                         password='centralis',
                         database='centralis')
    cursor = db.cursor()
    sessionID = sys.argv[1]
    current_directory = os.getcwd()
    final_directory = os.path.join(current_directory, f'{sessionID}')
    if not os.path.exists(final_directory):
        os.makedirs(final_directory)
    final_directory = os.path.join(current_directory, 'zipped results')
    if not os.path.exists(final_directory):
        os.makedirs(final_directory)
    cursor.execute(f"SELECT * from survey where SEID ={sessionID}")
    data = cursor.fetchall()
    df = processCSV(data, sessionID, cursor)
    print("finished processing CSV")
    graph1(data, sessionID)
    graph2(df, sessionID)
    graph3(df, sessionID)
    graph4(df, sessionID)
    graph5(df, sessionID)
    print("finished graph generation")
    shutil.make_archive(f'zipped results/{sessionID}', 'zip', f'{sessionID}')
    print("finished zip file generation")
    # delete original folder
    shutil.rmtree(f'{sessionID}')
    db.close()


if __name__ == "__main__":
    main()
