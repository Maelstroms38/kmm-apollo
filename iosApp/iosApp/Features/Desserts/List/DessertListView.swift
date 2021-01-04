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

protocol DessertListViewDelegate {
    func onCreateDessert(newDessert: Dessert)
    func onUpdateDessert(updatedDessert: Dessert)
    func onDeleteDessert(dessertId: String)
}

@available(iOS 14.0, *)
struct DessertListView: View, DessertListViewDelegate {
    
    @StateObject private var viewModel = DessertListViewModel()
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.desserts, id: \.dessertId) { dessert in
                    NavigationLink(destination: DessertDetailView(delegate: self, dessertId: dessert.dessertId), label: {
                        DessertListRowView(dessert: dessert)
                    })
                }
                if viewModel.shouldDisplayNextPage {
                    nextPageView
                }
            }
            .navigationTitle("Desserts")
            .navigationBarItems(trailing:
                NavigationLink(destination: DessertCreateView(delegate: self)) {
                    Image(systemName: "square.and.pencil")
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
        viewModel.createDessert(newDessert: newDessert)
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        viewModel.updateDessert(updatedDessert: updatedDessert)
    }
    
    func onDeleteDessert(dessertId: String) {
        viewModel.deleteDessert(dessertId: dessertId)
    }
}
