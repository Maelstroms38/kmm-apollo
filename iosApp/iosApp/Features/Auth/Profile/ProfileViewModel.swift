//
//  ProfileViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 1/26/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class ProfileViewModel: ObservableObject {
    
    @Published public var desserts: [Dessert] = []
    
    let repository: AuthRepository = AuthRepository(apolloProvider: Network.shared.apolloProvider)
    
    var delegate: DessertDelegate?
    
    func fetchDesserts() {
        self.repository.getProfileDesserts(completionHandler: { [weak self] (data, error) in
            guard let self = self,
                  let desserts = data else {return}
            self.desserts = desserts
        })
    }
    
    func onCreateDessert(newDessert: Dessert) {
        self.desserts.append(newDessert)
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        let insertIndex = self.desserts.firstIndex { dessert -> Bool in
            return dessert.id == updatedDessert.id
        }
        if let index = insertIndex {
            self.desserts[index] = updatedDessert
        }
    }
    
    func onDeleteDessert(dessertId: String) {
        let deletedIndex = self.desserts.firstIndex { dessert -> Bool in
            return dessert.id == dessertId
        }
        if let delete = deletedIndex {
            self.desserts.remove(at: delete)
        }
    }
}
