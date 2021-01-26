//
//  ProfileView.swift
//  iosApp
//
//  Created by Michael Stromer on 1/26/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

@available(iOS 14.0, *)
struct ProfileView: View {
    private(set) var delegate: DessertDelegate
    
    @StateObject var profileViewModel: ProfileViewModel
    
    private(set) var detailViewModel: DessertDetailViewModel
    
    private(set) var createViewModel: DessertCreateViewModel
    
    var logoutHandler: () -> Void
    
    var body: some View {
        NavigationView {
            List {
                ForEach(profileViewModel.desserts, id: \.id) { dessert in
                    NavigationLink(destination: DessertDetailView(detailViewModel: detailViewModel, dessert: dessert, delegate: delegate, createViewModel: createViewModel)) {
                        DessertListRowView(dessert: dessert)
                    }
                }
            }
            .navigationTitle("Profile")
            .navigationBarItems(trailing:
                HStack {
                    Button(action: {
                        logoutHandler()
                    }, label: {
                        Image(systemName: "arrow.down.left.circle.fill")
                    })
                    NavigationLink(destination: DessertCreateView(delegate: delegate, createViewModel: createViewModel, dessert: nil)) {
                        Image(systemName: "plus")
                    }
                }
            )
            .onAppear() {
                profileViewModel.fetchDesserts()
            }
        }
    }
}
