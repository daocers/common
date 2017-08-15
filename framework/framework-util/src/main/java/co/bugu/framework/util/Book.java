package co.bugu.framework.util;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * Created by daocers on 2016/9/26.
 */
@XStreamAlias("my-book")
public class Book {
//    @XStreamAsAttribute
    @XStreamAlias("my_id")
    private Integer id;

    @XStreamAlias("book_name")
    private String name;

    private String authorName;

    @XStreamAlias("lines")
    private List<Line> lineList;

    public List<Line> getLineList() {
        return lineList;
    }

    public void setLineList(List<Line> lineList) {
        this.lineList = lineList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
