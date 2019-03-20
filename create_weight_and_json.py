from keras import backend as K
from keras.models import load_model

K.clear_session()

model = load_model('model.h5',compile=False)
model_json = model.to_json()
with open("my_json_model.json", 'w') as json_file:
    json_file.write(model_json)
model.save_weights("weight_model.h5")
