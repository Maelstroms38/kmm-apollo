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

    let repository = DessertRepository(databaseDriverFactory: DatabaseDriverFactory())
    
    func fetchFavorites() {
        let favorites = self.repository.getFavoriteDesserts()
        let desserts = favorites.map { Dessert(dessertId: $0.id, name: $0.name ?? "", description: $0.description_ ?? "", imageUrl: $0.imageUrl ?? "") }
        self.favorites = desserts
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        self.repository.removeFavorite(dessertId: updatedDessert.dessertId)
        self.repository.saveFavorite(dessertId: updatedDessert.dessertId, completionHandler: { [weak self] (data, error) in
            guard let self = self else { return }
            
            let insertIndex = self.favorites.firstIndex { dessert -> Bool in
                return dessert.dessertId == updatedDessert.dessertId
            }
            if let index = insertIndex {
                self.favorites[index] = updatedDessert
            }
        })
    }
    
    func onDeleteDessert(dessertId: String) {
        self.repository.removeFavorite(dessertId: dessertId)
    }
}
