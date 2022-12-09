/**
 * mypage interested Count, ,,,
 */


    console.log("mypageJS 들어옴!!");
    const memberNo = document.querySelector('#memberNo').value;
    console.log("memberNo="+memberNo);
    const myInterestedList = document.querySelector('#myInterestedList');
    countMyInterestedList(memberNo);

    function countMyInterestedList(memberNo){
        axios.get('/api/mypage/count/interested/'+memberNo)
            .then(response => {
                console.log("interestedCount"+response.data);
                myInterestedList.value = response.data;
            })
            .catch(error =>{
                console.log(error);
            })
    }

