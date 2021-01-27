//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Michael Stromer on 1/26/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class LoginViewModel: ObservableObject {
    
    let repository: AuthRepository = AuthRepository(apolloProvider: Network.shared.apolloProvider)
    
    @Published public var token: String = ""
    
    init() {
        token = getAuthToken()
    }
    
    func signIn(email: String, password: String) {
        repository.signIn(email: email, password: password) { [weak self] (data, error) in
            guard let self = self,
                  let token = data else { return }
            self.token = token
        }
    }
    
    func signUp(email: String, password: String) {
        repository.signUp(email: email, password: password) { [weak self] (data, error) in
            guard let self = self,
                  let token = data else { return }
            self.token = token
            
        }
    }
    
    func getAuthToken() -> String {
        return repository.getAuthToken()
    }
    
    func deleteAuthToken() {
        repository.deleteAuthToken()
        self.token = ""
    }
}
