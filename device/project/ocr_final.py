from flask import Flask, render_template
from flask_socketio import SocketIO
from picamera.array import PiRGBArray
from picamera import PiCamera
import time
import cv2
import os, io

app = Flask(__name__)
#app.config['SECRET_KEY'] = 'secret!'
socketio = SocketIO(app)

#===============ocr code===========

def detect_text(path):
    from google.cloud import vision
    client = vision.ImageAnnotatorClient()

    with io.open(path, 'rb') as image_file:
        content = image_file.read()

    image = vision.types.Image(content=content)

    response = client.text_detection(image=image,
    image_context={"language_hints":["ko"]}, #korean, english
    )
    texts = response.text_annotations
    print('Texts:')

    text = texts[0]
    print('\n"{}"'.format(text.description))
    socketio.emit('message_from_raspi','\n"{}"'.format(text.description))

#===============ocr end===========

#===============socket code==========
@socketio.on('take_a_pic')
def handle_message(message):
    print('received: ' + message)
    camera = PiCamera()
    rawCapture = PiRGBArray(camera)
    time.sleep(0.1)
    camera.capture(rawCapture, format="bgr")
    image = rawCapture.array
    filename = message +'.jpg'
    cv2.imwrite(filename, image)
    detect_text(filename)
#    socketio.emit('message_from_raspi', message)
#===============socket end==========

if __name__ == '__main__':
    socketio.run(app, debug=True, host='192.168.219.104')
