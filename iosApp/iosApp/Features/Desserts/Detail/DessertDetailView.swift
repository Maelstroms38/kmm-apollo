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
    
    @StateObject var detailViewModel = DessertDetailViewModel()
    
    private(set) var dessertId: String
    
    @State var isEditingViewShown = false {
        didSet {
            if isEditingViewShown == false &&
                detailViewModel.dessert == nil {
                self.presentationMode.wrappedValue.dismiss()
            }
        }
    }
    
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        List {
            Section(header: Text("Preview")) {
                HStack {
                    Spacer()
                    if let image = detailViewModel.dessert?.imageUrl,
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
                Text(detailViewModel.dessert?.description_ ?? "")
                    .font(.body)
            }
                
            Section(header: Text("Reviews")) {
                if let userId = detailViewModel.userState?.userId,
                   !userId.isEmpty {
                    NavigationLink(destination: ReviewCreateView(review: Review(id: "new", dessertId: dessertId, userId: "", text: "", rating: 0))) {
                        HStack {
                            VStack(alignment: .leading) {
                                Text("Create Review")
                                    .font(.title3)
                                    .foregroundColor(.accentColor)
                            }
                        }
                    }
                }
                ForEach(detailViewModel.reviews ?? [], id: \.id) { review in
                    if (review.userId == detailViewModel.userState?.userId ?? "") {
                        NavigationLink(destination: ReviewCreateView(review: review)) {
                                DessertReviewRowView(review: review)
                            }
                    } else {
                        DessertReviewRowView(review: review)
                    }
                }
            }
        }
        .listStyle(GroupedListStyle())
        .navigationBarTitle(detailViewModel.dessert?.name ?? "", displayMode: .inline)
        .navigationBarItems(trailing:
            HStack {
                Button(action: {
                    if (detailViewModel.isFavorite ?? false) {
                        detailViewModel.removeFavorite(dessertId: dessertId)
                    } else {
                        guard let dessert = detailViewModel.dessert else { return }
                        detailViewModel.saveFavorite(dessert: dessert)
                    }
                }, label: {
                    Image(systemName: detailViewModel.isFavorite ?? false ? "heart.fill" : "heart")
                })
                if (detailViewModel.userState?.userId ?? "" == detailViewModel.dessert?.userId ?? "") {
                    Button(action: {
                        self.isEditingViewShown = true
                    }, label: {
                        Image(systemName: "square.and.pencil")
                    })
                }
            }
        )
        .sheet(isPresented: $isEditingViewShown) {
            DessertCreateView(dessert: detailViewModel.dessert)
            .onDisappear() {
                detailViewModel.fetchDessert(dessertId: dessertId) {
                    self.isEditingViewShown = false
                }
            }
        }
        .onAppear() {
            detailViewModel.userState = detailViewModel.getUserState()
            detailViewModel.fetchDessert(dessertId: dessertId) {
                self.isEditingViewShown = false
            }
        }
    }
}
