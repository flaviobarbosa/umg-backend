package com.umg.umg_backend.domain.model;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
  private Album album;
  private ArrayList<Artist> artists;
  private int duration_ms;
  private boolean explicit;
  private String id;
  private String name;
}
