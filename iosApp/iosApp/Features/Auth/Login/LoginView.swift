//
//  LoginView.swift
//  iosApp
//
//  Created by Michael Stromer on 1/26/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

@available(iOS 14.0, *)
struct LoginView: View {
    @State private var login: Bool = true
    @State private var email: String = ""
    @State private var password: String = ""
    
    @StateObject var loginViewModel = LoginViewModel()
    
    private(set) var delegate: DessertDelegate
    
    private(set) var profileViewModel: ProfileViewModel
    
    private var label: String {
        return login ? "Login" : "Sign Up"
    }
    
    var body: some View {
        if (loginViewModel.token.isEmpty) {
            NavigationView {
                Form {
                    Section {
                        TextField("Email", text: $email)
                        SecureField("Password", text: $password)
                    }
                    Section {
                        Button(
                            action: {
                                if (login) {
                                    loginViewModel.signIn(email: email, password: password)
                                } else {
                                    loginViewModel.signUp(email: email, password: password)
                                }
                            },
                            label: { Text(label) }
                        )
                    }
                }
                .navigationTitle(label)
            }
            
        } else {
            ProfileView(delegate: delegate, logoutHandler: {
                    loginViewModel.deleteAuthToken()
            })
        }
    }
}
