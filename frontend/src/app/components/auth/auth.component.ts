import {Component, OnInit} from '@angular/core';
import {UpperCasePipe} from "@angular/common";
import {FormsModule, NgForm} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {CurrentService} from "../../services/current.service";
import {AuthService} from "../../services/auth.service";

@Component({
    standalone: true,
    selector: 'app-auth',
    templateUrl: './auth.component.html',
    imports: [
        UpperCasePipe,
        FormsModule,
        HttpClientModule
    ],
    providers: [AuthService],
    styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
    login: string = "";
    password: string = "";

    constructor(private authService: AuthService, private currentService: CurrentService) {
    }

    setErrorText(text: string) {
        let authErrorParagraph = document.getElementById("auth_error");

        if (authErrorParagraph != null) {
            authErrorParagraph.innerText = text;
        }
    }

    onSubmit(form: NgForm) {
        this.authService.auth({login: this.login, password: this.password})
            .subscribe({
                next: (data: any) => {
                    if (data !== null) {
                        this.setErrorText("");
                        this.currentService.setUser({login: this.login, password: this.password});
                    } else {
                        this.setErrorText("Неправильный логин или пароль");
                    }
                    console.log("user auth data: ", data);
                },
                error: error => {
                    console.log("error: ", error);
                    this.setErrorText("Неправильный логин или пароль");
                }
            });
    }

    ngOnInit() {
    }

}
