import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class ErrorHttpHandlerService {

    constructor() {
    }


    handleHttpError(httpStatus: number) {
        console.log("http status: ", httpStatus);
        if (httpStatus == 403) {
            alert("недостаточная роль для выполнения действия");
        }
    }
}
