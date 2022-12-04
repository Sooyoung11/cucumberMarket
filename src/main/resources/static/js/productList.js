
window.addEventListener('DOMContentLoaded', function() {

    const interested     = document.querySelector("#interested");
    const nointerested   = document.querySelector("#nointerested");

//    axios
//    .get('/product/check?memberNo=' + memberNo + '&productNo=' + productNo)
//    .then(response => {
//        console.log(response);
//    })
//    .catch(err => { console.log(err); });


    nointerested.addEventListener('click', function () {
        axios
        .get('/product/addInterested?memberNo=' + memberNo + '&productNo=' + productNo)
        .then(response => {
            interested.className = 'col my-2';
            nointerested.className = 'col my-2 d-none';
         })
        .catch(err => { console.log(err); });
    });

    interested.addEventListener('click', function () {
        axios
        .delete('/product/deleteInterested?memberNo=' + memberNo + '&productNo=' + productNo)
        .then(response => { console.log(response); })
        .catch(err => { console.log(err); });
    });

})