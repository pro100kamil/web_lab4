import {Injectable, OnInit} from "@angular/core";
import {User} from "../models/interfaces";

@Injectable({
    providedIn: "root",       // глобальный сервис
})
export class CurrentService implements OnInit {
    private itemName = "user";
    // static user: User | null = {login: "count", password: "kol"};

    setUser(user: User) {
        // CurrentService.user = user;
        // localStorage.setItem("login", user.login);
        // localStorage.setItem("password", user.password);

        localStorage.setItem(this.itemName, btoa(user.login + ":" + user.password));
    }

    clear() {
        // localStorage.removeItem("login");
        // localStorage.removeItem("password");
        localStorage.removeItem(this.itemName);
    }

    // getUser(): User | null {
    //     if (localStorage.getItem("login") === null || localStorage.getItem("password") === null) {
    //         return null;
    //     }
    //     return {
    //         login: localStorage.getItem("login") as string,
    //         password: localStorage.getItem("password") as string
    //     };
    // }

    getUser(): string | null {
        return localStorage.getItem(this.itemName);
    }

    isAuthenticatedUser() {
        return this.getUser() != null;
    }

    ngOnInit(): void {
        console.log("current init");
    }
}
