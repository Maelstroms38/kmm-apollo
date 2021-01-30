//
//  DessertCreateViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 1/3/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

class DessertCreateViewModel: ObservableObject {
    @Published var dessert: Dessert?
    
    let repository: DessertRepository = DessertRepository(apolloProvider: Apollo.shared.apolloProvider)
    
    func createDessert(newDessert: Dessert) {
        repository.doNewDessert(dessertInput: DessertInput(description: newDessert.description_, imageUrl: newDessert.imageUrl, name: newDessert.name)) { [weak self] (data, error) in
            guard let self = self,
                  let newDessert = data else { return }
            self.dessert = newDessert
        }
    }
    
    func updateDessert(dessert: Dessert) {
        repository.updateDessert(dessertId: dessert.id, dessertInput: DessertInput(description: dessert.description_, imageUrl: dessert.imageUrl, name: dessert.name)) { [weak self] (data, error) in
            guard let self = self,
                  let updatedDessert = data else { return }
            self.dessert = updatedDessert
        }
    }
    
    func deleteDessert(dessertId: String) {
        repository.deleteDessert(dessertId: dessertId) { [weak self] (deleted, error) in
            guard let self = self else { return }
            self.dessert = nil
        }
    }
}
