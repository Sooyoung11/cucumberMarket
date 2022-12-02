const modifyButton = document.querySelector("#modifyButton");
modifyButton.addEventListener("click", function () {
  const title = document.querySelector("#title").value;
  const postContent = document.querySelector("#postContent").value;

  if (title == "" && postContent == "") {
    alert("제목과 내용을 입력해주세요.");
    return;
  } else if (title == "") {
    alert("제목을 입력해주세요.");
    return;
  } else if (postContent == "") {
    alert("내용을 입력해주세요.");
    return;
  }

  const check = confirm("수정하시겠습니까?");

  if (check) {
    // 사용자가 '확인'을 선택했을 때
    form.action = "/post/modify"; // 제출 요청 주소
    form.method = "post"; // 제출 요청 방식
    form.submit(); // 서버로 제출
  }
});

const image1 = document.querySelector("#image1");
image1.addEventListener("click", function () {
  let deleteModifyNav = document.querySelector("#deleteModifyNav");
  if (deleteModifyNav.style.display == "none") {
    deleteModifyNav.style.display = "block";
  } else {
    deleteModifyNav.style.display = "none";
  }
});

const deleteImageButton = document.querySelector("#deleteImageButton");
deleteImageButton.addEventListener("click", function () {
  let image1src = image1.src;
  image1src = image1src.substr(28);
  console.log(image1src);

  axios
    .delete("/post/reply/" + image1src)
    .then((response) => {
      console.log(response);
    })
    .catch((error) => {
      console.log(error);
    });
});
