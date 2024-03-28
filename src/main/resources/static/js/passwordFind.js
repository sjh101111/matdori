function passwordFind() {
        var username = document.getElementById('username').value;
        var email = document.getElementById('email').value;

        if(username && email) {
            // 서버로부터 비밀번호를 조회합니다.
            fetch('/forgot', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `username=${encodeURIComponent(username)}&email=${encodeURIComponent(email)}`
            })
                .then(response => response.text())
                .then(password => {
                    // 조회된 비밀번호를 alert 창으로 표시합니다.
                    alert('회원님의 비밀번호는 ' + password + ' 입니다.');
                })
                .catch(error => {
                    // 에러가 발생하면 사용자에게 알립니다.
                    alert('비밀번호 조회에 실패했습니다. 다시 시도해주세요.');
                });
        } else {
            alert('Username과 Email을 모두 입력해주세요.');
        }
}
