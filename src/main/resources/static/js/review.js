const postReviewButton = document.getElementById('post-review-btn');

if (postReviewButton) {
    postReviewButton.addEventListener('click', event => {
        fetch(`/api/review`, { // 템플릿 리터럴 구문을 사용하여 ID 삽입
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value,
                restaurantId: document.getElementById('restaurant').value,
                rating: document.getElementById('rating').value,
                waitingTime: document.getElementById('waitingTime').value,
                visitTime: document.getElementById('visitTime').value

            }),
        }).then(response => {
            if (response.ok) {
                alert('등록 완료되었습니다');
                //location.replace(`/restaurant/${restaurantId}`);
            } else {
                alert('등록 실패. 다시 시도해 주세요.');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('등록 실패. 다시 시도해 주세요.');
        });
    });
}