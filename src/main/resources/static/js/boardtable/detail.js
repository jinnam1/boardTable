const btnUpdateEle = document.getElementById("btnUpdate");
const btnDeleteEle = document.getElementById("btnDelete");
const btnAllEle = document.getElementById("btnAll");

// 수정버튼
btnUpdateEle.addEventListener("click", (e) => {
  // 페이지에 있는 userId의 값을 가져와 해당 글의 업데이트 url로 이동
  // get update는 해당 뷰로 이동
  const updateIdEle = document.getElementById("userId");
  location.href = `/boardtables/${updateIdEle.value}/update`;
});

// 삭제 버튼
btnDeleteEle.addEventListener("click", (e) => {
  // 삭제 여부 확인
  if (!confirm("삭제하시겠습니까")) return;

  // 확인 후 삭제 url로 이동
  const updateIdEle = document.getElementById("userId");
  location.href = `/boardtables/${updateIdEle.value}/del`;
});

// 목록 버튼
btnAllEle.addEventListener("click", (e) => {
  location.href = `/boardtables`;
});
