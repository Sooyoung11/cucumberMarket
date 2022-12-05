/**
 * join.js
 * /member/join.html
 */

window.addEventListener('DOMContentLoaded', function () {

    const btnSubmit = document.querySelector('#btnSubmit');

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
            btnSubmit.classList.remove('disabled');
        } else {
            memberIdOk.className = 'd-none';
            memberIdNok.className = '';
            btnSubmit.classList.add('disabled');
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
            btnSubmit.classList.remove('disabled');
        } else {
            passwordOk.className = 'd-none';
            passwordNok.className = '';
            passwordNok2.className = '';
            btnSubmit.classList.add('disabled');
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
            btnSubmit.classList.remove('disabled');
        } else {
            password2Ok.className = 'd-none';
            password2Nok.className = '';
            password2Nok2.className = '';
            btnSubmit.classList.add('disabled');
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
            btnSubmit.classList.remove('disabled');
        } else {
            nicknameOk.className = 'd-none';
            nicknameNok.className = '';
            btnSubmit.classList.add('disabled');
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
            btnSubmit.classList.remove('disabled');
        } else {
            emailOk.className = 'd-none';
            emailNok.className = '';
            emailNok2.className = '';
            btnAuthcode.classList.remove('btn-success');
            btnAuthcode.classList.add('disabled');
            btnAuthcode.classList.add('btn-outline-success');
            btnSubmit.classList.add('disabled');
        }
    }

    function sendEmail(email){

    }



});