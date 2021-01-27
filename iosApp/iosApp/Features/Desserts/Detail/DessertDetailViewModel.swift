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
    
    @Published public var reviews: [Review]?
    
    @Published var isFavorite: Bool?
    
    let repository = DessertRepository(apolloProvider: Apollo.shared.apolloProvider)
    
    @Environment(\.presentationMode) var presentationMode
    
    func fetchDessert(dessertId: String, completion: @escaping () -> Void) {
        repository.getDessert(dessertId: dessertId) { [weak self] (data, error) in
            defer { completion() }
            do {
                guard let self = self else { return }
                let isFavorite = self.repository.isFavorite(dessertId: dessertId)
                self.isFavorite = isFavorite
                
                guard let dessert = data?.dessert,
                      let reviews = data?.reviews else {
                    self.dessert = nil
                    return
                }
                self.dessert = dessert
                self.reviews = reviews
            }
        }
    }
    
    func saveFavorite(dessert: Dessert) {
        self.repository.saveFavorite(dessert: dessert)
        self.isFavorite = true
    }
    
    func removeFavorite(dessertId: String) {
        self.repository.removeFavorite(dessertId: dessertId)
        self.isFavorite = false
    }
}
