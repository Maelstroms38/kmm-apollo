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
}
