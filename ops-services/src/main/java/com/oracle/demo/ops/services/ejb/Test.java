package com.oracle.demo.ops.services.ejb;

/**
 * **************************************************************************
 * <p/>
 * This code is provided for example purposes only.  Oracle does not assume
 * any responsibility or liability for the consequences of using this code.
 * If you choose to use this code for any reason, including but not limited
 * to its use as an example you do so at your own risk and without the support
 * of Oracle.
 * <p/>
 * ****************************************************************************
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/28/12
 * Time: 8:21 PM
 */

import java.util.List;

public class Test {

  public static void main(String... args) throws Exception {
    String json =
        "{"
        + "'title': 'Computing and Information systems',"
        + "'id' : 1,"
        + "'children' : 'true',"
        + "'groups' : [{"
        + "'title' : 'Level one CIS',"
        + "'id' : 2,"
        + "'children' : 'true',"
        + "'groups' : [{"
        + "'title' : 'Intro To Computing and Internet',"
        + "'id' : 3,"
        + "'children': 'false',"
        + "'groups':[]"
        + "}]"
        + "}]"
        + "}";

    // Now do the magic.
//    Data data = new Gson().fromJson(json, Data.class);

    // Show it.
//    System.out.println(data);
  }

}

class Data {
  private String title;
  private Long id;
  private Boolean children;
  private List<Data> groups;

  public String getTitle() { return title; }
  public Long getId() { return id; }
  public Boolean getChildren() { return children; }
  public List<Data> getGroups() { return groups; }

  public void setTitle(String title) { this.title = title; }
  public void setId(Long id) { this.id = id; }
  public void setChildren(Boolean children) { this.children = children; }
  public void setGroups(List<Data> groups) { this.groups = groups; }

  public String toString() {
    return String.format("title:%s,id:%d,children:%s,groups:%s", title, id, children, groups);
  }
}
