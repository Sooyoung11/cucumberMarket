/**
 * mylist 거래중, 거래완료, 전체보기
 */

window.addEventListener('DOMContentLoaded', ()=>{
    console.log("myProductList.js 들어옴!!")

    const myProductListSelect = document.querySelector('#myProductListSelect');

    myProductListSelect.addEventListener('change' ,function (event) {
        let selectedOption = myProductListSelect.options[myProductListSelect.selectedIndex].value;
        console.log("selectedValue: "+ selectedOption);
        if (selectedOption == 1){
            axios.get('/product/myList')
                .then(response =>{
                    console.log(response);
                })
                .catch(error => {
                    console.log(error)
                })

        } else if(selectedOption ==2){

        } else{

        }
    });
})




