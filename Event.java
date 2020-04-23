package hello;

import java.util.Date;

public class Event {
  private long id;
  private Date date;
  
  public Event(long id, Date date) {
    this.id = id;
    this.date = date;
  }
  
  /**
   * @return the id
   */
  public long getId() {
    return id;
  }
  
  /**
   * @param id the id to set
   */
  public void setId(long id) {
    this.id = id;
  }
  
  /**
   * @return the date
   */
  public Date getDate() {
    return date;
  }
  
  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }
  
}