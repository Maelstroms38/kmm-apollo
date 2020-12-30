import SwiftUI
import shared

@available(iOS 14.0, *)
struct ContentView: View {
    var body: some View {
        TabView {
            DessertListView()
                .tabItem {
                    Label("Desserts", systemImage: "person.crop.square.fill.and.at.rectangle")
                }
        }
    }
}

@available(iOS 14.0, *)
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
