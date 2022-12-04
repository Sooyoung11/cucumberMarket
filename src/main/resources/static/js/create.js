window.addEventListener("DOMContentLoaded", (event) => {
  // Toggle the side navigation
  const insertPostButton = document.querySelector("#insertPostButton");

  insertPostButton.addEventListener("click", checkTexts);
  function checkTexts() {
    const title = document.querySelector("#title").value;
    const content = document.querySelector("#postContent").innerText;
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


  function fileUpload(){
		var fileInput = document.querySelector("#chooseFile");

		for( var i=0; i<fileInput.length; i++ ){
			if( fileInput[i].files.length > 0 ){
				for( var j = 0; j < fileInput[i].files.length; j++ ){
					console.log(fileInput[i].files[j].name); // 파일명 출력
				}
			}
		}

	}
 

});
