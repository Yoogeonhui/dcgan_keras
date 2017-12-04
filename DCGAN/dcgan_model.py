from keras.layers import Dense, Activation, BatchNormalization, Reshape, UpSampling2D, Conv2D, MaxPooling2D, \
    Conv2DTranspose, LeakyReLU, Flatten
from keras.models import Sequential
from keras.optimizers import SGD
import numpy as np

import preprocess
from preprocess import nextBatch
from PIL import Image
BATCH_SIZE = 100


def repeat_trans_conv(model, size):
    model.add(Conv2DTranspose(size,(5,5), strides=(2,2), padding='same'))
    model.add(BatchNormalization())
    model.add(Activation('relu'))


def repeat_conv(model, size):
    model.add(Conv2D(size, (5,5), strides=(2,2), padding='same'))
    model.add(BatchNormalization())
    model.add(Activation(LeakyReLU))


def generator():
    model = Sequential()
    model.add(Dense(input_dim=100, output_dim=1024))
    model.add(Activation('relu'))
    model.add(Dense(4*4*1024))
    model.add(BatchNormalization())
    model.add(Activation('relu'))
    model.add(Reshape((4,4,1024), input_shape=(1024*4*4,)))
    repeat_trans_conv(model, 512)
    repeat_trans_conv(model, 256)
    repeat_trans_conv(model, 128)
    repeat_trans_conv(model, 64)
    model.add(Conv2DTranspose(3,(5,5), strides=(2,2), padding='same'))
    model.add(Activation('tanh'))
    model.summary()
    return model


def discriminator():
    model = Sequential()
    repeat_conv(model, 64)
    repeat_conv(model, 128)
    repeat_conv(model, 256)
    repeat_conv(model, 512)
    repeat_conv(model, 1024)
    model.add(Flatten())
    model.add(Dense(1))
    model.add(Activation('relu'))
    model.add(Activation('sigmoid'))
    return model


def generator_ppap_dis(g, d):
    model = Sequential()
    model.add(g)
    d.trainable = False
    model.add(d)
    return model


#from github
def combine_images(generated_images):
    num = generated_images.shape[0]
    width = int(np.math.sqrt(num))
    height = int(np.math.ceil(float(num) / width))
    shape = generated_images.shape[1:3]
    image = np.zeros((height*shape[0], width*shape[1]),
                     dtype=generated_images.dtype)
    for index, img in enumerate(generated_images):
        i = int(index/width)
        j = index % width
        image[i*shape[0]:(i+1)*shape[0], j*shape[1]:(j+1)*shape[1]] = \
            img[:, :, 0]
    return image


def __main__():
    g = generator()
    d = discriminator()
    d_with_g = generator_ppap_dis(g,d)
    d_optim = SGD(lr=0.0005, momentum=0.9, nesterov=True)
    g_optim = SGD(lr=0.0005, momentum=0.9, nesterov=True)
    g.compile(loss='binary_crossentropy', optimizer="SGD")
    d_with_g.compile(loss='binary_crossentropy', optimizer=g_optim)
    d.trainable = True
    d.compile(loss='binary_crossentropy', optimizer=d_optim)
    for epoch in range(100):
        print("Epoch is", epoch)
        print("Number of batches", int(X_train.shape[0]/BATCH_SIZE))
        for index in range(int(preprocess.size_of_data/BATCH_SIZE)):
            X_train = nextBatch(BATCH_SIZE)
            noise = np.random.uniform(-1, 1, size=(BATCH_SIZE, 100))
            image_batch = X_train[index*BATCH_SIZE:(index+1)*BATCH_SIZE]
            generated_images = g.predict(noise, verbose=0)
            if index % 20 == 0:
                image = combine_images(generated_images)
                image = image*127.5+127.5
                Image.fromarray(image.astype(np.uint8)).save(
                    str(epoch)+"_"+str(index)+".png")
            X = np.concatenate((image_batch, generated_images))
            y = [1] * BATCH_SIZE + [0] * BATCH_SIZE
            d_loss = d.train_on_batch(X, y)
            print("batch %d d_loss : %f" % (index, d_loss))
            noise = np.random.uniform(-1, 1, (BATCH_SIZE, 100))
            d.trainable = False
            g_loss = d_with_g.train_on_batch(noise, [1] * BATCH_SIZE)
            d.trainable = True
            print("batch %d g_loss : %f" % (index, g_loss))
            if index % 10 == 9:
                g.save_weights('generator', True)
                d.save_weights('discriminator', True)

