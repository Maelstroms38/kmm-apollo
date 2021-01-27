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
    
    @StateObject var dessertListViewModel = DessertListViewModel()
    
    var body: some View {
        NavigationView {
            List {
                ForEach(dessertListViewModel.desserts, id: \.id) { dessert in
                    NavigationLink(destination: DessertDetailView(dessertId: dessert.id)) {
                        DessertListRowView(dessert: dessert)
                    }
                }
                if dessertListViewModel.shouldDisplayNextPage {
                    nextPageView
                }
            }
            .navigationTitle("Desserts")
            .navigationBarItems(trailing:
                                    NavigationLink(destination: DessertCreateView(dessert: nil)) {
                    Image(systemName: "plus")
                }
            )
            .onAppear() {
                dessertListViewModel.currentPage = 0
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
