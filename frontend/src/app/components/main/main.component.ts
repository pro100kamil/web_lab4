import {AfterViewInit, Component} from "@angular/core";
import {FormsModule, NgForm} from "@angular/forms";
import {Attempt} from "../../models/interfaces";
import {CurrentService} from "../../services/current.service";
import {AttemptService} from "../../services/attempt.service";
import {HttpClientModule} from "@angular/common/http";
import {DrawService} from "../../services/draw.service";
import {TableModule} from 'primeng/table';
import {ErrorHttpHandlerService} from "../../services/error-http-handler.service";

@Component({
    selector: "app-main",
    standalone: true,
    imports: [
        FormsModule,
        HttpClientModule,
        TableModule
    ],
    providers: [AttemptService],
    templateUrl: "./main.component.html",
    styleUrl: "./main.component.css"
})
export class MainComponent implements AfterViewInit {

    attempts: Attempt[] = [

    ];

    paramX = 0;
    paramY = 0;
    paramR = 1;

    constructor(private attemptService: AttemptService,
                private currentService: CurrentService,
                private drawService: DrawService,
                private errorHttpHandlerService: ErrorHttpHandlerService) {
    }

    ngAfterViewInit(): void {
        this.drawService.start();
        this.drawService.updateCanvas();

        this.drawAllAttempts();
    }

    drawAllAttempts() {
        this.attemptService.allAttempts()
            .subscribe({
                next: (data: any) => {
                    if (data === null) {
                        return;
                    }
                    console.log("all attempt data: ", data);
                    this.attempts = data;
                    for (let attempt of this.attempts) {
                        this.drawService.drawPointForNg(attempt.x, attempt.y, attempt.hit ? "white" : "red");
                    }
                },
                error: error => {
                    console.log("error: ", error);
                }
            });
    }

    onCanvasClick(event: MouseEvent) {
        let x = event.pageX - this.drawService.canvas.offsetLeft;
        let y = event.pageY - this.drawService.canvas.offsetTop;

        let mathX = x - this.drawService.centerX;
        let mathY = this.drawService.centerY - y;

        let r_ = this.drawService.get_r_();

        //округляем до 4 знаков после точки
        let x_ = +(mathX / this.drawService.R * r_).toFixed(4);
        let y_ = +(mathY / this.drawService.R * r_).toFixed(4);

        console.log("canvas in ts: ", x_, y_, r_);

        this.attemptService.addPoint({paramX: x_, paramY: y_, paramR: r_})
            .subscribe({
                next: (data: any) => {
                    console.log("new attempt data from canvas: ", data);
                    if (data === null) {
                        return;
                    }
                    let attempt: Attempt = {x: data["x"], y: data["y"], r: data["r"], hit: data["hit"]};
                    this.attempts.push(attempt);
                    this.drawService.drawPointForNg(attempt.x, attempt.y, attempt.hit ? "white" : "red");
                },
                error: error => {
                    console.log("error: ", error);
                    this.errorHttpHandlerService.handleHttpError(error.status);
                }
            });
    }

    onFormSubmit(form: NgForm) {
        console.log("form: ", this.paramX, this.paramY, this.paramR);

        this.attemptService.addPoint({
            paramX: this.paramX,
            paramY: this.paramY,
            paramR: this.paramR
        })
            .subscribe({
                next: (data: any) => {
                    console.log("new attempt data: ", data);
                    if (data === null) {
                        return;
                    }
                    let attempt: Attempt = {x: data["x"], y: data["y"], r: data["r"], hit: data["hit"]};
                    this.attempts.push(attempt);
                    this.drawService.drawPointForNg(attempt.x, attempt.y, attempt.hit ? "white" : "red");
                },
                error: error => {
                    console.log("error: ", error);
                    this.errorHttpHandlerService.handleHttpError(error.status);
                }
            });
    }

    onClearButtonClick() {
        this.attemptService.clearAttempts()
            .subscribe({
                next: (data: any) => {
                    console.log("clear attempts ");
                    this.drawService.updateCanvas();
                    this.attempts = [];
                },
                error: error => {
                    console.log("error: ", error);
                    this.errorHttpHandlerService.handleHttpError(error.status);
                }
            });
    }

    radiusChanged() {
        this.drawService.updateCanvas();
        this.drawAllAttempts();
    }
}
