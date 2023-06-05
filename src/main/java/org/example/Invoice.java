package org.example;

public class Invoice {
  
  public String id;

  public Invoice(String id) {
    this.id = id;
  }

  public Invoice() {
  }

  @Override
  public String toString() {
    return "Invoice [id=" + id + "]";
  }

}