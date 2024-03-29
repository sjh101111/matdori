//댓글 생성 로직
const commentButton = document.getElementById('commentBtn');

if (commentButton) {
    commentButton.addEventListener('click', event => {
        let reviewId = window.location.pathname.split('/')[2];
        fetch(`/api/comment/review/${reviewId}`, { // 템플릿 리터럴 구문을 사용하여 ID 삽입
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                content: document.getElementById('content').value
            }),
        }).then(response => {
            if (response.ok) {
                alert('등록 완료되었습니다');

            } else {
                alert('등록 실패. 다시 시도해 주세요.');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('등록 실패. 다시 시도해 주세요.');
        });
    });
}

//댓글 수정 로직


document.addEventListener('DOMContentLoaded', function () {
    const editButton = document.getElementById('edit-comment-btn');
    const saveButton = document.getElementById('save-comment-btn');
    const commentText = document.getElementById('comment-content');
    const editField = document.getElementById('edit-comment-field');

    // "수정" 버튼 클릭 이벤트
    editButton.addEventListener('click', function () {
        // 텍스트 숨기기
        commentText.style.display = 'none';
        // 입력 필드 보이기 (댓글 내용으로 초기화)
        editField.style.display = '';
        editField.value = commentText.textContent;
        // "수정" 버튼 숨기기
        editButton.style.display = 'none';
        // "수정 완료" 버튼 보이기
        saveButton.style.display = '';
    });

    // "수정 완료" 버튼 클릭 이벤트
    saveButton.addEventListener('click', function () {
        // 여기에 fetch 요청 로직 추가...
        // 요청 성공 시 아래 로직 실행
        let reviewId = window.location.pathname.split('/')[2];
        let commentId = button.getAttribute('data-comment-id')
        fetch(`/api/comment/review/${reviewId}/${commentId}`, { // 템플릿 리터럴 구문을 사용하여 ID 삽입
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                content: document.getElementById('content').value
            }),
        }).then(response => {
            if (response.ok) {
                alert('수정 완료되었습니다');

            } else {
                alert('수정 실패. 다시 시도해 주세요.');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('등록 실패. 다시 시도해 주세요.');
        });

        // 텍스트 업데이트 및 보이기
        commentText.textContent = editField.value;
        commentText.style.display = '';
        // 입력 필드 숨기기
        editField.style.display = 'none';
        // "수정 완료" 버튼 숨기기
        saveButton.style.display = 'none';
        // "수정" 버튼 보이기
        editButton.style.display = '';
    })
})


//댓글 삭제 버튼
    const deleteButton = document.getElementById('delete-comment-btn-btn');

    if (deleteButton) {
        deleteButton.addEventListener('click', event => {
            let reviewId = window.location.pathname.split('/')[2];
            let commentId = button.getAttribute('data-comment-id')
            fetch(`/api/comment/review/${reviewId}/${commentId}`, {
                method: 'DELETE'
            }).then(() => {
                alert('삭제가 완료되었습니다');
                location.replace('/articles');
            });
        });
    }


