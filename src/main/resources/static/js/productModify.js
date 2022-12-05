/**
 * productModify.js
 * 상품 관련 Ajax 기능 구현
 * /product/modify.html 에 포함.
 */

window.addEventListener('DOMContentLoaded', () =>{



const formModify = document.querySelector('#formModify');

//삭제버튼
const productBtnDelete = document.querySelector('#productBtnDelete');
productBtnDelete.addEventListener('click', function() {
    const result = confirm('정말 삭제하시겠습니까?');
    if(result) {
        formModify.action ='/product/delete';
        formModify.method = 'post';
        formModify.submit();
    }
});

//수정버튼
const productBtnUpdate = document.querySelector('#productBtnUpdate');
productBtnUpdate.addEventListener('click', function() {
    const title = document.querySelector('#title').value;
    const content = document.querySelector('#content').value;

const result = confirm("정말 수정하시겠습니까?");
if(result) {
    formModify.action = 'product/update';
    formModify.method = 'post';
    formModify.submit();
}
});

});