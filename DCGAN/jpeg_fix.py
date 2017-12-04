import os

lists = os.listdir('./rmt')
print(lists)
for i in lists:
    if i[-4]!= '.' and i[-5]!='.':
        print('rename '+i+'to '+i[:-4]+'.'+i[-4:])
        os.rename('./rmt/'+i, './rmt/'+i[:-4]+'.'+i[-4:])