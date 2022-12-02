/**
 * 사용자 프로필 정보 수정
 */
const form = document.getElementById("formModify");
const passwordMatched = document.querySelector('#passwordMatched');
const passwordMismatched = document.querySelector('#passwordMismatched');
const btnSubmit = document.querySelector('#btnSubmit');
const passwordConfirm = document.querySelector('#passwordConfirm');
const password = document.querySelector('#password');
const passwordCheck = document.querySelector('#passwordCheck');

//비밀번호 일치, 불일치 체크
function passwordValueCheck() {
    if (form.password.value == form.passwordConfirm.value) {
        console.log("일치")
        passwordMatched.className = "my-2";
        passwordMismatched.className = 'my-2 d-none';
        btnSubmit.classList.remove('disabled');
    } else {
        console.log("불일치")
        passwordMatched.className = 'my-2 d-none';
        passwordMismatched.className = 'my-2';
        btnSubmit.classList.add('disabled');
    }
}

password.addEventListener('change', function () {
    if (password.value.length < 8 || password.value.length > 20) {
        passwordCheck.className = "my-2";
        passwordConfirm.disabled = true;
    } else {
        passwordCheck.className = "my-2 d-none";
        passwordConfirm.disabled = false;
        passwordValueCheck();
    }
})

passwordConfirm.addEventListener('change', function () {
    passwordValueCheck();
})

form.addEventListener("submit", function (e) {
    if (form.name.value == '' || form.nickname.value == '' || form.address.value == '' || form.phone.value == '' || form.email.value == '') {
        alert('사용자 정보를 모두 입력해주세요');
        e.preventDefault();
        form.name.focus();
        return;
    }

    const result = confirm("정말 수정하시겠습니까?");
    if (result) {
        form.action = '/mypage/update';
        form.method = 'post';
        form.submit();
    }
})