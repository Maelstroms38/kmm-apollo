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
struct DessertDetailView: View, DessertDelegate {
    
    @StateObject private var viewModel = DessertDetailViewModel()
    
    private(set) var dessert: Dessert
    
    private(set) var delegate: DessertDelegate
    
    @State var isEditingViewShown = false
    
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        List {
            Section(header: Text("Preview")) {
                HStack {
                    Spacer()
                    if let image = dessert.imageUrl,
                       let url = URL(string: image) {
                        KFImage(url)
                            .resizable()
                            .aspectRatio(contentMode: .fill)
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
                Text(dessert.description_)
                    .font(.body)
            }
                
            Section(header: Text("Reviews")) {
                if let reviews = viewModel.reviews {
                    ForEach(reviews, id: \.id) { review in
                        DessertReviewRowView(review: review)
                    }
                }
            }
        }
        .listStyle(GroupedListStyle())
        .navigationBarTitle(dessert.name, displayMode: .inline)
        .navigationBarItems(trailing:
            HStack {
                Button(action: {
                    if (viewModel.isFavorite ?? false) {
                        viewModel.removeFavorite(dessertId: dessert.id)
                    } else {
                        viewModel.saveFavorite(dessertId: dessert.id)
                    }
                }, label: {
                    Image(systemName: viewModel.isFavorite ?? false ? "heart.fill" : "heart")
                })
                Button(action: {
                    self.isEditingViewShown = true
                }, label: {
                    Image(systemName: "square.and.pencil")
                })
            }
        )
        .sheet(isPresented: $isEditingViewShown) {
            DessertCreateView(delegate: self, dessert: viewModel.dessert)
        }
        .onAppear() {
            viewModel.delegate = delegate
            viewModel.fetchDessert(dessertId: dessert.id)
        }
    }
    
    func onCreateDessert(newDessert: Dessert) {
        viewModel.onCreateDessert(newDessert: newDessert)
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        viewModel.onUpdateDessert(updatedDessert: updatedDessert)
    }
    
    func onDeleteDessert(dessertId: String) {
        presentationMode.wrappedValue.dismiss()
        viewModel.onDeleteDessert(dessertId: dessertId)
    }
    
}
