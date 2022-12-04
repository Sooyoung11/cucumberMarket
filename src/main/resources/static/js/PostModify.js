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
  let deleteModifyImage01Nav = document.querySelector(
    "#deleteModifyImage01Nav"
  );
  if (deleteModifyImage01Nav.style.display == "none") {
    deleteModifyImage01Nav.style.display = "block";
  } else {
    deleteModifyImage01Nav.style.display = "none";
  }
});

const image2 = document.querySelector("#image2");
image2.addEventListener("click", function () {
  let deleteModifyImage02Nav = document.querySelector(
    "#deleteModifyImage02Nav"
  );
  if (deleteModifyImage02Nav.style.display == "none") {
    deleteModifyImage02Nav.style.display = "block";
  } else {
    deleteModifyImage02Nav.style.display = "none";
  }
});

const deleteImage01Button = document.querySelector("#deleteImage01Button");
deleteImage01Button.addEventListener("click", function () {
  let imageSrc = image1.src;
  console.log(imageSrc);
  imageSrc = imageSrc.substr(28);
  console.log(imageSrc);

  axios
    .delete("/post/reply/" + imageSrc)
    .then((response) => {
      alert("사진이 삭제되었습니다.");
      location.reload();
    })
    .catch((error) => {
      console.log(error);
    });
});

const deleteImage02Button = document.querySelector("#deleteImage02Button");
deleteImage02Button.addEventListener("click", function () {
  let imageSrc = image2.src;
  console.log(imageSrc);
  imageSrc = imageSrc.substr(28);
  console.log(imageSrc);

  axios
    .delete("/post/reply/" + imageSrc)
    .then((response) => {
      alert("사진이 삭제되었습니다.");
      location.reload();
    })
    .catch((error) => {
      console.log(error);
    });
});
