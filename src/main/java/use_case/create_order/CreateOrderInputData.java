package use_case.create_order;

import entities.Item;
import entities.Student;

public class CreateOrderInputData {
    private Item item;
    private Student student;
    private String buyerEmail = "";
    private String sameAddress = "";
    private String otherAddress = "";

    /**
     * Creates a new CreateOrderInputData with the parameters given
     *
     * @param item the item with which to create the CreateOrderInputData
     * @param student the student with which to create the CreateOrderInputData
     * @param buyerEmail the buyer email with which to create the CreateOrderInputData
     * @param sameAddress the boolean same address option with which to create the CreateOrderInputData
     * @param otherAddress the "other" address with which to create the CreateOrderInputData
     */
    public CreateOrderInputData(Item item, Student student, String buyerEmail, String sameAddress, String otherAddress) {
        this.item = item;
        this.student = student;
        this.buyerEmail = buyerEmail;
        this.sameAddress = sameAddress;
        this.otherAddress = otherAddress;
    }

    public Item getItem() {
        return item;
    }

    public Student getStudent() {
        return student;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public String getSameAddress() {
        return sameAddress;
    }

    public String getOtherAddress() {
        return otherAddress;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public void setSameAddress(String sameAddress) {
        this.sameAddress = sameAddress;
    }

    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress;
    }

}
