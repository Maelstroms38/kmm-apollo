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

class DessertDetailViewModel: ViewModel, ObservableObject {
    
    @Published public var dessert: Dessert?
    
    @Published public var reviews: [Review]?
    
    @Published var isFavorite: Bool?
    
    @Published var userState: UserState?
    
    let dessertRepository: DessertRepository
    
    let authRepository: AuthRepository
    
    init(dessertRepository: DessertRepository, authRepository: AuthRepository) {
        self.dessertRepository = dessertRepository
        self.authRepository = authRepository
        self.userState = authRepository.getUserState()
    }
    
    @Environment(\.presentationMode) var presentationMode
    
    func fetchDessert(dessertId: String, completion: @escaping () -> Void) {
        dessertRepository.getDessert(dessertId: dessertId) { [weak self] (data, error) in
            defer { completion() }
            do {
                guard let self = self else { return }
                let favorite = self.dessertRepository.getFavoriteDessert(dessertId: dessertId)
                let isFavorite = favorite != nil
                self.isFavorite = isFavorite
                
                guard let dessert = data?.dessert,
                      let reviews = data?.reviews else {
                    self.dessert = nil
                    return
                }
                if (isFavorite) {
                    self.updateFavorite(dessert: dessert)
                }
                self.dessert = dessert
                self.reviews = reviews
            }
        }
    }
    
    func saveFavorite(dessert: Dessert) {
        self.dessertRepository.saveFavorite(dessert: dessert)
        self.isFavorite = true
    }
    
    func updateFavorite(dessert: Dessert) {
        self.dessertRepository.updateFavorite(dessert: dessert)
    }
    
    func removeFavorite(dessertId: String) {
        self.dessertRepository.removeFavorite(dessertId: dessertId)
        self.isFavorite = false
    }
    
    func getUserState() -> UserState? {
        return authRepository.getUserState()
    }
}
