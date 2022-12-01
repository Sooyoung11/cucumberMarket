window.addEventListener("DOMContentLoaded", (event) => {
  // Toggle the side navigation
  const insertPostButton = document.querySelector("#insertPostButton");

  insertPostButton.addEventListener("click", checkTexts);
  function checkTexts() {
    const title = document.querySelector("#title").value;
    const content = document.querySelector("#postContent").value;

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
});
