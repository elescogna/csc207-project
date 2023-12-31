package use_case.search;

import entities.Student;

import java.util.HashMap;
import java.util.HashSet;

public class SearchInputData {
    private HashMap<String, Object> filteredAttributes;
    private Student currentStudent;

    public SearchInputData(HashMap<String, Object> filteredAttributes, Student currentStudent) {
        this.currentStudent = currentStudent;
        this.filteredAttributes = filteredAttributes;
    }

    public void setFilteredAttributes(HashMap<String, Object> filteredAttributes) {
        this.filteredAttributes = filteredAttributes;
    }

    public HashMap<String, Object> getFilteredAttributes() {
        return this.filteredAttributes;
    }

    public Object getSingleAttribute(String attribute) {
        // TODO: for this to work we need to force the user to select something from all the filterable attribute
        // even if one of the options we put has to be "other" or "none"
        return this.filteredAttributes.get(attribute);
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }
}
