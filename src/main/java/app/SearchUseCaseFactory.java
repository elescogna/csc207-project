package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeViewModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.home.HomeDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.SearchView;

import javax.swing.*;
import java.io.IOException;

public class SearchUseCaseFactory {



    /** Prevent instantiation. */
    private SearchUseCaseFactory() {}

    public static SearchView
    create(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel,
           HomeViewModel homeViewModel,
           SearchResultViewModel searchResultViewModel,
           SearchDataAccessInterface clothingDataAccessObject,
           SearchDataAccessInterface furnitureDataAccessObject,
           SearchDataAccessInterface schoolItemDataAccessObject,
           SearchDataAccessInterface technologyDataAccessObject) {

        try {
            SearchController searchController = createSearchUseCase(
                    viewManagerModel, searchViewModel, searchResultViewModel, clothingDataAccessObject,
                    furnitureDataAccessObject, schoolItemDataAccessObject,
                    technologyDataAccessObject);
            return new SearchView(searchViewModel, searchController, homeViewModel, viewManagerModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not access Atlas Database.");
        }

        return null;
    }

    private static SearchController createSearchUseCase(ViewManagerModel viewManagerModel,
                      SearchViewModel searchViewModel,
                      SearchResultViewModel searchResultViewModel,
                      SearchDataAccessInterface clothingDataAccessObject,
                      SearchDataAccessInterface furnitureDataAccessObject,
                      SearchDataAccessInterface schoolItemDataAccessInterface,
                      SearchDataAccessInterface technologyDataAccessInterface)
            throws IOException {

        // Pass this method's parameters to the Presenter.
        SearchOutputBoundary searchOutputBoundary =
                new SearchPresenter(viewManagerModel, searchViewModel, searchResultViewModel);

        SearchInputBoundary searchInteractor =
                new SearchInteractor(clothingDataAccessObject, furnitureDataAccessObject,
                        schoolItemDataAccessInterface,
                        technologyDataAccessInterface, searchOutputBoundary);

        return new SearchController(searchInteractor);
    }
}
