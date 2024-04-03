document.getElementById('forgotForm').onsubmit = function(event) {
    event.preventDefault();
    var username = document.getElementById('username').value;
    var email = document.getElementById('email').value;
    fetch('/forgot', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'username=' + encodeURIComponent(username) + '&email=' + encodeURIComponent(email)
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(function(text) {
            var match = text.match(/newPassword=(.+)/);
            if (match && match[1]) {
                alert('임시 비밀번호: ' + match[1]);
                location.replace("/login");
            }
        })
        .catch(function(error) {
            alert('해당 정보가 없습니다!');
        });
};