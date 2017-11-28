from keras.layers import Dense, Activation, BatchNormalization, Reshape, UpSampling2D, Conv2D, MaxPooling2D, \
    Conv2DTranspose, LeakyReLU, Flatten
from keras.models import Sequential
from keras.optimizers import SGD, Adam
import numpy as np
from os import listdir
import cv2
import math
from keras.backend import categorical_crossentropy
from keras import backend as K

what_now = 0
imageDir = './image'
directoryList = listdir(imageDir)
size_of_data = len(directoryList)
size = 64

def nextBatch(batch_size = 100):
    global what_now
    save = np.zeros(shape = (batch_size, size, size, 3), dtype=np.float32)
    loadList = []
    isthisEnd = False
    if what_now+batch_size > size_of_data:
        loadList = directoryList[what_now:size_of_data]
        loadList.extend(directoryList[0:batch_size-(size_of_data-what_now)])
        what_now = batch_size-(size_of_data-what_now)
        isthisEnd = True
    else:
        loadList = directoryList[what_now:what_now+batch_size]
        what_now += batch_size
    for i, name in enumerate(loadList):
        image = cv2.imread(imageDir+'/'+name, cv2.IMREAD_COLOR)
        resized = cv2.resize(image, (size,size))
        save[i] = resized
    save = (save- 127.5) / 127.5
    return (isthisEnd, save)


BATCH_SIZE = 100


def repeat_trans_conv(model, size):
    model.add(Conv2DTranspose(size,(5,5), strides=(2,2), padding='same'))
    model.add(BatchNormalization())
    model.add(Activation('relu'))


def repeat_conv(model, size):
    model.add(Conv2D(size, (5,5), strides=(2,2), padding='same'))
    model.add(BatchNormalization())
    model.add(LeakyReLU())

def accuracy(mat1, mat2):
    mat1 = np.argmax(mat1,axis=1)
    mat2 = np.argmax(mat2,axis=1)
    diff = mat1-mat2
    n_tot = mat1.shape[0]
    n_rig = (diff==0).sum()
    acc = n_rig*100.0/n_tot
    return acc


def generator():
    model = Sequential()
    model.add(Dense(input_dim=100, units=4*4*1024))
    model.add(Activation('relu'))
    model.add(BatchNormalization())
    model.add(Reshape((4,4,1024), input_shape=(1024*4*4,)))
    repeat_trans_conv(model, 512)
    repeat_trans_conv(model, 256)
    repeat_trans_conv(model, 128)
    #repeat_trans_conv(model, 64)
    model.add(Conv2DTranspose(3,(5,5), strides=(2,2), padding='same'))
    model.add(Activation('tanh'))
    model.summary()
    return model


def discriminator():
    model = Sequential()
    model.add(Conv2D(128, (5,5), strides=(2,2), padding='same', input_shape = (size,size,3)))
    model.add(BatchNormalization())
    model.add(LeakyReLU())
    #repeat_conv(model, 128)
    repeat_conv(model, 256)
    repeat_conv(model, 512)
    repeat_conv(model, 1024)
    model.add(Flatten())
    model.add(Dense(1024))
    model.add(Dense(2,activation='softmax'))
    model.summary()
    return model


def generator_ppap_dis(g, d):
    model = Sequential()
    model.add(g)
    d.trainable = False
    model.add(d)
    return model

def combine_images(generated_images):
    getsoo = int(math.sqrt(generated_images.shape[0]))
    output = np.zeros((size * getsoo, size * getsoo, 3), dtype=np.float32)
    for i in range(0, getsoo):
        for j in range(0, getsoo):
            output[size*i:size*i+size, size*j:size*j+size, :] = generated_images[10*i+j]
    output = output * 127.5 + 127.5
    output = output.astype(int)
    return output


