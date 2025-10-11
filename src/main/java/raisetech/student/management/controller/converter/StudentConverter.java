package raisetech.Student.Management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.data.StudentCourse;
import raisetech.Student.Management.domain.StudentDetail;

//受講生と受講生コース情報を受講生詳細に変換するコンバーター
@Component
public class StudentConverter {

  //受講生に紐づく受講生コース情報をマッピング
  //受講生コース情報は受講生に知して複数存在するので、ループを回して受講生詳細情報を組み立てる
  //＠Param　studentList 受講生一覧
  //＠Param　studentCourseList　受講生コース情報のリスト
  //@return　受講生詳細情報のリスト
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentCourseList) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCoursList = studentCourseList.stream()
          .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
          .collect(Collectors.toList());
      studentDetail.setStudentCourseList(convertStudentCoursList);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
