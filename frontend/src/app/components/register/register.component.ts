import {Component, OnInit} from '@angular/core';
import {UpperCasePipe} from "@angular/common";
import {FormsModule, NgForm} from "@angular/forms";
import {RegisterService} from "../../services/register.service";
import {User} from "../../models/interfaces";
import {HttpClientModule} from "@angular/common/http";
import {CurrentService} from "../../services/current.service";

@Component({
    standalone: true,
    selector: 'app-register',
    templateUrl: './register.component.html',
    imports: [
        UpperCasePipe,
        FormsModule,
        HttpClientModule
    ],
    providers: [RegisterService],
    styleUrl: "./register.component.css"
})
export class RegisterComponent implements OnInit {
    login: string = "";
    password: string = "";


    constructor(private registerService: RegisterService, private currentService: CurrentService) {
    }

    setErrorText(text: string) {
        let registerErrorParagraph = document.getElementById("register_error");

        if (registerErrorParagraph != null) {
            registerErrorParagraph.innerText = text;
        }
    }

    onSubmit(form: NgForm) {
        this.registerService.register({login: this.login, password: this.password})
            .subscribe({
                next: (data: any) => {
                    if (data !== null) {
                        this.setErrorText(" ");
                        this.currentService.setUser({login: this.login, password: this.password});
                    } else {
                        this.setErrorText("Логин занят");
                    }
                    console.log("user register data: ", data);
                },
                error: error => {
                    console.log("error: ", error);
                    this.setErrorText("Логин занят");
                }
            });
    }

    ngOnInit() {
    }

}
