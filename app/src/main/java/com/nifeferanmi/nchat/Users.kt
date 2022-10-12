package com.nifeferanmi.nchat

class Users {
    var email : String? = null
    var uid : String? = null
    var name : String? = null

    constructor()

    constructor(email: String?, uid: String?, name: String?){
        this.email = email
        this.uid = uid
        this.name = name

    }
}