package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.el.util.Validation;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること(){
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の単一検索が行えること(){
    String id = "1";
    Student actual = sut.searchStudent(id);

    Student expected = new Student();
    expected.setId("1");
    expected.setName("山田太郎");
    expected.setKanaName("ヤマダタロウ");
    expected.setNickname("タロウ");
    expected.setEmail("taro@example.com");
    expected.setArea("東京");
    expected.setAge(30);
    expected.setSex("男");

    assertThat(actual.getId()).isEqualTo(expected.getId());
    assertThat(actual.getName()).isEqualTo(expected.getName());
    assertThat(actual.getKanaName()).isEqualTo(expected.getKanaName());
    assertThat(actual.getNickname()).isEqualTo(expected.getNickname());
    assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    assertThat(actual.getArea()).isEqualTo(expected.getArea());
    assertThat(actual.getAge()).isEqualTo(expected.getAge());
    assertThat(actual.getSex()).isEqualTo(expected.getSex());

  }

  @Test
  void 受講生の単一検索時検索対象が見つからない時に空を返すこと(){
    String id = "99";
    Student actual = sut.searchStudent(id);
    assertThat(actual).isNull();
  }

  @Test
  void 受講生のコース情報の全件検索が行えること(){
    List<StudentCourse> actual = sut.searchStudentCourseList();
    assertThat(actual.size()).isEqualTo(7);
  }

  @Test
  void 受講生IDに紐づく受講生コース情報の検索が行えること(){
    String studentId = "1";
    List<StudentCourse> actual = sut.searchStudentCourse(studentId);

    StudentCourse expected = new StudentCourse();
    expected.setId("1");
    expected.setStudentId("1");
    expected.setCourseName("Javaコース");

    assertThat(actual.get(0).getId()).isEqualTo(expected.getId());
    assertThat(actual.get(0).getStudentId()).isEqualTo(expected.getStudentId());
    assertThat(actual.get(0).getCourseName()).isEqualTo(expected.getCourseName());
  }

  @Test
  void 受講生IDに紐づく受講生コース情報の検索時検索対象が見つからない時に空を返すこと(){
    String studentId = "99";
    List<StudentCourse> actual = sut.searchStudentCourse(studentId);
    assertThat(actual).isEmpty();
  }

  @Test
  void 受講生の登録が行えること(){
    Student student = new Student();
    student.setName("田中太郎");
    student.setKanaName("タナカタロウ");
    student.setNickname("タロウ");
    student.setEmail("taro@example.com");
    student.setArea("大阪");
    student.setAge(27);
    student.setSex("男");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void  受講生コース情報の登録が行えること(){
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("9");
    studentCourse.setCourseName("Javaコース");
    studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourse(studentCourse.getStudentId());

    assertThat(actual.size()).isEqualTo(1);
  }

  @Test
  void 受講生情報の更新が行えること(){

    Student student = new Student();
    student.setId("1");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロウ");
    student.setEmail("taro@example.com");
    student.setArea("大阪");
    student.setAge(30);
    student.setSex("男");
    student.setRemark("");
    student.setDeleted(false);

    sut.updateStudent(student);

    Student actual = sut.searchStudent("1");

    assertThat(actual.getArea()).isEqualTo("大阪");
  }

  @Test
  void 受講生情報の更新時に対象が見つからない時空を返すこと(){

    Student student = new Student();
    student.setId("99");
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロウ");
    student.setEmail("taro@example.com");
    student.setArea("東京");
    student.setAge(30);
    student.setSex("男");
    student.setRemark("");
    student.setDeleted(false);

    sut.updateStudent(student);

    Student actual = sut.searchStudent("99");

    assertThat(actual).isNull();
  }

  @Test
  void 受講生コース情報の更新が行えること(){

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("AWSコース");

    sut.updateStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourse("1");

    assertThat(actual.get(0).getCourseName()).isEqualTo("AWSコース");

  }

  @Test
  void 受講生コース情報の更新時に対象が見つからない時空を返すこと(){

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setStudentId("99");
    studentCourse.setCourseName("AWSコース");

    sut.updateStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourse("99");

    assertThat(actual).isEmpty();

  }

}