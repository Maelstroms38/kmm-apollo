import SwiftUI
import shared

func greet() -> String {
    return Greeting().greeting()
}

@available(iOS 14.0, *)
struct ContentView: View {
    var body: some View {
        TabView {
            Text(greet()).tabItem {
                Label("Desserts", systemImage: "list.bullet")
            }
        }
    }
}
