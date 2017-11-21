import cv2
from os import listdir
import numpy as np

what_now = 0
imageDir = './images'
directoryList = listdir(imageDir)
size_of_data = len(directoryList)

print(str(100*[1]))

def nextBatch(batch_size = 100):
    save = np.zeros(shape = (batch_size, 128, 128, 3))
    loadList = []
    if what_now+batch_size > size_of_data:
        loadList = directoryList[what_now:size_of_data]
        loadList.extend(directoryList[0:batch_size-(size_of_data-what_now)])
    else:
        loadList = directoryList[what_now:what_now+batch_size]
    for i, name in enumerate(loadList):
        image = cv2.imread(imageDir+'/'+name, cv2.IMREAD_COLOR)
        resized = cv2.resize(image, (128,128))
        save[i] = resized
    return save