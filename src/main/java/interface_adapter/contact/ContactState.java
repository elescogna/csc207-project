package interface_adapter.contact;

import entities.Item;

public class ContactState {
    private Item currentItem = null;
    private String subjectText = "";
    private String bodyText = "";
    private String error = "";

    /**
     * Default constructor from which to make the ContactState.
     */
    public ContactState() {}

    public Item getCurrentItem() { return currentItem; }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public String getSubjectText() { return subjectText; }

    public void setSubjectText(String subjectText) {
        this.subjectText = subjectText;
    }

    public String getBodyText() { return bodyText; }

    public void setBodyText(String bodyText) { this.bodyText = bodyText; }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }
}
