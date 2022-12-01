const modifyButton = document.querySelector("#modifyButton");
modifyButton.addEventListener("click", function () {
  const title = document.querySelector("#title").value;
  const postContent = document.querySelector("#postContent").value;

  if (title == "" || postContent == "") {
    alert("제목과 내용을 입력해주세요");
    return;
  }

  const check = confirm("정말 수정해?");

  if (check) {
    // 사용자가 '확인'을 선택했을 때
    form.action = "/post/modify"; // 제출 요청 주소
    form.method = "post"; // 제출 요청 방식
    form.submit(); // 서버로 제출
  }
});
