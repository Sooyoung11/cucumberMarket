/**
 * mypage interested Count, ,,,
 */

    const memberNo = document.querySelector('#memberNo').value;
    console.log("memberNo="+memberNo);

    //찜목록 불러오기
    const myInterestedList = document.querySelector('#myInterestedList');
    countMyInterestedList(memberNo);

    //판매목록(진행중) 불러오기
    const proceedingList = document.querySelector('#proceedingList');
    countProceedingList(memberNo);

    //판매목록(거래완료) 불러오기
    const completedList = document.querySelector('#completedList');
    countCompletedList(memberNo);

    //판매목록(거래완료) count 불러오는 함수
    function countCompletedList(memberNo){
        axios.get('/api/mypage/count/completed/'+memberNo)
            .then(response => {
                console.log("completedCount" +response.data);
                completedList.value = response.data;
            })
            .catch(error =>{
                console.log(error);
            })
    }

    //판매목록(진행중) count 불러오는 함수
    function countProceedingList(memberNo){
        axios.get('/api/mypage/count/proceeding/'+ memberNo)
            .then(response => {
                console.log("proceedingCount" + response.data);
                proceedingList.value = response.data;
            })
            .catch(error=>{
                console.log(error);
            })

    }

    //찜목록 count 불러오는 함수
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

