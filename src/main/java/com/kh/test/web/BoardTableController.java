package com.kh.test.web;

import com.kh.test.domain.boardtable.svc.BoardTableSVC;
import com.kh.test.domain.entity.BoardTable;
import com.kh.test.web.form.boardtable.AllForm;
import com.kh.test.web.form.boardtable.FindForm;
import com.kh.test.web.form.boardtable.SaveForm;
import com.kh.test.web.form.boardtable.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/boardtables")
@RequiredArgsConstructor

public class BoardTableController {

  private final BoardTableSVC boardTableSVC;


  @GetMapping
  //초기 화면에서 기존에 sql에 저장되어 있는 게시판의 목록을 나열하는 메소드
  public String Home(Model model,
                     @RequestParam(value = "reqPage",defaultValue = "1")Integer reqPage,
                     @RequestParam(value = "reqRec",defaultValue = "10")Integer reqRec) {

    // 해당 테이블의 총 레코드 반환
    int totalRecords = boardTableSVC.getTotalRecords(); // 총 레코드 수
    int totalPages = (int) Math.ceil((double) totalRecords / reqRec); // 전체 페이지 수

    // 현재 페이지의 정보를 토대로 레코드 반환
    List<BoardTable> list = boardTableSVC.findAll(reqPage,reqRec);

    // 현재 페이지 그룹 계산
    int pagesPerPage = 10; // 한 페이지 그룹당 표시할 페이지 수
    int currentPageGroupStart = ((reqPage - 1) / pagesPerPage) * pagesPerPage + 1;
    int currentPageGroupEnd = Math.min(currentPageGroupStart + pagesPerPage - 1, totalPages);



    // AllForm 객체를 담는 리스트 생성
    List<AllForm> allFormsList = new ArrayList<>();

    // entity 객체를 AllForm 객체로 변환 후 저장
    for (BoardTable boardTable : list) {
      AllForm allForm = new AllForm();
      allForm.setBid(boardTable.getBid());
      allForm.setTitle(boardTable.getTitle());
      allForm.setContent(boardTable.getContent());
      allForm.setUserName(boardTable.getUserName());
      allForm.setCreatedAt(boardTable.getCreatedAt());
      allForm.setUpdatedAt(boardTable.getUpdatedAt());
      allFormsList.add(allForm);
    }

    // model을 이욜해 AllForm객체의 리스트를 뷰에 전달
    model.addAttribute("allFormsList", allFormsList);
    // model을 이용해 뷰에서 사용할 페이징 정보를 전달
    model.addAttribute("totalRecords", totalRecords);          // 총 레코드 수
    model.addAttribute("currentPage", reqPage);                // 현재 페이지 번호
    model.addAttribute("recordsPerPage", reqRec);              // 페이지당 레코드 수
    model.addAttribute("pagesPerPage", pagesPerPage);          // 한 페이지 그룹 크기
    model.addAttribute("currentPageGroupStart", currentPageGroupStart); // 페이지 그룹 시작
    model.addAttribute("currentPageGroupEnd", currentPageGroupEnd);     // 페이지 그룹 끝
    model.addAttribute("totalPages", totalPages);              // 전체 페이지 수

    // all.html 호출
    return "/boardtable/all";
  }


  // 게시글 조회
  @GetMapping("/{id}/detail")
  public String findById(@PathVariable("id") Long bid,
                         Model model) {
    // 정상적으로 bid 반환되는지 확인
    log.info("bid = {}", bid);


    // bid 해당하는 게사글을 찾아서 반환 형식은 entity boardtable
    BoardTable findedBoardTable = boardTableSVC.findById(bid);

    // 찾아낸 게시글을 담기위한 FindForm 생성
    FindForm findForm = new FindForm();

    // BoardTable 객체를 findForm 객체에 할당
    findForm.setBid(findedBoardTable.getBid());
    findForm.setTitle(findedBoardTable.getTitle());
    findForm.setContent(findedBoardTable.getContent());
    findForm.setUserName(findedBoardTable.getUserName());
    findForm.setCreatedAt(findedBoardTable.getCreatedAt());
    findForm.setUpdatedAt(findedBoardTable.getUpdatedAt());
    findForm.setMemberId(findedBoardTable.getMemberId());

    // 만들어낸 findForm을 뷰로 전달
    model.addAttribute("findForm", findForm);

    return "/boardtable/detail";
  }

