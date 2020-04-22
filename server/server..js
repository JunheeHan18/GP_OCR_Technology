var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var http = require('http').Server(app); 
var io = require('socket.io')(http);
var transData = null;
var {PythonShell}=require('python-shell');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.listen(3000, function () {
    console.log('서버: 3000번 포트 실행 중...');
});

http.listen(4000, function () {
    console.log('서버: 4000번 포트 실행 중...');
});


//여기부터 http expess 통신

var connection = mysql.createConnection({
    host: "kpu-project-db.crgzflc7ns8y.ap-northeast-2.rds.amazonaws.com",
    user: "scott",
    database: "ocrdb",
    password: "tiger2020",
    port: 3306
});

app.post('/user/join', function (req, res) {
    console.log(req.body);
    var memberPwd = req.body.memberPwd;
    var memberId = req.body.memberId;

    // 삽입을 수행하는 sql문.
    var sql = 'INSERT INTO member (member_id,member_password,regist_date) VALUES (?, ?,CURRENT_DATE())';
    var params = [memberId, memberPwd];

    // sql 문의 ?는 두번째 매개변수로 넘겨진 params의 값으로 치환된다.
    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            message = '회원가입에 성공했습니다.';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});

app.post('/user/login', function (req, res) {
    var memberId= req.body.memberId;
    var memberPwd = req.body.memberPwd;
    var sql = 'select * from member where member_id = ?';

    connection.query(sql, userEmail, function (err, result) {
        var resultCode = 404;
        var message = '404 not found 에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            if (result.length === 0) {
                resultCode = 204;
                message = '존재하지 않는 계정입니다!';
            } else if (memberPwd !== result[0].member_password) {
                resultCode = 204;
                message = '비밀번호가 틀렸습니다!';
            } else {
                resultCode = 200;
                message = '로그인 성공! ' + result[0].member_id + '님 환영합니다!';
            }
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    })
});

//변환 텍스트 저장 로직
app.post('/user/text/save', function (req, res) {
    console.log(req.body);
    var title = req.body.title;
    var content = req.body.content;

    // 삽입을 수행하는 sql문.
    var sql = 'INSERT INTO bookmark (title,content,save_date) VALUES (?, ?,CURRENT_DATE())';
    var params = [title, content];

    // sql 문의 ?는 두번째 매개변수로 넘겨진 params의 값으로 치환된다.
    connection.query(sql, params, function (err, result) {
        var resultCode = 404;
        var message = '에러가 발생했습니다';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            message = '텍스트 저장에 성공했습니다.';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});

// 여기까지 http express 통신

//여기서 부터 소켓 통신
io.sockets.on('connection', function (socket){ 
   //원격에서 접속이 되면 기본 응답 
   socket.emit('message_from_server', 'Server Conneted..');

  //메세지가 들어 오면 응답 
   socket.on('message_from_client', function (msg){ 
     console.log('message:', msg); 
     /*받은 메세지를 되돌려 주자. 
     아니면 받은 데이터를 이용 라즈베리파에서 뭐든 할 수 있다. 
     */ 
     socket.emit('message_from_server', 'Server Conneted..'); 
   });

//상태모드 독서모드가 들어 오면 응답 
   socket.on('set_to_reading', function (msg){ 
     console.log('message:', msg); 
     /*받은 메세지를 되돌려 주자. 
     아니면 받은 데이터를 이용 라즈베리파에서 뭐든 할 수 있다. 
     */ 
     socket.emit('message_from_server', '독서모드로 변경되었습니다.');
     var pyshell = new PythonShell('python_client.py');
     pyshell.send(msg);
     pyshell.on('message', function (data) {
     console.log(data);
     socket.emit('text_from_raspi', data);
     });

   });

//상태모드 일상모드가 들어 오면 응답 
   socket.on('set_to_normal', function (msg){ 
     console.log('message:', msg); 
     /*받은 메세지를 되돌려 주자. 
     아니면 받은 데이터를 이용 라즈베리파에서 뭐든 할 수 있다. 
     */ 
     socket.emit('message_from_server', '일상모드로 변경되었습니다.'); 
   });

});