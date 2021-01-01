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
    func onUpdateDessert(updatedDessert: Dessert)
    func onDeleteDessert(dessertId: String)
}

@available(iOS 14.0, *)
struct DessertListView: View, DessertListViewDelegate {
    
    @StateObject private var viewModel = DessertListViewModel()
    
    @State private var isCreatingViewShown = false
    
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
                Button(action: {
                    self.isCreatingViewShown = true
                }) {
                    Image(systemName: "square.and.pencil")
                }
            )
            .onAppear() {
                viewModel.fetchDesserts()
            }
            .sheet(isPresented: $isCreatingViewShown) {
                VStack {
                    DessertFormView(handler: { dessert in
                        switch dessert.action {
                        case .CREATE:
                            viewModel.createDessert(newDessert: dessert)
                        default:
                            break
                        }
                        
                        self.isCreatingViewShown = false
                    },
                    dessertId: "new", name: "", description: "", imageUrl: "")
                }
            }
            .onDisappear() {
                self.isCreatingViewShown = false
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
    
    func onUpdateDessert(updatedDessert: Dessert) {
        viewModel.updateDessert(updatedDessert: updatedDessert)
    }
    
    func onDeleteDessert(dessertId: String) {
        viewModel.deleteDessert(dessertId: dessertId)
    }
}
