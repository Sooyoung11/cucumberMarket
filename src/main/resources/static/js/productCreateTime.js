window.addEventListener("DOMContentLoaded", function () {

    createTimeCheck();
    function createTimeCheck() { // 등록 시간 처리
        let createTime      = document.querySelector("#createTime");
        let createTimeValue = createTime.innerText.split("-");
        let createDate      = new Date(createTimeValue[0], createTimeValue[1]-1, createTimeValue[2], createTimeValue[3], createTimeValue[4], createTimeValue[5]); // 글 작성 시간
        let nowDate         = new Date(); // 현재 시간

        let diff        = nowDate.getTime() - createDate.getTime(); // 빼기
        let diffSec     = Math.floor(diff / 1000); // 초단위로
        let diffMin     = Math.floor(diff / 1000 / 60); // 분단위로
        let diffHour    = Math.floor(diff / 1000 / 60 / 60); // 시단위로
        let diffDay     = Math.floor(diff / 1000 / 60 / 60 / 24); // 일단위
        let diffYear    = Math.floor(diff / 1000 / 60 / 60 / 24 / 365); // 년단위

        if (diffMin < 1) { return createTime.innerText = '방금전' };  // 등록 시간이 1분 전이면
        if (diffMin < 60) { return createTime.innerText = diffMin + '분전' }; // 등록 시간이 1시간 전이면
        if (diffHour < 24) { return createTime.innerText = diffHour + '시간전' }; // 등록 시간이 24시간 전이면
        if (diffDay < 365) { return createTime.innerText = diffDay + '일전' }; // 등록 시간이 365일 전이면

        return createTime.innerText = diffYear + '년전'; // 등록 시간이 365일이 지났으면
    }


});
