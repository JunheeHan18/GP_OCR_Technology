from picamera.array import PiRGBArray
from picamera import PiCamera
import time
import cv2
import os, io

#===============camera code===========
def take_pic(filename):
    camera = PiCamera()
    rawCapture = PiRGBArray(camera)
    time.sleep(0.1)
    camera.capture(rawCapture, format="bgr")
    image = rawCapture.array
    cv2.imwrite(filename, image)
    camera.close()

#===============pic use===============
#===============ocr code===========

def detect_text():
    from google.cloud import vision
    client = vision.ImageAnnotatorClient()

    file_name = os.path.abspath('2.jpg')

    with io.open(file_name, 'rb') as image_file:
        content = image_file.read()

    image = vision.types.Image(content=content)

    response = client.text_detection(image=image,
    image_context={"language_hints":["ko"]}, #korean, english
    )
    texts = response.text_annotations
    print('Texts:')


    dic={'word':(0,0)}

    tempText =str(texts[0].description)
    print(tempText)


    for text in texts:
      print('\n"{}"'.format(text.description))

      vertices = (['({},{})'.format(vertex.x, vertex.y)
                    for vertex in text.bounding_poly.vertices])

      print('bounds: {}'.format(','.join(vertices)))
      
      vertices = str(vertices).replace("'","")
      vertices = str(vertices).replace("[","")
      vertices = str(vertices).replace("]","")
      vertices = vertices.replace(")","")
      vertices = vertices.replace("(","")
      vertices = vertices.replace("'","")
      vertices = vertices.strip()
      dic[str(text.description)]=vertices
      
#==========edit dic==========
    del dic['word']

    print("text boundary")
    print(dic[tempText])
    bound=[]
    tempbound = dic[tempText]
    tempbound = tempbound.replace("(","")
    tempbound = tempbound.replace(")","")
    bound = tempbound.split(',')
    print(bound)
    
    middleX=(int(bound[2])+int(bound[0]))/2
    middleY=(int(bound[5])+int(bound[1]))/2
    print(middleX,middleY)


    def detLocation(X,Y):
        for i in dic:
            tempdic = dic[i].split(',')
            temp=[]
            for a in tempdic:
                temp.append(int(a))
                
            dic[i]=temp
            # you can print two element by using sep
            
            midX1 = (temp[0]+temp[4])/2
            midY1 = (temp[1]+temp[5])/2
            
            midX2 = (temp[2]+temp[6])/2
            midY2 = (temp[3]+temp[7])/2
            
            #about mid1
            if midX1 < X :
                dic[i].append("left")
            elif midX1 > X :
                dic[i].append("right")
            elif midX1 == X :
                dic[i].append("middle")
                
            if midY1 < Y :
                dic[i].append("down")
            elif midY1 > Y :
                dic[i].append("top")
            elif midY1 == Y :
                dic[i].append("middle")
                
            print(i,dic[i],sep=" ")
                
            
            
            
    detLocation(middleX,middleY)
detect_text()

