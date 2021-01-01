//
//  Dessert.swift
//  iosApp
//
//  Created by Michael Stromer on 1/1/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

struct Dessert {
    let action: DessertAction
    let dessertId: String
    let name: String
    let description: String
    let imageUrl: String
    let reviews: [Review]
    
    init(action: DessertAction = .READ, dessertId: String, name: String = "", description: String = "", imageUrl: String = "", reviews: [Review] = []) {
        self.action = action
        self.dessertId = dessertId
        self.name = name
        self.description = description
        self.imageUrl = imageUrl
        self.reviews = reviews
    }
}
