var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.listen(3000, function () {
    console.log('서버 실행 중...');
});

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