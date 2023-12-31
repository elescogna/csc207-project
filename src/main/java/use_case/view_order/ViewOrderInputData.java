package use_case.view_order;

public class ViewOrderInputData {
    private String orderId;
    private String studentEmail;
    private String studentAddress;

    /**
     * Creates a new ViewOrderInputData with the given parameters.
     *
     * @param orderId the order ID with which to create this input data
     * @param studentEmail  the student email with which to create this input data
     * @param studentAddress  the student address with which to create this input data
     */
    public ViewOrderInputData(String orderId, String studentEmail, String studentAddress) {
        this.orderId = orderId;
        this.studentEmail = studentEmail;
        this.studentAddress = studentAddress;
    }

    public String getOrderId() { return orderId; }

    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getStudentEmail() { return studentEmail; }

    public void setStudentEmail(String currentStudentEmail) {
        this.studentEmail = currentStudentEmail;
    }

    public String getStudentAddress() { return studentAddress; }

    public void setStudentAddress(String currentStudentAddress) {
        this.studentAddress = currentStudentAddress;
    }
}
