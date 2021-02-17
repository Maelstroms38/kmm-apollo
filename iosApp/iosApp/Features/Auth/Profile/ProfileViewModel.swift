//
//  ProfileViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 1/26/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class ProfileViewModel: ViewModel, ObservableObject {
    
    @Published public var desserts: [Dessert] = []
    
    let authRepository: AuthRepository
    
    init(authRepository: AuthRepository) {
        self.authRepository = authRepository
    }
    
    func fetchDesserts() {
        self.authRepository.getProfileDesserts(completionHandler: { [weak self] (data, error) in
            guard let self = self,
                  let desserts = data else {return}
            self.desserts = desserts
        })
    }
}
