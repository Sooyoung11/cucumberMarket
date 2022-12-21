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



    const createBtn = document.querySelector('#createBtn')
    createBtn.addEventListener('click', function() {
    const title = document.querySelector('#title').value;
    const content = document.querySelector('#content').text;
    const price = document.querySelector('#price').value;
    const category = document.querySelector('#category').value;
    if  (title === '') {
        alert('제목을 입력해주세요..')
        return;
    } else if (content === '') {
        alert('내용을 입력해주세요.')
        return;
    } else if (price === '') {
        alert('가격을 입력해주세요.')
        return;
    }else if (category === 'disabled') {
        alert('카테고리를 입력해주세요.')
        return;
    }

});

    const cancelCreate = document.querySelector('#cancelCreate');
    cancelCreate.addEventListener('click', function () {
        const result = confirm('등록이 취소되었습니다.')
});