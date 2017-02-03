package at.prashant.prashantruns;

/**
 * Created by prashant on 2/1/17.
 */

public class HistoryFragmentItems
{
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HistoryFragmentItems(String title, String description)
    {
        super();
        this.title = title;
        this.description = description;
    }
}