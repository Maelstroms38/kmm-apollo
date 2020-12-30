//
//  DessertDetailView.swift
//  iosApp
//
//  Created by Michael Stromer on 12/30/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import KingfisherSwiftUI
import shared

@available(iOS 14.0, *)
struct DessertDetailView: View {
    @StateObject private var viewModel = DessertDetailViewModel()
    
    let dessertId: String
    
    var body: some View {
        List {
            Section(header: Text(viewModel.dessert?.name ?? "Loading...")) {
                HStack {
                    Spacer()
                    if let image = viewModel.dessert?.imageUrl,
                       let url = URL(string: image) {
                        KFImage(url)
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 150, height: 150)
                            .cornerRadius(25)
                    } else {
                        RoundedRectangle(cornerRadius: 25)
                            .frame(width: 150, height: 150)
                            .foregroundColor(.gray)
                    }
                    Spacer()
                }
            }
        
        Section(header: Text("Summary")) {
            Text(viewModel.dessert?.description_ ?? "")
                .font(.body)
        }
            
        Section(header: Text("Reviews")) {
            ForEach(viewModel.dessert?.reviews as? [GetDessertQuery.Review] ?? [], id: \.id) { review in
                DessertReviewRowView(review: review)
            }
        }
        .listStyle(GroupedListStyle())
        .navigationTitle("Dessert")
        .onAppear() {
            viewModel.fetchDessert(dessertId: dessertId)
        }
    }
    }
    
}
