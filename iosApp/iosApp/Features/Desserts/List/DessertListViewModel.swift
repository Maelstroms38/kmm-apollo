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
    @Published public var desserts: [GetDessertsQuery.Result] = []
    
    let repository = DessertRepository()
    
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
            
            guard let self = self else { return }
            guard let results = data?.results as? [GetDessertsQuery.Result] else { return }
            
            if page > 0 {
                self.desserts.append(contentsOf: results)
            } else {
                self.desserts = results
            }
            
            if let totalPages = data?.info?.pages {
                self.totalPages = totalPages.int32Value
            }
            
            if let count = data?.info?.count {
                self.totalDesserts = count.int32Value
            }
        }
    }
    
    func createDessert(dessert: NewDessertMutation.NewDessert) {
        let result = GetDessertsQuery.Result(__typename: dessert.__typename, id: dessert.id, name: dessert.name, description: dessert.description_, imageUrl: dessert.imageUrl)
        self.desserts.append(result)
    }
    
    func updateDessert(dessert: UpdateDessertMutation.UpdateDessert) {
        let result = GetDessertsQuery.Result(__typename: dessert.__typename, id: dessert.id, name: dessert.name, description: dessert.description_, imageUrl: dessert.imageUrl)
        let insertIndex = self.desserts.firstIndex { dessert -> Bool in
            return dessert.id == result.id
        }
        if let index = insertIndex {
            self.desserts[index] = result
        }
    }
    
    func deleteDessert(dessertId: String) {
        let deletedIndex = self.desserts.firstIndex { dessert -> Bool in
            return dessert.id == dessertId
        }
        if let delete = deletedIndex {
            self.desserts.remove(at: delete)
        }
    }
}
