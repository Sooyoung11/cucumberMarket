/**
 * reply.js
 * 댓글 관련 Ajax 기능 구현
 */

window.addEventListener('DOMContentLoaded', event => {

    // 선택한 replyNo 가져오기 위해
    let getReplyNo = 0;

    // 선택한 postNo 가져오기 위해
    let getPostNo = 0;

    // HTML 의 Document Object들이 모두 로딩이 끝난 후에 코드들이 실행될 수 있도록 하기 위해서.
    readAllReplies(); // 포스트 상세 페이지가 로딩된 후 댓글 목록 화면 출력.


    // btnReplyRegister 버튼(댓글 등록 버튼)을 찾고 이벤트 리스너를 등록.
    const btnReplyRegister = document.querySelector('#btnReplyRegister');
    btnReplyRegister.addEventListener('click', registerNewReply);

    // 댓글 작성 함수
    function registerNewReply() {
        // 포스트 글 번호 찾음.
        const postNo = document.querySelector('#postNo').value;
        // 댓글 작성자 찾음.
        const replier = document.querySelector('#replier').value;
        // 댓글 내용을 찾음.
        const replyContent = document.querySelector('#replyContent').value;

        // 댓글 작성자와 내용은 비어있으면 안됨.
        if (replier == '' || replyContent == '') {
            alert('댓글 내용은 반드시 이름하세요.');
            return;
        }

        //비밀 댓글 체크 여부
        let secretReply = document.getElementById('secretReply').checked;
        console.log(secretReply);

        // Ajax POST 요청을 보낼 때 서버로 보내는 데이터 작성.
        // java {} 은 배열, javascript {} 은 object.
        const data = {
            postNo: postNo, // 댓글이 달릴 포스트 아이디(번호)
            replyContent: replyContent, // 댓글 내용
            replier: replier, // 댓글 작성자
            secretReply: secretReply, // 비밀 댓글
            likeCount: 0, // 좋아요 카운트
            parent: 0 // 댓글로 구분
        };

        // Axios 라이브러리를 사용해서 Ajax POST 요청을 보냄.
        axios.post('/api/reply', data)
            .then(response => {
                console.log(response);
                alert('# ' + response.data + '댓글 작성 성공');
                clearInputs(); // 댓글 작성자와 내용을 삭제.
                readAllReplies(); // 댓글 목록을 다시 요청.

            }) // 성공 응답을 받았을 때
            .catch(error => {
                console.log(error);
            }); // 실패 응답을 받았을 때

    }

    function clearInputs() {
        // replyContent 초기화
        document.querySelector('#replyContent').value = '';
    }

    // 댓글 select문
    function readAllReplies() {
        const postNo = document.querySelector('#postNo').value;// 댓글이 달려있는 글 번호
        let parent = 0; // 댓글 = 0, 대댓글 = 1로 구분

        getPostNo = postNo;
        axios.get('/api/reply/all?postNo=' + postNo +'&parent=' + parent) // Ajax GET 요청 보냄.
            .then(response => { updateReplyList(response.data)})
            .catch(error => {
                console.log(error);
            });
    }


    // 댓글 리스트
    function updateReplyList(data) {
        // 댓글들의 배열(data)을 html 영역에 보일 수 있도록 html 코드를 작성.
        const divReplies = document.querySelector('#replies');

        let str = ''; // div 안에 들어갈 HTML 코드

        for (let r of data) {

            str +=
                '<h6 class="my-2 p-3">' + '작성자 : ' + r.replier + '</h6>'
                + '<div class="d-flex text-muted pt-3">'
                + '<div class="pb-3 mb-0 small 1h-sm border-bottom w-100">'


            // 비밀 체크 했을 때
            if (r.secretReply != false) {

                str +=
                    '<div class="d-flex justify-content-between">'
                    + '<div>'
                    + '<strong class="text-gray-dark  p-4">' + '비밀 댓글입니다.' + '</strong>'
                    + '</div>'
                    + '<div class="inline ">'
                    + '</div>'
                    + '</div>'
                    + '<div class="text-gray-dark my-2 p-4">' + r.modifiedTime + '</div>'

                // 로그인한 유저와 작성자가 같을 경우 [수정] 보이게 하기
                if (r.replier == loginUser) {
                    str += `<button type="button" class="btnModifies btn text-gray"  data-rid="${r.replyNo}">수정하기</button>`
                }

                str += '</div>'
                    + '</div>'

            } else { // 비밀 체크 하지 않을 때
                str +=
                    '<div class="d-flex justify-content-between">'
                    + '<div>'
                    + '<strong class="text-gray-dark  p-4">' + r.replyContent + '</strong>'
                    + '</div>'
                    + `<button type="button" id="likeButton" class="likeButton btn btn-outline-dark my-2"  data-rid="${r.replyNo}" >` + '👍 ' + r.likeCount + '</button>'
                    + '</div>'
                    + '<div class="text-gray-dark my-2 p-4">' + r.modifiedTime + '</div>'

                // 로그인한 유저와 작성자가 같을 경우 [수정] 보이게 하기
                if (r.replier == loginUser) {
                    str += `<button type="button" class="btnModifies btn text-primary"  data-rid="${r.replyNo}">수정하기</button>`
                }

                str += `<button type="button" class="btnReReply btn text-primary" " data-rid="${r.replyNo}">` + '답글보기' + '</button>'

                // 선택한 댓글의 대댓글 작성하기
                if (r.replyNo == getReplyNo) {
                    str +=
                        `<hr />
                                <!-- 대댓글 작성하기 -->
                                  <div class="card border-gray my-5 w-75 container text-center">
                                  
                                        <div class="card-body row  ">
                                            <div class="col-10">
                                                <textarea
                                                    class="form-control"
                                                    id="rereplyContent"
                                                    placeholder="댓글 작성"></textarea>
                                            </div>
                                            <div class="col-2">
                                                <button class="btn btn-primary" id="btnReReplyRegister">등록</button>
                                                <div class="form-inline">
                                                    <div>
                                                        비밀댓글
                                                        <input type="checkbox" id="secretReReply" name="secretReReply" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        
                                    </div>
                                    <hr />
                        <!--대댓글 리스트-->
                        <div id="rereplise" class="my-2 bg-body rounded shadow-sm w-75 container text-center"></div>`
                }
            }
        }
        divReplies.innerHTML = str;


        // 좋아요 버튼
        const likeButton = document.querySelectorAll('.likeButton');
        likeButton.forEach(btn => {
            btn.addEventListener('click', likeCountFunction);
        });

        // 좋아요 카운트 함수
        function likeCountFunction(event) {

            const replyNo = event.target.getAttribute('data-rid');

            // 해당 댓글 아이디의 댓글 객체를 Ajax patch 방식으로 요청.
            axios
                .patch('/api/reply/' + replyNo)
                .then(response => {
                    (response.data);
                    readAllReplies();
                })
                .catch(error => {
                    console.log(error)
                });
        }

        // 대댓글 작성 버튼
        const ReButton = document.querySelectorAll('.btnReReply');
        ReButton.forEach(btn => {
            btn.addEventListener('click', ReReply);
        });

        // [수정] 버튼들이 HTML 요소로 만들어진 이후에, [수정] 버튼에 이벤트 리스너를 등록.
        const buttons = document.querySelectorAll('.btnModifies');
        buttons.forEach(btn => {
            btn.addEventListener('click', getReply);
        });

        function getReply(event) {
            alert('수정완료');
            // console.log(event); // 이벤트가 발생한 타켓 -> 버튼
            // 클릭된 버튼의 data-rid 속성값을 읽음.
            const rid = event.target.getAttribute('data-rid');

            // 해당 댓글 아이디의 댓글 객체를 Ajax GET 방식으로 요청.
            axios
                .get('/api/reply/' + rid)
                .then(response => {
                    showModal(response.data)
                })
                .catch(error => {
                    console.log(error)
                });

        }

        const divModal = document.querySelector('#replyModal');
        const replyModal = new bootstrap.Modal(divModal); // 부트스트랩 Modal 객체 생성.
        const modalreplyId = document.querySelector('#modalreplyId');
        const modalreplyText = document.querySelector('#modalreplyText');
        const modalBtnDelete = document.querySelector('#modalBtnDelete');
        const modalBtnUpdate = document.querySelector('#modalBtnUpdate');


        function showModal(reply) {
            // modal에 댓글 아이디, 댓글 내용 채우기
            modalreplyId.value = reply.replyNo;
            modalreplyText.value = reply.replyContent;

            replyModal.show(); // modal를 보여주기
        }

        modalBtnDelete.addEventListener('click', deleteReply);
        modalBtnUpdate.addEventListener('click', updateReply);

        function deleteReply(event) {
            const replyNo = modalreplyId.value; // 삭제할 댓글 아이디
            const result = confirm('삭제 할까요?');
            if (result) {
                axios
                    .delete('/api/reply/' + replyNo) // Ajax DELETE 요청 전송
                    .then(response => {
                        alert('#' + response.data + ' 댓글 삭제 성공');
                        readAllReplies();
                    }) // HTTP 200 OK 응답
                    .catch(err => {
                        console.log(err)
                    }) // 실패 응답 처리
                    .then(function () { // 성공 또는 실패 처리 후 항상 실행할 코드
                        replyModal.hide();
                    });
            }
        }

        function updateReply(event) {
            const replyNo = modalreplyId.value; // 수정할 댓글 아이디
            const replyContent = modalreplyText.value; // 수정할 댓글 내용

            if (replyContent == '') {
                alert('댓글 내용은 반드시 입력해주세요');
                return;
            }

            const result = confirm('수정 할까요?');

            if (result) {
                const data = {replyContent: replyContent};
                axios
                    .put('/api/reply/' + replyNo, data) // Ajax PUT 요청 전송
                    .then(response => {
                        alert('#' + response.data + ' 댓글 수정 성공');
                        readAllReplies();
                    }) // HTTP 200 OK 응답
                    .catch(err => {
                        console.log(err)
                    }) // 실패 응답 처리
                    .then(function () { // 성공 또는 실패 처리 후 항상 실행할 코드
                        replyModal.hide();
                    });
            }
        }


        // 대댓글 리스트 출력
        function ReReply(event) {
            const replyNo = event.target.getAttribute('data-rid');

            if (getReplyNo != 0) {
                getReplyNo = 0;
                readAllReplies();

            } else {
                getReplyNo = replyNo;
                readAllReReplies();
                readAllReplies();
            }


        }

        // 대댓글 등록 버튼
        const btnReReply = document.querySelector('#btnReReplyRegister');
        btnReReply.addEventListener('click', btnReReplyFunction);

        // 대댓글 등록 함수
        function btnReReplyFunction() {

            // 댓글 작성자 찾음.
            const replier = loginUser;
            // 댓글 내용을 찾음.
            const replyContent = document.querySelector('#rereplyContent').value;

            // 댓글 작성자와 내용은 비어있으면 안됨.
            if (replier == '' || replyContent == '') {
                alert('댓글 내용은 반드시 이름하세요.');
                return;
            }

            //비밀 댓글 체크 여부
            let secretReply = document.getElementById('secretReReply').checked;
            console.log(secretReply);

            // Ajax POST 요청을 보낼 때 서버로 보내는 데이터 작성.
            // java {} 은 배열, javascript {} 은 object.
            const data = {
                postNo: getPostNo, // 댓글이 달릴 포스트 아이디(번호)
                replyContent: replyContent, // 댓글 내용
                replier: replier, // 댓글 작성자
                secretReply: secretReply, // 비밀 댓글
                likeCount: 0, // 좋아요 카운트
                parent: 1 // 대댓글 구분
            };

            console.log(data);
            // Axios 라이브러리를 사용해서 Ajax POST 요청을 보냄.
            axios.post('/api/reply', data)
                .then(response => {
                    console.log(response);
                    alert('# ' + response.data + '댓글 작성 성공');
                    clearInputs(); // 댓글 작성자와 내용을 삭제.
                    readAllReReplies(data); // 댓글 목록을 다시 요청.

                }) // 성공 응답을 받았을 때
                .catch(error => {
                    console.log(error);
                }); // 실패 응답을 받았을 때


        }

        // 대댓글 리스트
        function readAllReReplies() {

            // 선택한 postNO 가져오기
            let postNo = 0;
            postNo = getPostNo;

            // 대댓글 구분하기
            let parent = 1;

            axios
                .get('/api/reply/all?postNo=' + postNo + '&parent=' + parent) // Ajax GET 요청 보냄.
                .then(response => {
                    updateReReplyList(response.data); // 대댓글 리스트 html로 그리기
                })
                .catch(error => {
                    console.log(error);
                });

        }

        function updateReReplyList(data) {

            const divReReplies = document.querySelector('#rereplise');

            let str = ''; // div 안에 들어갈 HTML 코드

            // 비밀 댓글 출력 구분
            for (let r of data) {

                str +=
                    '<hr />'
                    + '<div class="d-flex justify-content-between">'
                    + '<h6 class="my-2 p-2">' + '작성자 : ' + r.replier + '</h6>'

                if (r.secretReply != false) { // 비밀 체크 했을 때

                    str +=
                        '<strong class="text-gray-dark  p-4">' + '비밀 댓글입니다.' + '</strong>'
                        + '<div class="text-gray-dark my-2 p-4">' + r.modifiedTime + '</div>'

                    if (r.replier == loginUser) { // 로그인한 유저와 작성자가 같을때만 보이기
                        str += `<button type="button" class="btnModify btn text-gray"  data-rid="${r.replyNo}">수정하기</button>`
                    }

                    str +=
                        '</div>'
                        + '<hr />'


                } else { // 비밀 체크 하지 않을 때
                    str +=
                        '<strong class="text-gray-dark  p-4">' + r.replyContent + '</strong>'
                        + '<div class="text-gray-dark my-2 p-4">' + r.modifiedTime + '</div>'

                    // 로그인한 유저와 작성자가 같을때만 보이기
                    if (r.replier == loginUser) {
                        str += `<button type="button" class="btnModify btn text-primary"  data-rid="${r.replyNo}">수정하기</button>`
                    }

                    str +=
                        '</div>'
                        + '<hr />'
                }
            }
            divReReplies.innerHTML = str;

            // [수정] 버튼들이 HTML 요소로 만들어진 이후에, [수정] 버튼에 이벤트 리스너를 등록.
            const button = document.querySelectorAll('.btnModify');
            button.forEach(btn => {
                btn.addEventListener('click', getReply);
            });
        }

    }


});

