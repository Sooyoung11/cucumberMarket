window.addEventListener("DOMContentLoaded", function () {
  const interested = document.querySelector("#interested"); // 노란색 별 모양
  const nointerested = document.querySelector("#nointerested"); // 빈 별 모양
  const dealStatus = document.querySelector("#dealStatus"); // select 박스 감싸고 있는 div -> display 설정
  const selectOption = document.querySelector("#selectOption"); // select 박스
  const btnChat = document.querySelector("#btnChat"); // 채팅하기 버튼
  const btnUpdateAndDelete = document.querySelector("#btnUpdateAndDelete"); // 수정 삭제 버튼

  const memberAddress = document.querySelector("#memberAddress"); // 상품 등록자의 주소
  let memberAddressSplit = memberAddress.innerText.split(" "); // 주소 자르고
  memberAddress.innerHTML = memberAddressSplit[0] + ' ' + memberAddressSplit[1]; // 값 넣기


//    console.log(new Date());
//    let createTime = document.querySelector("#createTime");
//    console.log(createTime.innerText);

  if (memberNo == productMemberNo) {
    // 로그인한 no와 상품 등록자의 no가 같으면
    dealStatus.className = "row mb-3"; // 거래 상테 select 보임
    btnUpdateAndDelete.className = "my-2"; // 수정 삭제 보임
    isDealStatus(); // 거래 상태 체크해서 거래 중 or 거래 완료 보여줌
  } else {
    btnChat.className = "my-2 text-center"; // 채팅하기 버튼 보임
    checkIsInterestedProduct(); // 별 모양 처리
  }

  nointerested.addEventListener("click", function () {
    axios
      .get("/product/addInterested?memberNo=" + memberNo + "&productNo=" + productNo)
      .then((response) => {
        checkIsInterestedProduct();
        alert("관심 목록에 추가되었습니다.");
      })
      .catch((err) => {
        console.log(err);
      });
  });

  interested.addEventListener("click", function () {
    axios
      .delete("/product/deleteInterested?memberNo=" + memberNo + "&productNo=" + productNo)
      .then((response) => {
        checkIsInterestedProduct();
      })
      .catch((err) => {
        console.log(err);
      });
  });

  selectOption.addEventListener("change", function () {
    changeDealStatus();
  });

  function checkIsInterestedProduct() {
    // 관심 목록에 있는지 체크해서 별모양 이미지 바꿔 주기
    axios
      .get("/product/checkInterestedProduct?memberNo=" + memberNo + "&productNo=" + productNo)
      .then((response) => {
        console.log(response.data);

        if (response.data == "ok") {
          interested.className = "col my-2";
          nointerested.className = "col my-2 d-none";
        } else if (response.data == "nok") {
          interested.className = "col my-2 d-none";
          nointerested.className = "col my-2";
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }

  function changeDealStatus() {
    let option = selectOption.value;


    if (option == "ing") { // 거래 중
      axios
        .get("/product/ing?productNo=" + productNo)
        .then((response) => {
          isDealStatus();
        })
        .catch((err) => {
          console.log(err);
        });
    } else if (option == "done") { // 거래 완료

      let boughtMemberNo = 0; // 상품 구매자

      let _left = Math.ceil((window.screen.width - 500) / 2);
      let _top = Math.ceil((window.screen.height - 600) / 2);
      window.open( "/chat/productChatList?memberNo=" + memberNo, "PopupNew", "width=500,height=600,left=" + _left + ",top=" + _top );

    }
  }

  window.parentFunction = function (memberNo) {
      console.log(memberNo);
      let boughtMemberNo = memberNo;

      axios
      .get( "/product/done?productNo=" + productNo +  "&boughtMemberNo=" +boughtMemberNo)
      .then((response) => {
        isDealStatus();
      })
      .catch((err) => {
        console.log(err);
      });
  }

  function isDealStatus() {
    // 상품의 거래 상태 체크
    let str = "";

    axios
      .get("/product/isDealStatus?productNo=" + productNo)
      .then((response) => {
        console.log(response.data);
        if (response.data == "nok") { // 거래 중(null)인 경우
          str =
            '<option value="ing" selected>거래 중</option>' +
            '<option value="done">거래 완료</option>';
        } else if (response.data == "ok") { // 거래 완료(not null)인 경우
          str =
            '<option value="ing">거래 중</option>' +
            '<option value="done" selected>거래 완료</option>';
        }

        selectOption.innerHTML = str;
      })
      .catch((err) => {
        console.log(err);
      });
  }
});
