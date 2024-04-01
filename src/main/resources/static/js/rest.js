document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.deleteRestBtn').forEach(button => {
        button.addEventListener('click', function() {
            const restaurantId = this.getAttribute('data-id');
            if (confirm('정말 삭제하시겠습니까?')) {
                fetch(`/api/restaurant/${restaurantId}`, {
                    method: 'DELETE',
                })
                    .then(response => {
                        if (response.ok) {
                            alert('삭제되었습니다.');
                            window.location.reload(); // 페이지를 새로고침하여 변경 사항 반영
                        } else {
                            alert('삭제에 실패했습니다.');
                        }
                    })
                    .catch(error => console.error('Error:', error));
            }
        });
    });
});

const postRestButton = document.getElementById('post-rest-btn');
if (postRestButton) {
    postRestButton.addEventListener('click', event => {
        fetch(`/api/restaurant`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body : JSON.stringify({
                name: document.getElementById('name').value,
                address: document.getElementById('address').value,
                category: document.getElementById('category').value
            }),
        }).then(() => {
            alert('등록 완료되었습니다');
            location.replace("/restaurants");
        })
    })
}

const modiRestButton = document.getElementById('modi-rest-btn');
if (modiRestButton) {
    // 클릭 이벤트가 감지되면 수정 API 요청
    modiRestButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let restaurantId = params.get('restaurantId');

        fetch(`/api/restaurant/${restaurantId}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name: document.getElementById('name').value,
                address: document.getElementById('address').value,
                category: document.getElementById('category').value
            })
        }).then(() => {
            alert('수정이 완료되었습니다');
            location.replace(`/restaurants`);
        });
    });
}