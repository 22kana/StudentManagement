package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
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

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getName(), expected.getName());
    assertEquals(expected.getKanaName(), expected.getKanaName());
    assertEquals(expected.getEmail(), expected.getEmail());

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
    expected.setStudentId("1");
    expected.setCourseName("Javaコース");

    assertEquals(expected.getStudentId(), actual.get(0).getStudentId());
    assertEquals(expected.getCourseName(), actual.get(0).getCourseName());
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
  void 受講生コース情報の更新が行えること(){

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("AWSコース");

    sut.updateStudentCourse(studentCourse);

    List<StudentCourse> actual = sut.searchStudentCourse("1");

    assertThat(actual.get(0).getCourseName()).isEqualTo("AWSコース");

  }

}