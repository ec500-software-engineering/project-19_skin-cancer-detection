# import theano
# theano.config.device = 'gpu'
# theano.config.floatX = 'float32'
#Try to fater the load model process, which takes 58s at first.
# first try to add K.clear_session() to "Destroys the current TF graph and creates a new one.Useful to avoid clutter from old models / layers." but it doensn't work
# Then i tried to set some compile parameters, and it slower about 5 seconds. Finally i set the load_model function with compile = false, it the time reduce to 28 s

# finally, i tried to create model's weight json file, so that i can only load the weight file only when i predict. And it takes 29s. I think it's the best way for
# now though.
from keras.layers import *
from keras.preprocessing import image
from keras.applications.inception_resnet_v2  import preprocess_input
from keras.models import model_from_json
import time

# read the json_model_file
start1 = time.time()
json_file = open("my_json_model.json", 'r')
loaded_model_json = json_file.read()
json_file.close()
end1 = time.time()
print('read json file uses: '+str(end1-start1))


start2 = time.time()
model = model_from_json(loaded_model_json)
end2 = time.time()
print('load model from json uses: '+str(end2-start2))


# load the model weights from weight_model.h5
start3 = time.time()
model.load_weights("weight_model.h5")
end3 = time.time()
print('load model weights uses: '+str(end3-start3))


start4 = time.time()
img_path = '/Users/y/PycharmProjects/skincancer/input/HAM10000_images_part_2/ISIC_0029597.jpg'
img = image.load_img(img_path, target_size=(224, 224))
x = image.img_to_array(img)
x = np.expand_dims(x, axis=0)
x = preprocess_input(x)
preds = model.predict(x)
print(preds)
end4 = time.time()
print('get image and predict uses: '+str(end4-start4))
# images = np.vstack([x])
# classes = model.predict_classes(images, batch_size=10)
# print(classes)

# The predict_classes method is only available for the Sequential class (which is the class of your first model)
# but not for the Model class (the class of your second model).
#
# With the Model class, you can use the predict method which will give you a vector of probabilities and then get
# the argmax of this vector (with np.argmax(y_pred1,axis=1)).
