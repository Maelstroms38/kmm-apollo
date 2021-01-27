//
//  DessertFavoriteViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 1/17/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class DessertFavoriteViewModel: ObservableObject {
    
    @Published public var favorites: [Dessert] = []

    let repository = DessertRepository(apolloProvider: Network.shared.apolloProvider)
    
    func fetchFavorites() {
        let favorites = self.repository.getFavoriteDesserts()
        self.favorites = favorites
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        self.repository.removeFavorite(dessertId: updatedDessert.id)
        self.repository.saveFavorite(dessert: updatedDessert)
        
        let insertIndex = self.favorites.firstIndex { dessert -> Bool in
            return dessert.id == updatedDessert.id
        }
        
        if let index = insertIndex {
            self.favorites[index] = updatedDessert
        }
    }
    
    func onDeleteDessert(dessertId: String) {
        self.repository.removeFavorite(dessertId: dessertId)
    }
}
