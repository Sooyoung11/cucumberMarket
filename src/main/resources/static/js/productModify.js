const form = document.querySelector('#formModify');
const updateButton = document.querySelector('#updateButtonOk')
updateButton.addEventListener('click', function(){
    const title = document.querySelector('#title').value;
    const content = document.querySelector('#content').text;
    const price = document.querySelector('#price').value;
    const category = document.querySelector('#category').value;
    if (title === '') {
        alert('제목을 입력해주세요..')
        return;
    } else if (content === '') {
        alert('내용을 입력해주세요.')
        return;
    } else if (price === '') {
        alert('가격을 입력해주세요.')
        return;
    } else if (category === '') {
        alert('카테고리를 입력해주세요.')
        return;
    }
    const result = confirm('수정하시겠습니까?')
    if(result) {
        form.action = '/product/update';
        form.method = 'post';
        form.submit();
    }

});