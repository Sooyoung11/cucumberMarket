// 파일 확장자
function setThumbnail(event) {
    pathpoint = event.value.lastIndexOf('.');
    filepoint = event.value.substring(pathpoint + 1, event.length);
    filetype = filepoint.toLowerCase();
    if (filetype == 'jpg' || filetype == 'png' || filetype == 'jpeg') {

        return true;
    } else {
        alert('첨부파일은 jpg,png,jpeg로 된 이미지만 가능합니다.')
        parentObj = event.parentNode
        node = parentObj.replaceChild(event.cloneNode(true), event);
        return false;
    }

}