def __main__():
    res_var = 0
    tf_session = K.get_session()
    g = generator()
    d = discriminator()


    d_optim = Adam(lr=0.0002)
    g_optim = Adam(lr=0.0002)

    g.compile(loss='binary_crossentropy', optimizer="SGD")
    #g.load_weights('generator')
    d.trainable=True
    d.compile(loss='categorical_crossentropy', optimizer=d_optim)
    d_with_g = generator_ppap_dis(g,d)
    d_with_g.compile(loss='categorical_crossentropy', optimizer=g_optim)
    #d.load_weights('discriminator')
    index = 0
    #pretraining
    flag = False
    '''
    while(not flag):
        isthisEnd, image_batch = nextBatch(BATCH_SIZE)
        flag = isthisEnd
        noise = np.random.uniform(-1, 1, size=(BATCH_SIZE, 100))
        generated_images = g.predict(noise)
        if index % 100 == 0:
            image = combine_images(generated_images)
            cv2.imwrite('res'+str(res_var)+'.png', image)
            res_var = (res_var+1)%5
        X = np.concatenate((image_batch, generated_images))
        y = np.zeros([2*BATCH_SIZE,2])
        y[:BATCH_SIZE,1] = 1
        y[BATCH_SIZE:,0] = 1
        d.trainable = True
        d_loss = d.train_on_batch(X, y)
        noise = np.random.uniform(-1, 1, (BATCH_SIZE, 100))
        if index % 10 == 0:
            d_predict = d.predict(X, verbose=0)
            wtf = d_with_g.predict(noise, verbose=0)
            with open('dis_res.txt','w') as file:
                file.write(str(d_predict)+'\n')
            with open('dis_gen_res.txt', 'w') as file:
                file.write(str(wtf)+'\n')
            print('d loss: '+str(d_loss))
            d_acc = accuracy(d_predict, y)
            print('d_acc : '+str(d_acc))
            with open('disloss.txt', 'a') as file:
                file.write(str(d_loss)+'\n')
            with open('dis_acc.txt','a') as file:
                file.write(str(d_acc)+'\n')
        if index % 100 == 9:
            d.save_weights('discriminator', True)
        index +=1
    '''
    for epoch in range(100):
        print("Epoch is", epoch)
        print("Number of batches", int(size_of_data/BATCH_SIZE))

        index = 0
        while(not flag):
            isthisEnd, image_batch = nextBatch(BATCH_SIZE)
            flag = isthisEnd
            noise = np.random.uniform(-1, 1, size=(BATCH_SIZE, 100))
            generated_images = g.predict(noise)
            if index % 100 == 0:
                image = combine_images(generated_images)
                cv2.imwrite('res'+str(res_var)+'.png', image)
                res_var = (res_var+1)%5
            X = np.concatenate((image_batch, generated_images))
            y = np.zeros([2*BATCH_SIZE,2])
            y[:BATCH_SIZE,1] = 1
            y[BATCH_SIZE:,0] = 1
            d.trainable = True
            d_loss = d.train_on_batch(X, y)
            d.trainable = False
            y2 = np.zeros([BATCH_SIZE,2])
            y2[:,1] = 1
            g_loss_in_func= d_with_g.train_on_batch(noise, y2)
            #_ = d_with_g.train_on_batch(noise, y2)

            noise = np.random.uniform(-1, 1, (BATCH_SIZE, 100))
            if index % 10 == 0:
                d_predict = d.predict(X, verbose=0)
                d_with_g_predict = d_with_g.predict(noise, verbose=0)
                with open('dis_res.txt','w') as file:
                    file.write(str(d_predict)+'\n')
                with open('dis_gen_res.txt', 'w') as file:
                    file.write(str(wtf)+'\n')
                d_predict = d_predict >= 0.5
                y2 = K.constant(y2)
                d_with_g_predict = K.constant(d_with_g_predict)
                g_loss = np.mean(categorical_crossentropy(y2, d_with_g_predict).eval(session=tf_session))
                print('d loss: '+str(d_loss)+' g loss: '+str(g_loss)+'g loss in func:'+str(g_loss_in_func))
                d_predict = d_predict.astype(int)
                d_acc = accuracy(d_predict, y)
                print('d_acc : '+str(d_acc))
                with open('disloss.txt', 'a') as file:
                    file.write(str(d_loss)+'\n')
                with open('genloss.txt', 'a') as file:
                    file.write(str(g_loss)+'\n')
                with open('dis_acc.txt','a') as file:
                    file.write(str(d_acc)+'\n')
            if index % 100 == 9:
                g.save_weights('generator', True)
                d.save_weights('discriminator', True)
            index += 1
