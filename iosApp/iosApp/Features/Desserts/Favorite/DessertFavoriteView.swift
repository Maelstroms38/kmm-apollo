//
//  DessertFavoriteView.swift
//  iosApp
//
//  Created by Michael Stromer on 1/17/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

@available(iOS 14.0, *)
struct DessertFavoriteView: View {
    
    @StateObject var favoriteViewModel = DessertFavoriteViewModel()
    
    var body: some View {
        NavigationView {
            List {
                ForEach(favoriteViewModel.favorites, id: \.id) { dessert in
                    NavigationLink(destination: DessertDetailView(dessertId: dessert.id)) {
                        DessertListRowView(dessert: dessert)
                    }
                }
            }
            .navigationTitle("Favorites")
            .navigationBarItems(trailing:
                                    NavigationLink(destination: DessertCreateView(dessert: nil)) {
                    Image(systemName: "plus")
                }
            )
            .onAppear() {
                favoriteViewModel.fetchFavorites()
            }
        }
    }
    
}
