package raisetech.student.management.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockmvc;

  @MockitoBean
  private StudentService service;

  @MockitoBean
  private StudentConverter converter;

  @Test
  void 受講生詳細の一覧検索ができて空のリストが返ってくること() throws Exception {
    mockmvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生単一検索ができて単一の受講生情報が返ってくること() throws Exception {
    String id = "999";
    mockmvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の登録ができて空のリストが返ってくること() throws Exception {
    mockmvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any(StudentDetail.class));
  }

  @Test
  void 受講生詳細の更新ができて成功したかどうかが返ってくること() throws Exception {
    mockmvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isOk())
        .andExpect(content().string("更新処理が成功しました。"));

    verify(service, times(1)).updateStudent(any(StudentDetail.class));
  }

  @Test
  void 例外処理が行えていること() throws Exception {
    mockmvc.perform(get("/testException"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("テストエラーが発生しました。"));
  }
}