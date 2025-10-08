package raisetech.Student.Management.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.Student.Management.controller.converter.StudentConverter;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;

//受講生情報を取り扱うサービス
//受講生の検索、登録、更新を行う
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  //代わりにnewしてくれて、repositoryを保持できるようになっている。
  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  //受講生詳細の一覧検索
  //全件検索なので、条件指定は行わない。
  //＠return 受講生詳細一覧（全件）
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  //受講生詳細の単一検索
  //IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定
  //@Param id 受講生ID
  //@return 受講生詳細
  public StudentDetail searchStudent(String id){
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getId());
    return new StudentDetail(student, studentCourse);
  }

  //受講生詳細の登録を行う
  //受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定
  //@Param studentDetail 受講生詳細
  //@return 登録情報を付与した受講生詳細
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();

    repository.registerStudent(student);
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentsCourse(studentCourse, student);
      repository.registerStudentCourse(studentCourse);
    });
    return studentDetail;
  }

  //受講生コース情報を登録する際の初期情報を設定
  //@Param studentsCourse 受講生コース情報
  //@return student 受講生
  private static void initStudentsCourse(StudentCourse studentCourse, Student student) {
    LocalDateTime now = LocalDateTime.now();

    studentCourse.setStudentId(student.getId());
    studentCourse.setCourseStartAt(now);
    studentCourse.setCourseEndAt(now.plusYears(1));
  }

  //受講生詳細の更新を行う
  //受講生と受講生コース情報をそれぞれ更新する
  //@Param studentDetail 受講生詳細
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList()
        .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
  }
}

