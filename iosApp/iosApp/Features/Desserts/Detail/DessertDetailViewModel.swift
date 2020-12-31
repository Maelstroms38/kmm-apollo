//
//  DessertDetailViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 12/30/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

class DessertDetailViewModel: ObservableObject {
    @Published public var dessert: GetDessertQuery.Dessert?
    
    let repository = DessertRepository()
    
    func fetchDessert(dessertId: String) {
        repository.getDessert(dessertId: dessertId) { [weak self] (data, error) in
            guard let self = self else { return }
            if let dessert = data {
                self.dessert = dessert
            }
        }
    }
    
    func newDessert(name: String, description: String, imageUrl: String) {
        repository.doNewDessert(name: name, description: description, imageUrl: imageUrl) { [weak self] (data, error) in
            guard let self = self else { return }
            if let dessert = data {
                self.dessert = GetDessertQuery.Dessert(__typename: dessert.__typename, id: dessert.id, name: dessert.name, description: dessert.description_, imageUrl: dessert.imageUrl, reviews: [])
            }
        }
    }
    
    func updateDessert(dessertId: String, name: String, description: String, imageUrl: String) {
        repository.updateDessert(dessertId: dessertId, name: name, description: description, imageUrl: imageUrl) { [weak self] (data, error) in
            guard let self = self else { return }
            if let dessert = data {
                self.dessert = GetDessertQuery.Dessert(__typename: dessert.__typename, id: dessert.id, name: dessert.name, description: dessert.description_, imageUrl: dessert.imageUrl, reviews: self.dessert?.reviews)
            }
        }
    }
    
    func deleteDessert(dessertId: String) {
        repository.deleteDessert(dessertId: dessertId) { [weak self] (deleted, error) in
            guard let self = self else { return }
            self.dessert = nil
        }
    }
}
