import {AfterViewInit, Component} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {RouterLink, RouterOutlet} from "@angular/router";
import {AuthComponent} from "../auth/auth.component";
import {RegisterComponent} from "../register/register.component";
import {NgIf} from "@angular/common";
import {CurrentService} from "../../services/current.service";

@Component({
    selector: "app-index",
    standalone: true,
    imports: [FormsModule, AuthComponent, RegisterComponent, RouterOutlet, RouterLink, NgIf],
    templateUrl: "./index.component.html",
    styleUrl: "./index.component.css"
})
export class IndexComponent implements AfterViewInit {
    colors: string[] = ["red", "yellow", "green"];
    readonly UPDATE_INTERVAL_IN_MS= 1000;

    hours: HTMLElement = <HTMLElement><unknown>null;
    minutes: HTMLElement = <HTMLElement><unknown>null;
    seconds: HTMLElement = <HTMLElement><unknown>null;

    constructor(public currentService: CurrentService) { }

    logout() {
        this.currentService.clear();
    }

    setTime() {
        let curDate = new Date();

        let h = curDate.getHours();
        let hh = (h >= 10 ? "" : "0") + h.toString();

        let m = curDate.getMinutes();
        let mm = (m >= 10 ? "" : "0") + m.toString();

        let s = curDate.getSeconds();
        let ss = (s >= 10 ? "" : "0") + s.toString();

        this.hours.innerText = hh;
        this.minutes.innerText = mm;
        this.seconds.innerText = ss;

        this.hours.style.color = this.colors[(this.colors.indexOf(this.hours.style.color) + 1) % this.colors.length];
        this.minutes.style.color = this.colors[(this.colors.indexOf(this.minutes.style.color) + 1) % this.colors.length];
        this.seconds.style.color = this.colors[(this.colors.indexOf(this.seconds.style.color) + 1) % this.colors.length];
    }

    ngAfterViewInit(): void {
        this.hours = <HTMLElement>document.getElementById("hours");
        this.minutes = <HTMLElement>document.getElementById("minutes");
        this.seconds = <HTMLElement>document.getElementById("seconds");

        this.setTime();
        setInterval(() => {this.setTime();}, this.UPDATE_INTERVAL_IN_MS);
    }

}
