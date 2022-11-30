/**
 * reply.js
 * 댓글 관련 Ajax 기능 구현
 */

window.addEventListener('DOMContentLoaded', event => {
    // HTML 의 Document Object들이 모두 로딩이 끝난 후에 코드들이 실행될 수 있도록 하기 위해서.
    //readAllReplies(); // 포스트 상세 페이지가 로딩된 후 댓글 목록 화면 출력.

    // btnReplyRegister 버튼을 찾고 이벤트 리스너를 등록.
    const btnReplyRegister = document.querySelector('#btnReplyRegister');
    btnReplyRegister.addEventListener('click', registerNewReply);

    // 댓글 작성 함수
    function registerNewReply() {
        // 포스트 글 번호 찾음.
        const postId = document.querySelector('#postNo').value;
        // // 일단 임시로 postId 받기
        // const postId = 1;
        // 댓글 작성자 id 찾음.
        const writer = document.querySelector('#writer').value;
        // 댓글 내용을 찾음.
        const replyText = document.querySelector('#replyText').value;

        // 댓글 작성자와 내용은 비어있으면 안됨.
        if (writer == '' || replyText == '') {
            alert('댓글 내용은 반드시 이름하세요.');
            return;
        }
        // Ajax POST 요청을 보낼 때 서버로 보내는 데이터 작성.
        // java {} 은 배열, javascript {} 은 object.
        const data = {
            postId: postId, // 댓글이 달릴 포스트 아이디(번호)
            replyText: replyText, // 댓글 내용
            writer: writer // 댓글 작성자
        };

        // Axios 라이브러리를 사용해서 Ajax POST 요청을 보냄.
        axios.post('/api/reply', data)
            .then(response => {
                console.log(response);
                alert('# ' + response.data + '댓글 작성 성공');
                clearInputs(); // 댓글 작성자와 내용을 삭제.
                // readAllReplies(); // 댓글 목록을 다시 요청.
            }) // 성공 응답을 받았을 때
            .catch(error => {
                console.log(error);
            }); // 실패 응답을 받았을 때

    }

    function clearInputs() {
        document.querySelector('#writer').value = '';
        document.querySelector('#replyText').value = '';
    }

    const divModal = document.querySelector('#replyModal');
     const replyModal = new bootstrap.Modal(divModal); // 부트스트랩 Modal 객체 생성.
    const modalreplyId = document.querySelector('#modalreplyId');
    const modalreplyText = document.querySelector('#modalreplyText');
    const modalBtnDelete = document.querySelector('#modalBtnDelete');
    const modalBtnUpdate = document.querySelector('#modalBtnUpdate');


    function showModal(reply) {
        // modal에 댓글 아이디, 댓글 내용 채우기
        modalreplyId.value = reply.replyId;
        modalreplyText.value= reply.replyText;

        replyModal.show(); // modal를 보여주기
    }

    function readAllReplies() {
        const postId = document.querySelector('#id').value; // 댓글이 달려있는 글 번호

        axios.get('/api/reply/all/' + postId ) // Ajax GET 요청 보냄.
            .then(response => { updateReplyList(response.data)})
            .catch(error => {
                console.log(error);
            });
    }

});

