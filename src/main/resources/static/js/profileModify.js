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
const totalAddress = document.querySelector('#totalAddress');
const detailAddress = document.getElementById("sample4_detailAddress");


const btnPostcode = document.querySelector('#btnPostcode');
btnPostcode.addEventListener('click', sample4_execDaumPostcode);
passwordValueCheck();

//본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
function sample4_execDaumPostcode() {

    const postcode = document.getElementById('sample4_postcode');
    const roadAddress = document.getElementById("sample4_roadAddress");
    const jibunAddress = document.getElementById("sample4_jibunAddress");

    postcode.className="form-control my-2";
    roadAddress.className="form-control my-2";
    jibunAddress.className="form-control my-2";
    detailAddress.className="form-control my-2";
    totalAddress.className="form-control d-none";

    new daum.Postcode({
        oncomplete: function(data) {

            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }

            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }

            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            postcode.value = data.zonecode;
            roadAddress.value = roadAddr;
            jibunAddress.value = data.jibunAddress;
            //totalAddress.value = data.roadnameCode+" "+ data.roadAddress+ " ";
            totalAddress.value = data.jibunAddress;
            console.log("totalAddress"+totalAddress);

            if(roadAddr !== ''){
                document.getElementById("sample4_extraAddress").value = '';
            } else {
                document.getElementById("sample4_extraAddress").value = '';
            }
            var guideTextBox = document.getElementById("guide");

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
// 이메일 체크
const emailInput = document.querySelector('#email');
const emailOk = document.querySelector('#emailOk');
const emailNok = document.querySelector('#emailNok');
const emailNok2 = document.querySelector('#emailNok2');
const btnAuthcode= document.querySelector('#btnAuthcode');

emailInput.addEventListener('change', function () {
    const email= emailInput.value;
    console.log(email);
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
        btnSubmit.classList.remove('disabled');
    } else {
        emailOk.className = 'd-none';
        emailNok.className = '';
        emailNok2.className = '';
        btnAuthcode.classList.add('disabled');
        btnSubmit.classList.add('disabled');
    }
}
const emailKeyInput= document.querySelector('#emailKey');
// 인증코드 발송
let authCode= '';
btnAuthcode.addEventListener('click', function(){
    emailKeyInput.type='text';
    axios
        .get('/member/sendEmail?email='+emailInput.value)
        .then(function(res){
            console.log(res.data);
            authCode= res.data;
            console.log('sendEmail=> authCode='+authCode);
        })
        .catch(err => { console.log(err); });
})

// 인증코드 체크

const emailKeyOk = document.querySelector('#emailKeyOk');
const emailKeyNok = document.querySelector('#emailKeyNok');
const emailKeyNok2 = document.querySelector('#emailKeyNok2');

emailKeyInput.addEventListener('change', function () {
    console.log('emailAuth=> authCode='+authCode)
    const emailKey= emailKeyInput.value;
    axios
        .get('/member/emailAuth?authCode='+authCode+'&&emailKey='+emailKey)
        .then(res => { displayCheckEmailKey(res.data) })
        .catch(err => { console.log(err); });
});

function displayCheckEmailKey(data) {
    if (data == 'emailKeyOk') {
        emailKeyOk.className = '';
        emailKeyNok.className = 'd-none';
        emailKeyNok2.className = 'd-none';
        btnSubmit.classList.remove('disabled');
    } else {
        emailKeyOk.className = 'd-none';
        emailKeyNok.className = '';
        emailKeyNok2.className = '';
        btnSubmit.classList.add('disabled');
    }
}

form.addEventListener("submit", function (e) {
    if (form.name.value == '' || form.nickname.value == '' || totalAddress == '' || form.phone.value == '' || form.email.value == '') {
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


