window.addEventListener("DOMContentLoaded", (event) => {
  // Toggle the side navigation
  const form = document.querySelector("#form");
  // 삭제 버튼 찾아서 이벤트 리스너 등록
  const btnDelete = document.querySelector("#delete");
  btnDelete.addEventListener("click", function () {
    const result = confirm("정말 삭제하시겠습니까?");
    if (result) {
      form.action = "/post/delete";
      form.method = "post";
      form.submit();
    }
  });

  const btnUpdate = document.querySelector("#modify");
  btnUpdate.addEventListener("click", function () {
    form.action = "/post/modify"; // 제출 요청 주소
    form.method = "get"; // 제출 요청 방식
    form.submit(); // 서버로 제출
  });
});

const gotoList = document.querySelector("#gotoList");
gotoList.addEventListener("click", function () {
  form.action = "/post/list"; // 제출 요청 주소
  form.method = "get"; // 제출 요청 방식
  form.submit(); // 서버로 제출
});
