// 파일 확장자
function fileCheck(event) {
    pathpoint = event.value.lastIndexOf('.');
    filepoint = event.value.substring(pathpoint+1, event.length);
    filetype=filepoint.toLowerCase();
    if(filetype=='jpg'||filetype=='png'||filetype=='jpeg'){
    } else {
        alert('첨부파일은 jpg,png,jpeg로 된 이미지만 가능합니다.')
        parentObj = event.parentNode
        node = parentObj.replaceChild(event.cloneNode(true),event);
        return;
    }








    // 이미지 미리보기
    // let i = event.target.files.length-1;
    // for(let image of event.target.files){
    //     let img = document.createElement("img");
    //     const reader = new FileReader();
    //     reader.onload = function (event){
    //         img.setAttribute("src",event.target.result);
    //     }
    //     reader.readAsDataURL(event.target.files[i--]);
    //     document.querySelector("#imgUpload").appendChild(img);
    // }
}

