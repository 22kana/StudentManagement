package raisetech.student.management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.service.StudentService;

//受講生の検索や登録、更新などを行うREST　APIとして実行されるControllerです。
@RestController
@Validated
public class StudentController {

  private StudentService service;

  //コンストラクタ、何で構成されているか
  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
  }

  //受講生詳細の一覧検索
  //全件検索なので、条件指定は行わない。
  //＠return 受講生詳細一覧（全件）
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  //受講生詳細検索
  //IDに紐づく任意の受講生の情報を取得
  //@Param id 受講生ID
  //@return 受講生
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id){
    return service.searchStudent(id);
  }

  //受講生詳細の登録を行う
  //@Param studentDetail 受講生詳細
  //@return 実行結果
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail){
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  //受講生詳細の更新を行う
  //キャンセルフラグの更新もここで行う
  //@Param studentDetail 受講生詳細
  //@return 実行結果
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail){
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }

  //例外処理用のメソッド
  @GetMapping("/testException")
  public List<StudentDetail> getException() throws Exception {
    throw new TestException("テストエラーが発生しました。");
  }

}
