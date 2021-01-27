import SwiftUI
import shared

@available(iOS 14.0, *)
struct ContentView: View {
    var body: some View {
        TabView {
            DessertListView()
                .tabItem {
                    Label("Desserts", systemImage: "safari")
                }
            DessertFavoriteView()
                .tabItem {
                    Label("Favorites", systemImage: "heart.fill")
                }
            LoginView().tabItem {
                Label("Profile", systemImage: "person")
            }
        }
    }
}
