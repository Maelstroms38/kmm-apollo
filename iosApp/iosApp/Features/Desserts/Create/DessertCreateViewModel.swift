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
    
    var delegate: DessertDelegate?
    
    let repository: DessertRepository = DessertRepository(apolloProvider: Network.shared.apolloProvider)
    
    func createDessert(newDessert: Dessert) {
        repository.doNewDessert(name: newDessert.name, description: newDessert.description_, imageUrl: newDessert.imageUrl) { [weak self] (data, error) in
            guard let self = self,
                  let newDessert = data else { return }
            self.delegate?.onCreateDessert(newDessert: newDessert)
        }
    }
    
    func updateDessert(dessert: Dessert) {
        repository.updateDessert(dessertId: dessert.id, name: dessert.name, description: dessert.description_, imageUrl: dessert.imageUrl) { [weak self] (data, error) in
            guard let self = self,
                  let _ = data else { return }
            self.delegate?.onUpdateDessert(updatedDessert: dessert)
        }
    }
    
    func deleteDessert(dessertId: String) {
        repository.deleteDessert(dessertId: dessertId) { [weak self] (deleted, error) in
            guard let self = self else { return }
            self.delegate?.onDeleteDessert(dessertId: dessertId)
        }
    }
}
