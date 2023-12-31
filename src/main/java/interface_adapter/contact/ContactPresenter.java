package interface_adapter.contact;
import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeViewModel;
import use_case.contact.ContactOutputBoundary;

public class ContactPresenter implements ContactOutputBoundary {
    private ContactViewModel contactViewModel;
    private ViewManagerModel viewManagerModel;
    private HomeViewModel homeViewModel;

    public ContactPresenter(ContactViewModel contactViewModel,
            ViewManagerModel viewManagerModel,
            HomeViewModel homeViewModel) {
        this.contactViewModel = contactViewModel;
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel = homeViewModel;
    }

    /**
     * Prepares to display the success message after the contact use case.
     */
    @Override
    public void prepareSuccessView() {
        contactViewModel.getState().setError(""); // explicitly set error to none
        contactViewModel.firePropertyChanged();   // state has been changed,

        viewManagerModel.setActiveView(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares to display the error message given.
     *
     * @param error the error message to display
     */
    @Override
    public void prepareFailView(String error) {
        contactViewModel.getState().setError(error);
        contactViewModel.firePropertyChanged();
    }
}
