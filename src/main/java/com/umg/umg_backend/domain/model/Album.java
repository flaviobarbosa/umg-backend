package com.umg.umg_backend.domain.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Album {
  private String id;
  private String name;
  private List<Image> images;
}
