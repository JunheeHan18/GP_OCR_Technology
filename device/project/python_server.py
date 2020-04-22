import socket

HOST = '192.168.219.103'
PORT = 2030
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
	s.bind((HOST, PORT))
except socket.error:
	print ('Bind failed')

s.listen()
(conn, addr) = s.accept()

while True:
	data = conn.recv(1024)
	print ('Client from : ' + data.decode())
	reply = data
	conn.send(reply)
	conn.close()

s.close()
