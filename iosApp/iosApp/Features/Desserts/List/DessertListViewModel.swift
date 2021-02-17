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

class DessertListViewModel: ViewModel, ObservableObject {
    
    private let dessertRepository: DessertRepository
    
    // UI binding
    @Published public var desserts: [Dessert] = []
    
    init(dessertRepository: DessertRepository)
    {
        self.dessertRepository = dessertRepository
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
        dessertRepository.getDesserts(page: page, size: 10) { [weak self] (data, error) in
            
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
}
