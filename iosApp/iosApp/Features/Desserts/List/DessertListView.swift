//
//  DessertListView.swift
//  iosApp
//
//  Created by Michael Stromer on 12/29/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

@available(iOS 14.0, *)
struct DessertListView: View {
    
    private(set) var delegate: DessertDelegate
    
    private(set) var detailViewModel: DessertDetailViewModel
    
    private(set) var createViewModel: DessertCreateViewModel
    
    @StateObject var dessertListViewModel: DessertListViewModel
    
    var body: some View {
        NavigationView {
            List {
                ForEach(dessertListViewModel.desserts, id: \.id) { dessert in
                    NavigationLink(destination: DessertDetailView(detailViewModel: detailViewModel, dessert: dessert, delegate: delegate, createViewModel: createViewModel)) {
                        DessertListRowView(dessert: dessert)
                    }
                }
                if dessertListViewModel.shouldDisplayNextPage {
                    nextPageView
                }
            }
            .navigationTitle("Desserts")
            .navigationBarItems(trailing:
                                    NavigationLink(destination: DessertCreateView(delegate: delegate, createViewModel: createViewModel, dessert: nil)) {
                    Image(systemName: "plus")
                }
            )
            .onAppear() {
                dessertListViewModel.fetchDesserts()
            }
        }
    }
    
    private var nextPageView: some View {
        HStack {
            Spacer()
            VStack {
                ProgressView()
                Text("Loading...")
            }
            Spacer()
        }
        .onAppear(perform: {
            dessertListViewModel.currentPage += 1
        })
    }
}