  // 글 작성 화면으로 이동
  @GetMapping("/add")
  public String GoAdd(Model model) {
    // 처음 글 작성 페이지로 이동할때 아무런 값도 가지지 않은 saveForm 객체를 생성하여 오류방지
    model.addAttribute("saveForm", new SaveForm());

    return "/boardtable/add";
  }


  // 글 작성 화면에서 저장 버튼이 클릭 되었을때
  @PostMapping("/add")
  public String Save(@Valid @ModelAttribute SaveForm saveForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes) {

    // 1차적으로 어노테이션 레벨에서 유효성 검사

    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "/boardtable/add";
    }
    

    // SaveForm 으로 받은 데이터를 기반으로 엔티티 작성
    BoardTable boardTable = new BoardTable();
    boardTable.setTitle(saveForm.getTitle());
    boardTable.setContent(saveForm.getContent());
    boardTable.setUserName(saveForm.getUserName());
    boardTable.setMemberId(saveForm.getMemberId());

    // 메소드 호출로 레코드 작성 후 생성된 레코드의 아이디 값 추출
    Long uid = boardTableSVC.AddBoardTable(boardTable);

    redirectAttributes.addAttribute("id", uid);


    return "redirect:/boardtables/{id}/detail";
  }


  // detail에서 삭제 버튼이 눌렸을 경우
  @GetMapping("/{id}/del")
  public String Delete(@PathVariable("id") Long bid) {

    log.info("bid = {}", bid);

    boardTableSVC.DeleteBoardTable(bid);

    return "redirect:/boardtables";
  }

  // 상세 화면에서 아이디 정보를 가지고 와서 그걸 기반으로 수정 페이지로 이동
  @GetMapping("/{id}/update")
  public String GoUpdate(@PathVariable("id") Long bid,
                         Model model) {

    // 아이디정보 제대로 들어왔는지 확인
    log.info("bid ={}", bid);

    // 아이디 기반으로 데이터베이스에서 검색 후 수정할 대상으로 변경
    BoardTable findBt = boardTableSVC.findById(bid);

    // 찾아낸 BoardTable 객체를 UpdateForm 객체에 옮겨 담는 작업
    UpdateForm updateForm = new UpdateForm();

    updateForm.setBid(findBt.getBid());
    updateForm.setTitle(findBt.getTitle());
    updateForm.setUserName(findBt.getUserName());
    updateForm.setContent(findBt.getContent());
    updateForm.setCreatedAt(findBt.getCreatedAt());
    updateForm.setUpdatedAt(findBt.getUpdatedAt());
    updateForm.setMemberId(findBt.getMemberId());


    model.addAttribute("updateForm", updateForm);

    return "/boardtable/update";
  }

  @PostMapping("/{id}/update")
  public String UpdateById(@PathVariable("id") Long bid,
                       @Valid@ModelAttribute UpdateForm updateForm,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

    // bid updateform이 잘 적용 되었는지 확인
    log.info("bid ={}",bid);
    log.info("updateForm ={}",updateForm);
    
    
    // 1차적으로 어노테이션 단계에서 유효성 검사 진행
    if (bindingResult.hasErrors()){
      return "/boardtable/update";
    }
    
    
    

    BoardTable boardTable = new BoardTable();
    boardTable.setBid(updateForm.getBid());
    boardTable.setTitle(updateForm.getTitle());
    boardTable.setContent(updateForm.getContent());
    boardTable.setUserName(updateForm.getUserName());
    boardTable.setUpdatedAt(updateForm.getUpdatedAt());

    Long rows = boardTableSVC.UpdateBoardTable(bid, boardTable);

    redirectAttributes.addAttribute("id",bid);


    return "redirect:/boardtables/{id}/detail";
  }

}
