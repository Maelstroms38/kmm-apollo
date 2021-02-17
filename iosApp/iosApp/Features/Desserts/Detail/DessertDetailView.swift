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
    
    @StateObject var viewModel = ViewModelFactory.viewModel(forType: DessertDetailViewModel.self)
    
    private(set) var dessertId: String
    
    @State var isEditingViewShown = false {
        didSet {
            if isEditingViewShown == false &&
                viewModel.dessert == nil {
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
                    if let image = viewModel.dessert?.imageUrl,
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
                Text(viewModel.dessert?.description_ ?? "")
                    .font(.body)
            }
                
            Section(header: Text("Reviews")) {
                if let userId = viewModel.userState?.userId,
                   !userId.isEmpty {
                    NavigationLink(destination: ReviewFormView(review: Review(id: "new", dessertId: dessertId, userId: "", text: "", rating: 0))) {
                        HStack {
                            VStack(alignment: .leading) {
                                Text("Create Review")
                                    .font(.title3)
                                    .foregroundColor(.accentColor)
                            }
                        }
                    }
                }
                ForEach(viewModel.reviews ?? [], id: \.id) { review in
                    if (review.userId == viewModel.userState?.userId ?? "") {
                        NavigationLink(destination: ReviewFormView(review: review)) {
                                DessertReviewRowView(review: review)
                            }
                    } else {
                        DessertReviewRowView(review: review)
                    }
                }
            }
        }
        .listStyle(GroupedListStyle())
        .navigationBarTitle(viewModel.dessert?.name ?? "", displayMode: .inline)
        .navigationBarItems(trailing:
            HStack {
                Button(action: {
                    if (viewModel.isFavorite ?? false) {
                        viewModel.removeFavorite(dessertId: dessertId)
                    } else {
                        guard let dessert = viewModel.dessert else { return }
                        viewModel.saveFavorite(dessert: dessert)
                    }
                }, label: {
                    Image(systemName: viewModel.isFavorite ?? false ? "heart.fill" : "heart")
                })
                if (viewModel.userState?.userId ?? "" == viewModel.dessert?.userId ?? "") {
                    Button(action: {
                        self.isEditingViewShown = true
                    }, label: {
                        Image(systemName: "square.and.pencil")
                    })
                }
            }
        )
        .sheet(isPresented: $isEditingViewShown) {
            DessertFormView(dessert: viewModel.dessert)
            .onDisappear() {
                viewModel.fetchDessert(dessertId: dessertId) {
                    self.isEditingViewShown = false
                }
            }
        }
        .onAppear() {
            viewModel.fetchDessert(dessertId: dessertId) {
                self.isEditingViewShown = false
            }
        }
    }
}
