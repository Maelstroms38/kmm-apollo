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
struct DessertListView: View, DessertDelegate {
    
    @StateObject private var viewModel = DessertListViewModel()
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.desserts, id: \.dessertId) { dessert in
                    NavigationLink(destination: DessertDetailView(dessert: dessert, delegate: self)) {
                        DessertListRowView(dessert: dessert)
                    }
                }
                if viewModel.shouldDisplayNextPage {
                    nextPageView
                }
            }
            .navigationTitle("Desserts")
            .navigationBarItems(trailing:
                NavigationLink(destination: DessertCreateView(delegate: self, dessert: nil)) {
                    Image(systemName: "plus")
                }
            )
            .onAppear() {
                viewModel.fetchDesserts()
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
            viewModel.currentPage += 1
        })
    }
    
    func onCreateDessert(newDessert: Dessert) {
        viewModel.onCreateDessert(newDessert: newDessert)
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        viewModel.onUpdateDessert(updatedDessert: updatedDessert)
    }
    
    func onDeleteDessert(dessertId: String) {
        viewModel.onDeleteDessert(dessertId: dessertId)
    }
}
