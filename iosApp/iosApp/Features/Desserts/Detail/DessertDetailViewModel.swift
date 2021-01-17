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
    
    @Published public var dessert: Dessert?
    
    @Published var isFavorite: Bool?
    
    let repository = DessertRepository(databaseDriverFactory: DatabaseDriverFactory())
    
    var delegate: DessertDelegate?
    
    func fetchDessert(dessertId: String) {
        repository.getDessert(dessertId: dessertId) { [weak self] (data, error) in
            guard let self = self else { return }
            
            let isFavorite = self.repository.isFavorite(dessertId: dessertId)
            self.isFavorite = isFavorite
            
            guard let dessertId = data?.id, let name = data?.name, let description = data?.description_, let imageUrl = data?.imageUrl, let reviews = data?.reviews as? [GetDessertQuery.Review] else { return }
            
            let reviewsMap = reviews.map { Review(id: $0.id, dessertId: dessertId, text: $0.text, rating: $0.rating?.intValue ?? 0) }
            
            self.dessert = Dessert(action: .READ, dessertId: dessertId, name: name, description: description, imageUrl: imageUrl, reviews: reviewsMap)
        }
    }
    
    func saveFavorite(dessertId: String) {
        self.repository.saveFavorite(dessertId: dessertId, completionHandler: { [weak self] (data, error) in
            guard let self = self else { return }
            self.isFavorite = true
        })
    }
    
    func removeFavorite(dessertId: String) {
        self.repository.removeFavorite(dessertId: dessertId)
        self.isFavorite = false
    }
    
    func onCreateDessert(newDessert: Dessert) {
        self.dessert = newDessert
        self.delegate?.onCreateDessert(newDessert: newDessert)
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        self.dessert = updatedDessert
        self.delegate?.onUpdateDessert(updatedDessert: updatedDessert)
    }
    
    func onDeleteDessert(dessertId: String) {
        self.dessert = nil
        self.delegate?.onDeleteDessert(dessertId: dessertId)
    }
    
}
