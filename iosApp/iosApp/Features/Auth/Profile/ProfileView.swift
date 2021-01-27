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
    @StateObject var profileViewModel = ProfileViewModel()
    
    var logoutHandler: () -> Void
    
    var body: some View {
        NavigationView {
            List {
                ForEach(profileViewModel.desserts, id: \.id) { dessert in
                    NavigationLink(destination: DessertDetailView(dessertId: dessert.id)) {
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
                        Image(systemName: "arrow.left.circle.fill")
                    })
                    NavigationLink(destination: DessertCreateView(dessert: nil)) {
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
