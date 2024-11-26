package com.kh.test.web;

import com.kh.test.domain.entity.ReplyBbs;
import com.kh.test.domain.replyBbs.SVC.ReplyBbsSVC;
import com.kh.test.web.api.ApiResponse;
import com.kh.test.web.api.ApiResponseCode;
import com.kh.test.web.exception.BusinessException;
import com.kh.test.web.form.replybbs.CommentForm;
import com.kh.test.web.req.ReqSave;
import com.kh.test.web.req.ReqUpdate;
import com.kh.test.web.util.KhUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/replyBbs")


public class ApiReplyBbsController {

  private final ReplyBbsSVC replyBbsSVC;
  
  
  // 댓글 작성
  @PostMapping("/add")
  public ApiResponse<CommentForm> addReply(@Valid @RequestBody ReqSave reqSave,
                                           BindingResult bindingResult){
    // 요청바디 확인
    log.info("reqSave = {}", reqSave);

    // 응답메시지 초기화
    ApiResponse<CommentForm> res = null;
    
    // 만약 어노테이션 레벨에서 오류가 발생한다면
    if(bindingResult.hasErrors()){
      // 1차적으로 로그에서 오류 정보를 보여준다.
      log.info("bindingResult = {}", bindingResult);
      // BusinessException 을 던짐. VALIDATION_ERROR = 유효성검사단계에서 이루어지는 오류 ,
      //KhUtil.getValidChkMap(bindingResult) 이부분은 bindingResult 객체 내에서 실제로 발생한 오류들을 탐색에서 맵객체로 만든다.
      // 그렇게에 응답 메세지 바디에서 헤더부분에는 첫번쨰 매개변수에 있는 응답코드와 메세지가 나오고
      // 두번째 매개변수에 있는 상세정보가 입력된다.
      throw new BusinessException(ApiResponseCode.VALIDATION_ERROR, KhUtil.getValidChkMap(bindingResult));
    }

    

    // 저장할 객체를 생성
    ReplyBbs replyBbs =new ReplyBbs();
    
    // 일치하는 각 속성에 매칭
    BeanUtils.copyProperties(reqSave, replyBbs);

    // bid값 따로 생성
    Long bid = replyBbs.getBid();

    log.info("bid = {}", bid);

    // 저장 시행
    Long rid = replyBbsSVC.addReply(replyBbs, bid);
    
    // 저장된 결과를 도출할 객체 생성
    ReplyBbs addReply = replyBbsSVC.findById(rid);

    CommentForm commentForm = new CommentForm();
    BeanUtils.copyProperties(addReply, commentForm);



    // 시행 결과
    // rid값이 null이 아니고 0 이상일때 생성된 객체를 바디에 넣어서 보여준다.
    if (rid!=null && rid>0){
      res = ApiResponse.of(ApiResponseCode.SUCCESS,commentForm);
      // 만약 생성된 rid값을 찾지 못하였을경우에는 INTERNAL_SERVER_ERROR 의 정보를 보여주고 바디에는 null 값을 넣는다
    }else{
      res = ApiResponse.of(ApiResponseCode.INTERNAL_SERVER_ERROR,null);
    }
    return res;
  }


  // 댓글 목록
  @GetMapping("/{bid}/all")
  public ApiResponse<List<ReplyBbs>> all(@PathVariable("bid") Long bid,
                                         @RequestParam(value = "reqPage",defaultValue = "1") Integer reqPage,
                                         @RequestParam(value = "reqRec",defaultValue = "10") Integer reqRec){
    // 게시판의 글번호가 잘들어오는지 확인
    log.info("bid = {}", bid);
    log.info("reqPage = {}", reqPage);
    log.info("reqRec = {}", reqRec);

    // 응답 초기화
    ApiResponse<List<ReplyBbs>> res = null;
    
    // 해당 게시판에 달려있는 댓글들의 총 갯수 반환
    int totalRecords = replyBbsSVC.getTotalRecords(bid);


    // 해당 게시판 글번호에 해당하는 댓글들을 리스트에 추가
    // 페이징을 추가
    List<ReplyBbs> replyBbsList = replyBbsSVC.findAll(bid,reqPage,reqRec);
    
    if (replyBbsList.size() != 0){
      res = ApiResponse.of(ApiResponseCode.SUCCESS,replyBbsList,totalRecords);
    }else {
      res = ApiResponse.of(ApiResponseCode.ENTITY_NOT_FOUND,null);
    }

    return res;

  }

  // 댓글 삭제
  @DeleteMapping("/{rid}")
  public ApiResponse<String> deleteReply(@PathVariable("rid") Long rid){
    // rid 값이 제대로 들어오는지 확인
    log.info("rid = {}", rid);
    // 응답 메세지 초기화
    ApiResponse<String> res = null;

    Long rows = replyBbsSVC.DeleteReply(rid);

    log.info("rows = {}", rows);

    if(rows == 1L){
      res = ApiResponse.of(ApiResponseCode.SUCCESS,rid + "번의 댓글이 삭제되었습니다.");
    }else{
      res = ApiResponse.of(ApiResponseCode.ENTITY_NOT_FOUND,null);
    }
    return res;
  }

  // 댓글 수정
  @PatchMapping
  public ApiResponse<CommentForm> updateReply(@Valid@RequestBody ReqUpdate reqUpdate,
                                              BindingResult bindingResult){
    // reqUpdate 값이 들어오는지 확인
    log.info("reqUpdate = {}", reqUpdate);

    Long rid = reqUpdate.getRid();

    log.info("rid = {}",rid);

    // 응답 메세지 초기화
    ApiResponse<CommentForm> res = null;


    // 어노테이션 단계에서의 오류 검증
    // 오류가 발생한다면
    if(bindingResult.hasErrors()){
      // 1차적으로 발생한 오류 확인
      log.info("bindingResult = {}",bindingResult);
      // 유효성 검사단계에서의 오류 발생!! 상세정보와 응답 코드, 메세지를 보낸다.
      throw new BusinessException(ApiResponseCode.VALIDATION_ERROR,KhUtil.getValidChkMap(bindingResult));
    }


    ReplyBbs replyBbs = new ReplyBbs();
    BeanUtils.copyProperties(reqUpdate, replyBbs);


    Long rows = replyBbsSVC.UpdateReply(rid, replyBbs);

    log.info("rows =  {}", rows);

    // 저장된 결과를 도출할 객체 생성
    ReplyBbs updateReply = replyBbsSVC.findById(rid);

    log.info("updateReply = {}", updateReply);

    CommentForm commentForm = new CommentForm();
    BeanUtils.copyProperties(updateReply, commentForm);

    if(rows == 1){
      res = ApiResponse.of(ApiResponseCode.SUCCESS,commentForm);
    }else{
      res = ApiResponse.of(ApiResponseCode.INTERNAL_SERVER_ERROR,null);
    }


    return res;

  }

  // 전체레코드수 가져오기
  @GetMapping("/totalCnt/{bid}")
  public ApiResponse<Integer> totalCnt(@PathVariable("bid") Long bid){
    ApiResponse<Integer> res = null;
    Integer totalRec =  replyBbsSVC.getTotalRecords(bid);

    res = ApiResponse.of(ApiResponseCode.SUCCESS, null, totalRec);

    return res;
  }
}
