window.addEventListener("DOMContentLoaded", (event) => {
    const form = document.querySelector("#form");

    const detailDelete = document.querySelector('#detailDelete');
    detailDelete.addEventListener('click', function () {

    const result = confirm('삭제하시겠습니까?')
        if(result) {
            form.action = '/product/delete';//
            form.method = 'post';
            form.submit();
        }
    });

    const detailUpdate = document.querySelector('#detailUpdate');
    detailUpdate.addEventListener('click', function() {
        form.action = '/product/modify';
        form.method = 'get';
        form.submit();
    });
});