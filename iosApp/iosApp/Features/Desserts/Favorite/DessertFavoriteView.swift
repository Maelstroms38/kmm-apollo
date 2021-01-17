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
    
    private(set) var delegate: DessertDelegate
    
    @StateObject var viewModel: DessertFavoriteViewModel
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.favorites, id: \.dessertId) { dessert in
                    NavigationLink(destination: DessertDetailView(dessert: dessert, delegate: delegate)) {
                        DessertListRowView(dessert: dessert)
                    }
                }
            }
            .navigationTitle("Favorites")
            .navigationBarItems(trailing:
                NavigationLink(destination: DessertCreateView(delegate: delegate, dessert: nil)) {
                    Image(systemName: "plus")
                }
            )
            .onAppear() {
                viewModel.fetchFavorites()
            }
        }
    }
    
}
