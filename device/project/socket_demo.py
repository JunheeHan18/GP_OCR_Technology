import asyncio
# �� ���� ����� �����Ѵ�.
import websockets
async def connect():
# �� ���Ͽ� ������ �մϴ�.
    async with websockets.connect("http:52.78.200.48:8888") as websocket:
# 10���� �ݺ��ϸ鼭 �� ���� ������ �޽����� �����մϴ�.
        for i in range(1,10,1):
            await websocket.send("hello socket!!");
# �� ���� ������ ���� �޽����� ���� �ֿܼ� ����մϴ�.
            data = await websocket.recv();
            print(data);
# �񵿱�� ������ �����Ѵ�.
asyncio.get_event_loop().run_until_complete(connect())

