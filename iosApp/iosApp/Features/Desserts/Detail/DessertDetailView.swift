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
    
    @Environment(\.presentationMode) var presentationMode
    
    let delegate: DessertListViewDelegate
    
    @StateObject private var viewModel = DessertDetailViewModel()
    
    @State private var isEditingViewShown = false
    
    let dessertId: String
    
    var body: some View {
        List {
            Section(header: Text("Preview")) {
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
            Text(viewModel.dessert?.description ?? "")
                .font(.body)
        }
            
        Section(header: Text("Reviews")) {
            ForEach(viewModel.dessert?.reviews ?? [], id: \.dessertId) { review in
                DessertReviewRowView(review: review)
            }
        }
        .listStyle(GroupedListStyle())
        .navigationTitle(viewModel.dessert?.name ?? "")
        navigationBarItems(trailing:
            Button(action: {
                self.isEditingViewShown = true
            }) {
                Image(systemName: "square.and.pencil")
            }
        )
        .onAppear() {
            viewModel.delegate = delegate
            viewModel.fetchDessert(dessertId: dessertId)
        }
        .padding()
        .sheet(isPresented: $isEditingViewShown) {
            VStack {
                DessertFormView(handler: { dessert in
                    let action = dessert.action
                    let dessertId = dessert.dessertId
                    let name = dessert.name
                    let description = dessert.description
                    let imageUrl = dessert.imageUrl
                    let reviews = dessert.reviews
                    let dessert = Dessert(action: action, dessertId: dessertId, name: name, description: description, imageUrl: imageUrl, reviews: reviews)
                    
                    switch dessert.action {
                    case .CREATE:
                        viewModel.newDessert(dessert: dessert)
                    case .UPDATE:
                        viewModel.updateDessert(dessert: dessert)
                    case .DELETE:
                        viewModel.deleteDessert(dessertId: dessertId)
                        self.presentationMode.wrappedValue.dismiss()
                        
                    default:
                        break
                    }
                    
                    self.isEditingViewShown = false
                },
                dessertId: dessertId, name: viewModel.dessert?.name ?? "", description: viewModel.dessert?.description ?? "", imageUrl: viewModel.dessert?.imageUrl ?? "")
            }
        }
    }
    }
    
}
