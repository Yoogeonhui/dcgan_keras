import threading

import requests, urllib
import re



mycnt = 0
nums = []
while True:
    URL = 'http://safebooru.org/index.php?page=post&s=list&tags=izumi_sagiri&pid='+str(mycnt)
    response = requests.get(URL)

    response_text = response.text
    indexes = [m.start() for m in re.finditer('<img src="//safebooru.org/thumbnails/', response_text)]
    if len(indexes) == 0:
        break
    for i in indexes:
        j = i + len('<img src="//safebooru.org/thumbnails/')
        tmp_str = ""
        while response_text[j]!='?':
            tmp_str += response_text[j]
            j += 1

        tmp_str = tmp_str.replace("thumbnail_", "")
        #print(tmp_str)
        nums.append(tmp_str)
    mycnt+=40

print(len(nums))

for i, j in enumerate(nums):
    myurl = 'http://safebooru.org//images/'+j
    if j[-4]!='.':
        f = open(str(i)+'.'+j[-4:], 'wb')
    else:
        f = open(str(i)+j[-4:], 'wb')
    response = requests.get(myurl)
    print(response.status_code)
    if response.status_code!=200:
        print('not 200')
        continue
    f.write(response.content)
    f.close()

