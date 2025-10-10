package raisetech.Student.Management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;

//受講生テーブルと受講生コース情報テーブルと紐づくRepository
@Mapper
public interface StudentRepository {

  //受講生の全件検索
  //＠return 受講生一覧（全件）
  List<Student> search();

  //受講生の検索
  //@Param id 受講生ID
  //@return 受講生
  Student searchStudent(String id);

  //受講生のコース情報の全件検索を行う
  //@return 受講生のコース情報（全件）
  List<StudentCourse> searchStudentCourseList();

  //受講生IDに紐づく受講生コース情報を検索
  //@Param studentId 受講生ID
  //@return 受講生IDに紐づく受講生コース情報
  List<StudentCourse> searchStudentCourse(String studentId);

  //受講生の新規登録
  //IDに関しては自動採番を行う
  //@Param student 受講生
  void registerStudent (Student student);

  //受講生コース情報の新規登録
  //IDに関しては自動採番を行う
  //@Param studentsCourses 受講生コース情報
  void registerStudentCourse(StudentCourse studentCourse);

  //受講生を更新
  //@Param student 受講生
  void updateStudent (Student student);

  //受講生コース情報のコース名を更新
  //@Param studentsCourses 受講生コース情報
  void updateStudentCourse(StudentCourse studentCourse);
}

