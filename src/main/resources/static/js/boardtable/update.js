import { getBytesSize, isNumeric } from "/js/common.js";

// 각 버튼의 DOM 객체 선언
const btnSaveEle = document.getElementById("btnSave");
const btnCancelEle = document.getElementById("btnCancel");
const btnAllEle = document.getElementById("btnAll");

// 저장 버튼
btnSaveEle.addEventListener("click", (e) => {
  // 저장 여부 확인
  if (!confirm("저장하시겠습니까?")) return;

  e.preventDefault(); //이벤트 잠시 막기

  // 오류 메시지가 들어갈 부분 이벤트 발생시 초기화
  errTitle.textContent = "";
  errUserN.textContent = "";
  errContent.textContent = "";

  // 각 인풋값의 공백을 제거한 뒤에 value 추출
  const titleVal = document.getElementById("title").value.trim();
  const userNameVal = document.getElementById("userName").value.trim();
  const contentVal = document.getElementById("content").value.trim();

  // 제목 부분
  if (titleVal.length == 0) {
    errTitle.textContent = "제목 입력은 필수입니다.";
    return;
  } else if (getBytesSize(titleVal) > 60) {
    errTitle.textContent =
      "제목은 한글로 20자,영문으로 60자까지만 입력가능합니다";
    return;
  }
  // 작성자 이름 부분
  if (userNameVal.length == 0) {
    errUserN.textContent = "작성자 입력은 필수입니다.";
    return;
  } else if (getBytesSize(userNameVal) > 30) {
    errUserN.textContent =
      "제목은 한글로 10자,영문으로 30자까지만 입력가능합니다";
    return;
  }
  // 내용 부분
  if (contentVal.length == 0) {
    errContent.textContent = "내용 입력은 필수입니다.";
    return;
  }
  // 확인 후 업데이트 폼에 submit 전송
  const updateFormEle = document.getElementById("updateForm");
  updateForm.submit();
});

// 취소 버튼
btnCancelEle.addEventListener("click", (e) => {
  const updateIdEle = document.getElementById("bid");
  location.href = `/boardtables/${updateIdEle.value}/detail`;
});

// 목록 버튼
btnAllEle.addEventListener("click", (e) => {
  location.href = `/boardtables`;
});
