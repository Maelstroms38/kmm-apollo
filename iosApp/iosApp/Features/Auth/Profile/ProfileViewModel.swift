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
    
    let repository: AuthRepository = AuthRepository(apolloProvider: Apollo.shared.apolloProvider)
    
    func fetchDesserts() {
        self.repository.getProfileDesserts(completionHandler: { [weak self] (data, error) in
            guard let self = self,
                  let desserts = data else {return}
            self.desserts = desserts
        })
    }
}
