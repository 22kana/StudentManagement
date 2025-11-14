package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.data.Student;

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

}