//
//  DessertFavoriteView.swift
//  iosApp
//
//  Created by Michael Stromer on 1/17/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI

@available(iOS 14.0, *)
struct DessertFavoriteView: View {
    
    @StateObject var viewModel = ViewModelFactory.viewModel(forType: DessertFavoriteViewModel.self)
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.favorites, id: \.id) { dessert in
                    NavigationLink(destination: DessertDetailView(dessertId: dessert.id)) {
                        DessertListRowView(dessert: dessert)
                    }
                }
            }
            .navigationTitle("Favorites")
            .onAppear() {
                viewModel.fetchFavorites()
            }
        }
    }
    
}
