package rofaeil.ashaiaa.idea.paginationfromsqlite;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 29/10/17.
 */

public class Item {

    private String title;
    private String content;

    public Item() {
    }

    public Item(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public interface ItemSource {

        int getCount();

        Item getItem(int position);

        void close();
    }

}
