import SwiftUI
import shared

@available(iOS 14.0, *)
struct ContentView: View, DessertDelegate {
    
    private let viewModel = DessertListViewModel()
    private let favoriteViewModel = DessertFavoriteViewModel()
    
    var body: some View {
        TabView {
            DessertListView(delegate: self, viewModel: viewModel)
                .tabItem {
                    Label("Desserts", systemImage: "safari")
                }
            DessertFavoriteView(delegate: self, viewModel: favoriteViewModel)
                .tabItem {
                    Label("Favorites", systemImage: "heart.fill")
                }
        }
    }
    
    func onCreateDessert(newDessert: Dessert) {
        viewModel.onCreateDessert(newDessert: newDessert)
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        viewModel.onUpdateDessert(updatedDessert: updatedDessert)
        favoriteViewModel.onUpdateDessert(updatedDessert: updatedDessert)
    }
    
    func onDeleteDessert(dessertId: String) {
        viewModel.onDeleteDessert(dessertId: dessertId)
        favoriteViewModel.onDeleteDessert(dessertId: dessertId)
    }
}
