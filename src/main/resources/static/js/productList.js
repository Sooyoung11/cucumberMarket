window.addEventListener("DOMContentLoaded", function () {
  const interested = document.querySelector("#interested"); // 노란색 별 모양
  const nointerested = document.querySelector("#nointerested"); // 빈 별 모양
  const dealStatus = document.querySelector("#dealStatus"); // select 박스 감싸고 있는 div -> display 설정
  const selectOption = document.querySelector("#selectOption"); // select 박스
  const btnChat = document.querySelector("#btnChat"); // 채팅하기 버튼
  const btnUpdateAndDelete = document.querySelector("#btnUpdateAndDelete"); // 수정 삭제 버튼

  // 기본 위치(top)값
  // var floatPosition = parseInt($(".sideBanner").css('top'))
  //
  // // scroll 인식
  // $(window).scroll(function() {
  //   alert('하이');
  //
  //   // 현재 스크롤 위치
  //   var currentTop = $(window).scrollTop();
  //   var bannerTop = currentTop + floatPosition + "px";
  //
  //   //이동 애니메이션
  //   $(".sideBanner").stop().animate({
  //     "top" : bannerTop
  //   }, 500);
  //
  // }).scroll();


  if (memberNo == productMemberNo) {
    // 로그인한 no와 상품 등록자의 no가 같으면
    dealStatus.className = "row"; // 거래 상테 select 보임
    btnUpdateAndDelete.className = "my-2"; // 수정 삭제 보임
    isDealStatus(); // 거래 상태 체크해서 거래 중 or 거래 완료 보여줌
  } else {
    btnChat.className = "my-2"; // 채팅하기 버튼 보임
    checkIsInterestedProduct(); // 별 모양 처리
  }

  nointerested.addEventListener("click", function () {
    axios
      .get(
        "/product/addInterested?memberNo=" +
          memberNo +
          "&productNo=" +
          productNo
      )
      .then((response) => {
        checkIsInterestedProduct();
        alert("관심 목록에 추가되었습니다.");
        //            let result = confirm("관심 목록에 추가되었습니다. 관심 목록 페이지로 가시겠습니까?");
        //            console.log(result);
        //            if (result) {
        //                window.location.href = "/product/interested?memberNo=" + memberNo;
        //            }
      })
      .catch((err) => {
        console.log(err);
      });
  });

  interested.addEventListener("click", function () {
    axios
      .delete(
        "/product/deleteInterested?memberNo=" +
          memberNo +
          "&productNo=" +
          productNo
      )
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
      .get(
        "/product/checkInterestedProduct?memberNo=" +
          memberNo +
          "&productNo=" +
          productNo
      )
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

    if (option == "ing") {
      // 거래 중
      axios
        .get(
          "/product/ing?productNo=" +
            productNo +
            "&boughtMemberNo=" +
            boughtMemberNo
        )
        .then((response) => {
          isDealStatus();
        })
        .catch((err) => {
          console.log(err);
        });
    } else if (option == "done") {
      // 거래 완료
      let _left = Math.ceil((window.screen.width - 500) / 2);
      let _top = Math.ceil((window.screen.height - 600) / 2);
      window.open(
        "/chat/productChatList?memberNo=" + memberNo,
        "PopupNew",
        "width=500,height=600,left=" + _left + ",top=" + _top
      );

      axios
        .get(
          "/product/done?productNo=" +
            productNo +
            "&boughtMemberNo=" +
            boughtMemberNo
        )
        .then((response) => {
          isDealStatus();
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  function isDealStatus() {
    // 상품의 거래 상태 체크
    let str = "";

    axios
      .get("/product/isDealStatus?productNo=" + productNo)
      .then((response) => {
        console.log(response.data);
        if (response.data == "nok") {
          // 거래 중(null)인 경우
          str =
            '<option value="ing" selected>거래 중</option>' +
            '<option value="done">거래 완료</option>';
        } else if (response.data == "ok") {
          // 거래 완료(not null)인 경우
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
