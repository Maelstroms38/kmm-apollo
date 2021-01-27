import SwiftUI
import shared

@available(iOS 14.0, *)
struct ContentView: View, DessertDelegate {
    
    private let dessertListViewModel = DessertListViewModel()
    private let favoriteViewModel = DessertFavoriteViewModel()
    private let profileViewModel = ProfileViewModel()
    
    var body: some View {
        TabView {
            DessertListView(delegate: self, dessertListViewModel: dessertListViewModel)
                .tabItem {
                    Label("Desserts", systemImage: "safari")
                }
            DessertFavoriteView(delegate: self, favoriteViewModel: favoriteViewModel)
                .tabItem {
                    Label("Favorites", systemImage: "heart.fill")
                }
            LoginView(delegate: self, profileViewModel: profileViewModel).tabItem {
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
