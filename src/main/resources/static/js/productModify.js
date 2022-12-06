
const form = document.querySelector('#formModify');
const updateButton = document.querySelector('#updateButtonOk')
updateButton.addEventListener('click', function(){
    const title = document.querySelector('#title').value;
    const content = document.querySelector('#content').value;
    const price = document.querySelector('#price').value;
    const category = document.querySelector('#category').value;
    if(title == '' || content == '' || price == '' || category == '') {
            alert('전부 입력해주세요.')
            return;
    }
    const result = confirm('삭제하시겠습니까?')
    if(result) {
        form.action = '/product/update';
        form.method = 'post';
        form.submit();
    }

});