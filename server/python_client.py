import socket
#import stdin
import sys
reply='null'
data = sys.stdin.readline() # node.js 에서 python파일로 넘기는 데이터 받아오는 부분

HOST = ''
PORT = 5000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))
#s.listen()
#(conn, addr) = s.accept()
#s.connect((HOST,PORT))
print('connected')
while True:
          #data = '한준희입니다 반갑습니다'
          s.send(data.encode())
          reply = s.recv(1024)
          reply.copy()

s.close()

print ('server_from : '+reply.decode())