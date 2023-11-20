package interface_adapter.contact;
import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeViewModel;
import use_case.contact.ContactOutputBoundary;

public class ContactPresenter implements ContactOutputBoundary {
    private ContactViewModel contactViewModel;
    private ViewManagerModel viewManagerModel;
    private HomeViewModel homeViewModel;

    @Override
    public void prepareSuccessView() {
        contactViewModel.getState().setError(""); // explicitly set error to none
        contactViewModel.firePropertyChanged();   // state has been changed,
                                                  // trigger to display success
                                                  // message

        viewManagerModel.setActiveView(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        contactViewModel.getState().setError(error);
        contactViewModel.firePropertyChanged();
    }
}