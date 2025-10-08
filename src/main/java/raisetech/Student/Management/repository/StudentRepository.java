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
  @Select("SELECT * FROM students")
  List<Student> search();

  //受講生の検索
  //@Param id 受講生ID
  //@return 受講生
  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  //受講生のコース情報の全件検索を行う
  //@return 受講生のコース情報（全件）
  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourseList();

  //受講生IDに紐づく受講生コース情報を検索
  //@Param studentId 受講生ID
  //@return 受講生IDに紐づく受講生コース情報
  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> searchStudentCourse(String studentId);

  //受講生の新規登録
  //IDに関しては自動採番を行う
  //@Param student 受講生
  @Insert("INSERT INTO students (name, kana_name, nickname, email, area, age, sex, remark, deleted) " +
      "VALUES (#{name}, #{kanaName}, #{nickname}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent (Student student);

  //受講生コース情報の新規登録
  //IDに関しては自動採番を行う
  //@Param studentsCourses 受講生コース情報
  @Insert("INSERT INTO students_courses (student_id, course_name, course_start_at, course_end_at) " +
      "VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourse studentCourse);

  //受講生を更新
  //@Param student 受講生
  @Update("UPDATE students SET name=#{name}, kana_name=#{kanaName}, nickname=#{nickname},"
      + " email=#{email}, area=#{area}, age=#{age}, sex=#{sex}, remark=#{remark}, deleted=#{deleted} "
      + " WHERE id=#{id}")
  void updateStudent (Student student);

  //受講生コース情報のコース名を更新
  //@Param studentsCourses 受講生コース情報
  @Update("UPDATE students_courses SET course_name=#{courseName} WHERE id=#{id}")
  void updateStudentCourse(StudentCourse studentCourse);
}

