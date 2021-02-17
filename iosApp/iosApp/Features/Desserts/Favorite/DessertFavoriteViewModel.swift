//
//  DessertFavoriteViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 1/17/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class DessertFavoriteViewModel: ViewModel, ObservableObject {
    
    @Published public var favorites: [Dessert] = []

    let dessertRepository: DessertRepository
    
    init(dessertRepository: DessertRepository) {
        self.dessertRepository = dessertRepository
    }
    
    func fetchFavorites() {
        let favorites = self.dessertRepository.getFavoriteDesserts()
        self.favorites = favorites
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        self.dessertRepository.removeFavorite(dessertId: updatedDessert.id)
        self.dessertRepository.saveFavorite(dessert: updatedDessert)
        
        let insertIndex = self.favorites.firstIndex { dessert -> Bool in
            return dessert.id == updatedDessert.id
        }
        
        if let index = insertIndex {
            self.favorites[index] = updatedDessert
        }
    }
    
    func onDeleteDessert(dessertId: String) {
        self.dessertRepository.removeFavorite(dessertId: dessertId)
    }
}
