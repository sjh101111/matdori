document.addEventListener('DOMContentLoaded', function () {
    const postReviewButton = document.getElementById('post-review-btn');
    const modiReviewButton = document.getElementById('modi-review-btn');

    const validateAndSubmitForm = (event, reviewId = null) => {
        event.preventDefault(); // 기본 폼 제출 방지

        const title = document.getElementById('title').value.trim();
        const content = document.getElementById('content').value.trim();

        if (!title && !content) {
            alert('제목과 내용을 모두 입력해주세요.');
            return;
        } else if (!title) {
            alert('제목을 입력해주세요.');
            return;
        } else if (!content) {
            alert('내용을 입력해주세요.');
            return;
        }

        let formData = new FormData();
        formData.append('title', title);
        formData.append('content', content);
        formData.append('restaurantId', document.getElementById('restaurant').value);
        formData.append('rating', document.getElementById('rating').value);
        formData.append('waitingTime', document.getElementById('waitingTime').value);
        formData.append('visitTime', document.getElementById('visitTime').value);

        const files = document.getElementById('imgFiles').files;
        for (let i = 0; i < files.length; i++) {
            formData.append('imgFiles', files[i]);
        }

        let requestUrl = '/api/review';
        let requestMethod = 'POST';
        if (reviewId) {
            requestUrl += `/${reviewId}`;
            requestMethod = 'PUT';
        }

        // fetch 요청
        fetch(requestUrl, {
            method: requestMethod,
            body: formData,
        }).then(response => {
            if (response.ok) {
                alert(reviewId ? '수정 완료되었습니다' : '등록 완료되었습니다');
                location.replace(`/reviews`);
            } else {
                alert('실패. 다시 시도해 주세요.');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('실패. 다시 시도해 주세요.');
        });
    };

    if (postReviewButton) {
        postReviewButton.addEventListener('click', event => validateAndSubmitForm(event));
    }

    if (modiReviewButton) {
        let params = new URLSearchParams(location.search);
        let reviewId = params.get('reviewId');
        modiReviewButton.addEventListener('click', event => validateAndSubmitForm(event, reviewId));
    }
});

const deleteReviewButton = document.getElementById('delete-btn');
if (deleteReviewButton) {
    deleteReviewButton.addEventListener('click', event => {
        let reviewId = document.getElementById('review-id').value;
        fetch(`/api/review/${reviewId}`, {
            method: 'DELETE'
        }).then(() => {
            alert('삭제가 완료되었습니다');
            location.replace(`/reviews`);
        });
    });
}