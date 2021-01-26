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
    
    @StateObject var detailViewModel: DessertDetailViewModel
    
    private(set) var dessert: Dessert
    
    private(set) var delegate: DessertDelegate
    
    private(set) var createViewModel: DessertCreateViewModel
    
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
                if let reviews = detailViewModel.reviews {
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
                    if (detailViewModel.isFavorite ?? false) {
                        detailViewModel.removeFavorite(dessertId: dessert.id)
                    } else {
                        detailViewModel.saveFavorite(dessert: dessert)
                    }
                }, label: {
                    Image(systemName: detailViewModel.isFavorite ?? false ? "heart.fill" : "heart")
                })
                Button(action: {
                    self.isEditingViewShown = true
                }, label: {
                    Image(systemName: "square.and.pencil")
                })
            }
        )
        .sheet(isPresented: $isEditingViewShown) {
            DessertCreateView(delegate: self, createViewModel: createViewModel, dessert: detailViewModel.dessert)
        }
        .onAppear() {
            detailViewModel.delegate = delegate
            detailViewModel.fetchDessert(dessertId: dessert.id)
        }
    }
    
    func onCreateDessert(newDessert: Dessert) {
        detailViewModel.onCreateDessert(newDessert: newDessert)
    }
    
    func onUpdateDessert(updatedDessert: Dessert) {
        detailViewModel.onUpdateDessert(updatedDessert: updatedDessert)
    }
    
    func onDeleteDessert(dessertId: String) {
        presentationMode.wrappedValue.dismiss()
        detailViewModel.onDeleteDessert(dessertId: dessertId)
    }
    
}
