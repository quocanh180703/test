package com.example.nhom3_tt_.dtos.requests.lecture;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureVideoFolderStructure {

  private Long instructorId;

  private Long courseId;

  private Long sectionId;
}
