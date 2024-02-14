import {Injectable} from "@angular/core";

let centerX: number = 225;  //в этом месте x=0 с точки зрения математических координат
let centerY: number = 225;  //в этом месте y=0 с точки зрения математических координат
let R: number = 200;
let DEFAULT_R_: number = 1;     // значения по умолчанию берутся в случае некорректности этих данных в форме
let canvas: any = null;
let context: any = null;


@Injectable({
    providedIn: "root",       // глобальный сервис
})
export class DrawService {
    canvas: any = null;
    centerX: number = centerX;
    centerY: number = centerY;
    R: number = R;

    start() {
        canvas = document.getElementById('canvas') as
            HTMLCanvasElement;
        this.canvas = canvas;
        context = canvas.getContext("2d");
        context.font = "12px Verdana";
    }

    drawPoint(x: number, y: number, delta: number = 2): void {
        context.rect(x - delta / 2, y - delta / 2, delta, delta);
    }


    get_r_(): number {
        let input_r: HTMLInputElement = document.getElementById("input_r") as HTMLInputElement;

        if (isNaN(+input_r.value) || +input_r.value < 1 || +input_r.value > 5) {
            return DEFAULT_R_;
        }
        return +input_r.value;
    }


    drawPointForNg(mathX: number, mathY: number, color: string = "red", delta: number = 4): void {
        console.log("drawPointForNg: ", mathX, mathY);
        let r_: number = this.get_r_();

        let x: number = mathX * R / r_ + centerX;
        let y: number = centerY - mathY * R / r_;

        context.beginPath();
        this.drawPoint(x, y, delta);
        context.strokeStyle = color;
        context.fillStyle = color;
        context.fill();
        context.stroke();
    }


    drawTextWithDeltaX(text: string, x: number, y: number, delta: number = 4): void {
        //смещение по оси х для надписей на оси y
        context.fillText(text, x + delta, y);
        this.drawPoint(x, y);
    }


    drawTextWithDeltaY(text: string, x: number, y: number, delta: number = 4): void {
        //смещение по оси y для надписей на оси x
        context.fillText(text, x, y - delta);
        this.drawPoint(x, y);
    }


    drawArrow(x: number, y: number, arrowDelta: number, direction: string): void {
        context.moveTo(x, y);
        if (direction === "right")
            context.lineTo(x - arrowDelta, y - arrowDelta);
        else
            context.lineTo(x - arrowDelta, y + arrowDelta);

        context.moveTo(x, y);
        if (direction === "right")
            context.lineTo(x - arrowDelta, y + arrowDelta);
        else
            context.lineTo(x + arrowDelta, y + arrowDelta);
    }


    drawAxes(radius: number, delta: number): void {
        let arrowDelta: number = 4;
        context.beginPath();

        this.drawPoint(centerX, centerY, 4);

        context.moveTo(centerX - radius - delta, centerY);
        context.lineTo(centerX + radius + delta, centerY); //OX

        this.drawArrow(centerX + radius + delta, centerY, arrowDelta, "right");
        context.fillText("X", centerX + radius + delta, centerY);

        context.moveTo(centerX, centerY + radius + delta);
        context.lineTo(centerX, centerY - radius - delta); //OY
        this.drawArrow(centerX, centerY - radius - delta, arrowDelta, "up");
        context.fillText("Y", centerX, centerY - radius - delta);

        //OX
        this.drawTextWithDeltaY("-R", centerX - radius, centerY);
        this.drawTextWithDeltaY("-R/2", centerX - radius / 2, centerY);
        this.drawTextWithDeltaY("R/2", centerX + radius / 2, centerY);
        this.drawTextWithDeltaY("R", centerX + radius, centerY);

        //OY
        this.drawTextWithDeltaX("-R", centerX, centerY + radius);
        this.drawTextWithDeltaX("-R/2", centerX, centerY + radius / 2);
        this.drawTextWithDeltaX("R/2", centerX, centerY - radius / 2);
        this.drawTextWithDeltaX("R", centerX, centerY - radius);

        context.stroke();
    }


    drawSecondQuarter(radius: number): void {
        //четверть окружности по радиусу
        context.moveTo(centerX, centerY);  //(0;0)

        context.arc(centerX, centerY, radius,
            Math.PI, Math.PI * 3 / 2,
            false);
        context.fill();
    }


    drawThirdQuarter(height: number, width: number): void {
        //прямоугольный треугольник по катетам
        //катеты могут быть отрицательными
        context.moveTo(centerX, centerY);  //(0;0)

        context.lineTo(centerX + width, centerY); //(width;0)
        context.lineTo(centerX, centerY - height);  //(0;height)
        context.lineTo(centerX, centerY);  //(0;0)
    }

    drawFourthQuarter(height: number, width: number): void {
        //прямоугольник по сторонам
        //сторона отрицательная => направление влево или вниз по математическим осям
        //сторона положительная => направление вправо или вверх по математическим осям
        context.moveTo(centerX, centerY);  //(0;0)

        context.lineTo(centerX + width, centerY); //(width;0)
        context.lineTo(centerX + width, centerY - height); //(width;height)
        context.lineTo(centerX, centerY - height); //(0;height)
        context.lineTo(centerX, centerY); //(0;0)
    }

    drawPlot(): void {
        context.beginPath();

        this.drawSecondQuarter(R);
        this.drawThirdQuarter(-R, -R);
        this.drawFourthQuarter(-R / 2, R);

        context.closePath();
        context.strokeStyle = "blue";
        context.fillStyle = "blue";
        context.fill();
        context.stroke();
        context.strokeStyle = "black";
        context.fillStyle = "black";
        this.drawAxes(R, 14);
    }

    updateCanvas(): void {
        context.clearRect(0, 0, canvas.width, canvas.height);  //очистка канваса
        this.drawPlot();
    }
}
