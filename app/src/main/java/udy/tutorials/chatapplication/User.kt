package udy.tutorials.chatapplication

class User {
    var uname:String?=null
    var email:String?=null
    var uid:String?=null
    constructor(){}

    constructor(uname:String?,pass:String?,uid:String?)
    {
        this.uname=uname
        this.email=pass
        this.uid=uid
    }

}