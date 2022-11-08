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


def graph2(info, sessionID):
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
            if item[6] == "Hesitantly":
                moreChange1 += 1
            elif item[6] == "Yes":
                mostChange1 += 1
            else:
                noChange1 += 1
        else:
            count2 += 1
            if item[6] == "Hesitantly":
                moreChange2 += 1
            elif item[6] == "Yes":
                mostChange2 += 1
            else:
                noChange2 += 1
    plt.title('Pursue investor?')
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
    plt.legend((p1[0], p2[0], p3[0]), ('No', 'Hesitantly', 'Yes'), loc=9)
    plt.savefig(f'{sessionID}/investor.png')


def graph3(info, sessionID):
    noChange1 = 0
    moreChange1 = 0
    noChange2 = 0
    moreChange2 = 0
    count1 = 0
    count2 = 0
    for item in info:
        if item[3] == 1:
            count1 += 1
            if item[7] == "Yes - remove someone or reduce their role" or item[7] == "Yes - add someone":
                moreChange1 += 1
            else:
                noChange1 += 1
        else:
            count2 += 1
            if item[7] == "Yes - remove someone or reduce their role" or item[7] == "Yes - add someone":
                moreChange2 += 1
            else:
                noChange2 += 1
    plt.title('People changes?')
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
    d = []
    for i in range(0, len(Bottom)):
        tempsum = Bottom[i] + Center[i]
        d.append(tempsum)

    width = 0.35
    p1 = plt.bar(ind, Bottom, width, color='red')
    p2 = plt.bar(ind, Center, width, bottom=Bottom, color='green')
    plt.legend((p1[0], p2[0]), ('No people change', 'People change'), loc=9)
    plt.savefig(f'{sessionID}/people.png')


def main():
    db = pymysql.connect(host='localhost',
                         port=3306,
                         user='root',
                         password='root',
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
    processCSV(data, sessionID, cursor)
    print("finished processing CSV")
    graph1(data, sessionID)
    graph2(data, sessionID)
    graph3(data, sessionID)
    print("finished graph generation")
    shutil.make_archive(f'zipped results/{sessionID}', 'zip', f'{sessionID}')
    print("finished zip file generation")
    # delete original folder
    shutil.rmtree(f'{sessionID}')
    db.close()


if __name__ == "__main__":
    main()
