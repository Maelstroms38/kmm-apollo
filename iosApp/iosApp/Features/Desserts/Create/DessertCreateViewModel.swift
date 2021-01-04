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
    @Published public var dessert: Dessert?
    
    var delegate: DessertListViewDelegate?
    
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
}
