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
    
    @StateObject var profileViewModel = ProfileViewModel()
    
    var logoutHandler: () -> Void
    
    var body: some View {
        NavigationView {
            List {
                ForEach(profileViewModel.desserts, id: \.id) { dessert in
                    NavigationLink(destination: DessertDetailView(dessertId: dessert.id, delegate: delegate)) {
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
                    NavigationLink(destination: DessertCreateView(delegate: delegate, dessert: nil)) {
                        Image(systemName: "plus")
                    }
                }
            )
            .onAppear() {
                profileViewModel.delegate = delegate
                profileViewModel.fetchDesserts()
            }
        }
    }
}
