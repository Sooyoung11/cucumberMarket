/**
 * join.js
 * /member/join.html
 */

window.addEventListener('DOMContentLoaded', function () {

    const btnModifySubmit = document.querySelector('#btnModifySubmit');

    // 아이디 체크
    const memberIdInput = document.querySelector('#memberId');
    const memberIdOk = document.querySelector('#memberIdOk');
    const memberIdNok = document.querySelector('#memberIdNok');

    memberIdInput.addEventListener('change', function () {
        const memberId = memberIdInput.value;
        console.log(memberId);
        axios
        .get('/member/check_memberid?memberId='+ memberId)
        .then(res => { displayCheckMemberId(res.data) })
        .catch(err => { console.log(err); });
    });

    function displayCheckMemberId(data) {
        if (data == 'memberIdOk') {
            memberIdOk.className = '';
            memberIdNok.className = 'd-none';
            btnModifySubmit.classList.remove('disabled');
        } else {
            memberIdOk.className = 'd-none';
            memberIdNok.className = '';
            btnModifySubmit.classList.add('disabled');
        }
    }

    // 비밀번호 체크
    const passwordInput = document.querySelector('#password');
    const passwordOk = document.querySelector('#passwordOk');
    const passwordNok = document.querySelector('#passwordNok');
    const passwordNok2 = document.querySelector('#passwordNok2');

    passwordInput.addEventListener('change', function () {
        const password = passwordInput.value;
        axios
        .get('/member/check_password?password='+ password)
        .then(res => { displayCheckPassword(res.data) })
        .catch(err => { console.log(err); });
    });

    function displayCheckPassword(data) {
        if (data == 'passwordOk') {
            passwordOk.className = '';
            passwordNok.className = 'd-none';
            passwordNok2.className = 'd-none';
            btnModifySubmit.classList.remove('disabled');
        } else {
            passwordOk.className = 'd-none';
            passwordNok.className = '';
            passwordNok2.className = '';
            btnModifySubmit.classList.add('disabled');
        }
    }

    // 비밀번호 재확인 체크
    const password2Input = document.querySelector('#password2');
    const password2Ok = document.querySelector('#password2Ok');
    const password2Nok = document.querySelector('#password2Nok');
    const password2Nok2 = document.querySelector('#password2Nok2');

    password2Input.addEventListener('change', function () {
        const password= passwordInput.value;
        const password2 = password2Input.value;
        axios
        .get('/member/check_password2?password='+password+'&&password2='+ password2)
        .then(res => { displayCheckPassword2(res.data) })
        .catch(err => { console.log(err); });
    });

    function displayCheckPassword2(data) {
        if (data == 'password2Ok') {
            password2Ok.className = '';
            password2Nok.className = 'd-none';
            password2Nok2.className = 'd-none';
            btnModifySubmit.classList.remove('disabled');
        } else {
            password2Ok.className = 'd-none';
            password2Nok.className = '';
            password2Nok2.className = '';
            btnModifySubmit.classList.add('disabled');
        }
    }

    // 닉네임 체크
    const nicknameInput = document.querySelector('#nickname');
    const nicknameOk = document.querySelector('#nicknameOk');
    const nicknameNok = document.querySelector('#nicknameNok');

    nicknameInput.addEventListener('change', function () {
        const nickname = nicknameInput.value;
        console.log(nickname);
        axios
        .get('/member/check_nickname?nickname='+ nickname)
        .then(res => { displayCheckNickname(res.data) })
        .catch(err => { console.log(err); });
    });

    function displayCheckNickname(data) {
        if (data == 'nicknameOk') {
            nicknameOk.className = '';
            nicknameNok.className = 'd-none';
            btnModifySubmit.classList.remove('disabled');
        } else {
            nicknameOk.className = 'd-none';
            nicknameNok.className = '';
            btnModifySubmit.classList.add('disabled');
        }
    }

    // 이메일 체크
    const emailInput = document.querySelector('#email');
    const emailOk = document.querySelector('#emailOk');
    const emailNok = document.querySelector('#emailNok');
    const emailNok2 = document.querySelector('#emailNok2');
    const btnAuthcode= document.querySelector('#btnAuthcode');
    const email = emailInput.value;

    emailInput.addEventListener('change', function () {
        axios
        .get('/member/check_email?email='+ email)
        .then(res => { displayCheckEmail(res.data) })
        .catch(err => { console.log(err); });
    });

    function displayCheckEmail(data) {
        if (data == 'emailOk') {
            emailOk.className = '';
            emailNok.className = 'd-none';
            emailNok2.className = 'd-none';
            btnAuthcode.classList.remove('disabled');
            btnAuthcode.classList.remove('btn-outline-success');
            btnAuthcode.classList.add('btn-success');
            btnModifySubmit.classList.remove('disabled');
        } else {
            emailOk.className = 'd-none';
            emailNok.className = '';
            emailNok2.className = '';
            btnAuthcode.classList.remove('btn-success');
            btnAuthcode.classList.add('disabled');
            btnAuthcode.classList.add('btn-outline-success');
            btnModifySubmit.classList.add('disabled');
        }
    }

    btnModifySubmit.addEventListener('click', function (e) {
        e.preventDefault();
        const result = confirm("정말 수정하시겠습니까?");
        if(result){

        }
    })

    const btnPostcode = document.querySelector('#btnPostcode');
    btnPostcode.addEventListener('click', sample4_execDaumPostcode);
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
// 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수
// 법정동명이 있을 경우 추가한다. (법정리는 제외)
// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
// 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }
// 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
// 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_extraAddress").value = '';
                }
                var guideTextBox = document.getElementById("guide");
// 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';
                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }

    function sendEmail(email){

    }




});