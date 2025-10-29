package raisetech.student.management.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository,converter);
  }

  @Test
  void 受講生詳細の一覧検索＿リポジトリとコンバーターが適切に呼び出せていること(){

    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    List<StudentDetail> actual = sut.searchStudentList();

    verify(repository,times(1)).search();
    verify(repository,times(1)).searchStudentCourseList();
    verify(converter,times(1)).convertStudentDetails(studentList, studentCourseList);
  }

  @Test
  void 受講生詳細の単一検索が適切に動作すること(){

    String id = "1";
    Student student = new Student();
    student.setId(id);
    List<StudentCourse> studentCourseList = new ArrayList<>();
    when(repository.searchStudent(id)).thenReturn(student);
    when(repository.searchStudentCourse(student.getId())).thenReturn(studentCourseList);

    StudentDetail actual = sut.searchStudent(id);

    Assertions.assertEquals(student, actual.getStudent());
    Assertions.assertEquals(studentCourseList, actual.getStudentCourseList());

    verify(repository,times(1)).searchStudent(id);
    verify(repository,times(1)).searchStudentCourse(id);
  }

  @Test
  void 受講生詳細の登録が適切に行われること(){

    Student student = new Student();
    student.setId("1");
    student.setName("田中太郎");
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseName("Javaコース");
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourse);
    StudentDetail studentDetail1 = new StudentDetail(student, studentCourseList);

    StudentDetail actual = sut.registerStudent(studentDetail1);

    verify(repository,times(1)).registerStudent(student);

    ArgumentCaptor<StudentCourse> studentCourseCaptor = ArgumentCaptor.forClass(StudentCourse.class);
    verify(repository, times(1)).registerStudentCourse(studentCourseCaptor.capture());

    StudentCourse studentCourseCaptured = studentCourseCaptor.getValue();
    Assertions.assertEquals("Javaコース", studentCourseCaptured.getCourseName());
    Assertions.assertEquals(student.getId(), studentCourseCaptured.getStudentId());
    Assertions.assertNotNull(studentCourseCaptured.getCourseStartAt());
    Assertions.assertNotNull(studentCourseCaptured.getCourseEndAt());
  }

  @Test
  void 受講生詳細の更新が適切に行われること(){

    Student student = new Student();
    student.setId("1");
    student.setName("田中太郎");
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseName("AWSコース");
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    sut.updateStudent(studentDetail);

    verify(repository,times(1)).updateStudent(student);

    ArgumentCaptor<StudentCourse> studentCourseCaptor = ArgumentCaptor.forClass(StudentCourse.class);
    verify(repository, times(1)).updateStudentCourse(studentCourseCaptor.capture());

    StudentCourse studentCourseCaptured = studentCourseCaptor.getValue();
    Assertions.assertEquals("AWSコース", studentCourseCaptured.getCourseName());
  }

  @Test
  void 受講生更新時にリポジトリで例外が発生した場合_例外がスローされること(){

    Student student = new Student();
    student.setId("1");
    student.setName("田中太郎");
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseName("AWSコース");
    List<StudentCourse> studentCourseList = new ArrayList<>();
    studentCourseList.add(studentCourse);
    StudentDetail studentDetail = new StudentDetail(student, studentCourseList);

    doThrow(new RuntimeException("DBエラー"))
        .when(repository).updateStudent(student);

    Assertions.assertThrows(RuntimeException.class, () -> sut.updateStudent(studentDetail));

    verify(repository, never()).updateStudentCourse(any());
  }
}