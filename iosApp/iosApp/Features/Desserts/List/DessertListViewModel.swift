//
//  DessertListViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 12/29/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

class DessertListViewModel: ObservableObject {
    @Published public var desserts: [Dessert] = []
    
    let repository: DessertRepository
    
    init(apolloProvider: ApolloProvider) {
        repository = DessertRepository(apolloProvider: apolloProvider)
    }
    
    public var currentPage: Int32 = 0 {
        didSet {
            fetchDesserts()
        }
    }
    
    public var shouldDisplayNextPage: Bool {
        if desserts.isEmpty == false,
           let totalPages = totalPages,
           currentPage < totalPages {
            return true
        }
        return false
    }

    public private(set) var totalPages: Int32?
    public private(set) var totalDesserts: Int32?
    
    func fetchDesserts() {
        let page = currentPage
        repository.getDesserts(page: page, size: 10) { [weak self] (data, error) in
            
            guard let self = self,
                  let desserts = data?.results else { return }
            
            if page > 0 {
                self.desserts.append(contentsOf: desserts)
            } else {
                self.desserts = desserts
            }
            
            if let totalPages = data?.info?.pages {
                self.totalPages = totalPages
            }

            if let count = data?.info?.count {
                self.totalDesserts = count
            }
            
        }
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
