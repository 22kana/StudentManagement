package raisetech.student.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter converter;

  @Test
  void 受講生に紐づく受講生コース情報をマッピングできていること(){

    converter = new StudentConverter();

    Student student = new Student();
    student.setId("1");
    student.setName("田中太郎");
    List<Student> studentList = new ArrayList<>();
    studentList.add(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("Javaコース");
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourse);

    List<StudentDetail> studentDetails = new ArrayList<>();

    List<StudentDetail> expected = new ArrayList<>();
    expected.add(new StudentDetail(student, studentCourseList));
    List<StudentDetail> actual = converter.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual).hasSize(1);
    assertThat(actual.get(0).getStudent().getName()).isEqualTo("田中太郎");
    assertThat(actual.get(0).getStudentCourseList())
        .extracting(StudentCourse::getCourseName)
        .containsOnly("Javaコース");

  }
}