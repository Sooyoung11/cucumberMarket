
window.addEventListener('DOMContentLoaded', function() {

    const interested     = document.querySelector("#interested");
    const nointerested   = document.querySelector("#nointerested");

    checkIsInterestedProduct();

    nointerested.addEventListener('click', function () {
        axios
        .get('/product/addInterested?memberNo=' + memberNo + '&productNo=' + productNo)
        .then(response => {
            checkIsInterestedProduct();
            let result = confirm("관심 목록에 추가되었습니다. 관심 목록 페이지로 가시겠습니까?");
            console.log(result);
            if (result) {
                window.location.href = "/product/interested?memberNo=" + memberNo;
            }
         })
        .catch(err => { console.log(err); });
    });

    interested.addEventListener('click', function () {
        axios
        .delete('/product/deleteInterested?memberNo=' + memberNo + '&productNo=' + productNo)
        .then(response => {
            checkIsInterestedProduct();
        })
        .catch(err => { console.log(err); });
    });

    function checkIsInterestedProduct() { // 관심 목록에 있는지 체크해서 별모양 이미지 바꿔 주기
        axios
            .get('/product/checkInterestedProduct?memberNo=' + memberNo + '&productNo=' + productNo)
            .then(response => {
                console.log(response.data);

                if(response.data == 'ok') {
                    interested.className = 'col my-2';
                    nointerested.className = 'col my-2 d-none';
                }else if (response.data == 'nok') {
                    interested.className = 'col my-2 d-none';
                    nointerested.className = 'col my-2';
                }
            })
            .catch(err => { console.log(err); });
    }


})