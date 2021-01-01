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
    
    var delegate: DessertListViewDelegate?
    
    let repository = DessertRepository()
    
    func fetchDessert(dessertId: String) {
        repository.getDessert(dessertId: dessertId) { [weak self] (data, error) in
            guard let self = self, let dessertId = data?.id, let name = data?.name, let description = data?.description_, let imageUrl = data?.imageUrl, let reviews = data?.reviews as? [GetDessertQuery.Review] else { return }
            
            let reviewsMap = reviews.map { Review(dessertId: dessertId, text: $0.text, rating: $0.rating?.intValue ?? 0) }
            
            self.dessert = Dessert(action: .READ, dessertId: dessertId, name: name, description: description, imageUrl: imageUrl, reviews: reviewsMap)
        }
    }
    
    func updateDessert(dessert: Dessert) {
        repository.updateDessert(dessertId: dessert.dessertId, name: dessert.name, description: dessert.description, imageUrl: dessert.imageUrl) { [weak self] (data, error) in
            guard let self = self,
                  let _ = data else { return }
            self.dessert = dessert
            self.delegate?.onUpdateDessert(updatedDessert: dessert)
        }
    }
    
    func deleteDessert(dessertId: String) {
        repository.deleteDessert(dessertId: dessertId) { [weak self] (deleted, error) in
            guard let self = self else { return }
            self.dessert = nil
            self.delegate?.onDeleteDessert(dessertId: dessertId)
        }
    }
}
