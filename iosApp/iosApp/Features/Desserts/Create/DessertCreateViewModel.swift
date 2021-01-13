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
    
    let repository = DessertRepository()
    
    func createDessert(newDessert: Dessert) {
        repository.doNewDessert(name: newDessert.name, description: newDessert.description, imageUrl: newDessert.imageUrl) { [weak self] (data, error) in
            guard let self = self,
                  let dessertId = data?.id,
                  let name = data?.name,
                  let description = data?.description_,
                  let imageUrl = data?.imageUrl else { return }
            
            let dessertData = Dessert(dessertId: dessertId, name: name, description: description, imageUrl: imageUrl)
            self.delegate?.onCreateDessert(newDessert: dessertData)
        }
    }
    
    func updateDessert(dessert: Dessert) {
        repository.updateDessert(dessertId: dessert.dessertId, name: dessert.name, description: dessert.description, imageUrl: dessert.imageUrl) { [weak self] (data, error) in
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
