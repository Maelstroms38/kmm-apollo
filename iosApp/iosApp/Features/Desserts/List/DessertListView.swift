//
//  DessertListView.swift
//  iosApp
//
//  Created by Michael Stromer on 12/29/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import SwiftUI

@available(iOS 14.0, *)
struct DessertListView: View {
    @StateObject private var viewModel = DessertListViewModel()
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.desserts, id: \.id) { dessert in
                    NavigationLink(destination: DessertDetailView(dessertId: dessert.id), label: {
                        DessertListRowView(dessert: dessert)
                    })
                }
                if viewModel.shouldDisplayNextPage {
                    nextPageView
                }
            }
            .navigationTitle("Desserts")
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
}
