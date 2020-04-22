import os, io

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

#    for text in texts:
    text = texts[0]
    print('\n"{}"'.format(text.description))

#        vertices = (['({},{})'.format(vertex.x, vertex.y)
#                    for vertex in text.bounding_poly.vertices])

#        print('bounds: {}'.format(','.join(vertices)))

file_name = './b.jpg'

detect_text(file_name)
