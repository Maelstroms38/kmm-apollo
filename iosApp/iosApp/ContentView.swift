import SwiftUI
import shared

@available(iOS 14.0, *)
struct ContentView: View, DessertDelegate {
    
    private let dessertListViewModel: DessertListViewModel
    private let favoriteViewModel: DessertFavoriteViewModel
    private let createViewModel: DessertCreateViewModel
    private let detailViewModel: DessertDetailViewModel
    private let loginViewModel: LoginViewModel
    private let profileViewModel: ProfileViewModel
    
    init(apolloProvider: ApolloProvider) {
        dessertListViewModel = DessertListViewModel(apolloProvider: apolloProvider)
        favoriteViewModel = DessertFavoriteViewModel(apolloProvider: apolloProvider)
        createViewModel = DessertCreateViewModel(apolloProvider: apolloProvider)
        detailViewModel = DessertDetailViewModel(apolloProvider: apolloProvider)
        loginViewModel = LoginViewModel(apolloProvider: apolloProvider)
        profileViewModel = ProfileViewModel(apolloProvider: apolloProvider)
    }
    
    var body: some View {
        TabView {
            DessertListView(delegate: self, detailViewModel: detailViewModel, createViewModel: createViewModel, dessertListViewModel: dessertListViewModel)
                .tabItem {
                    Label("Desserts", systemImage: "safari")
                }
            DessertFavoriteView(delegate: self, favoriteViewModel: favoriteViewModel, detailViewModel: detailViewModel, createViewModel: createViewModel)
                .tabItem {
                    Label("Favorites", systemImage: "heart.fill")
                }
            LoginView(loginViewModel: loginViewModel, delegate: self, profileViewModel: profileViewModel, detailViewModel: detailViewModel, createViewModel: createViewModel).tabItem {
                Label("Profile", systemImage: "person")
            }
        }
    }
    
    func onCreateDessert(newDessert: Dessert) {
        dessertListViewModel.onCreateDessert(newDessert: newDessert)
        profileViewModel.onCreateDessert(newDessert: newDessert)
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        dessertListViewModel.onUpdateDessert(updatedDessert: updatedDessert)
        favoriteViewModel.onUpdateDessert(updatedDessert: updatedDessert)
        profileViewModel.onUpdateDessert(updatedDessert: updatedDessert)
    }
    
    func onDeleteDessert(dessertId: String) {
        dessertListViewModel.onDeleteDessert(dessertId: dessertId)
        favoriteViewModel.onDeleteDessert(dessertId: dessertId)
        profileViewModel.onDeleteDessert(dessertId: dessertId)
    }
}
