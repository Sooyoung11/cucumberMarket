window.addEventListener("DOMContentLoaded", (event) => {
  // Toggle the side navigation
  const insertPostButton = document.querySelector("#insertPostButton");

  insertPostButton.addEventListener("click", checkTexts);
  function checkTexts() {
    const title = document.querySelector("#title").value;
    const content = document.querySelector("#postContent").value;
    console.log(content);

    if (title == "") {
      alert("제목을 입력해주세요.");
      return;
    }
    if (content == "") {
      alert("내용을 입력해주세요.");
      return;
    }
    const form = document.querySelector("#form");
    form.action = "/post/create";
    form.method = "post";
    form.submit();
  }

  const chooseFile = document.querySelector("#chooseFile");
  chooseFile.addEventListener("change", function () {
    let file = chooseFile.files;
    console.log(file.length);
    if (file.length == 3) {
      alert("사진은 2장까지 가능합니다!!");
      return;
    }
  });
});
