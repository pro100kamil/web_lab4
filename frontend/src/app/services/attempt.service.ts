import {Point} from "../models/interfaces";
import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CurrentService} from "./current.service";
import {MyConfiguration} from "../configuration/my-configuration";

@Injectable()
export class AttemptService {
    add_url: string = MyConfiguration.serverApiUrl + "attempts/new";
    all_url: string = MyConfiguration.serverApiUrl + "attempts/all";
    clear_url: string = MyConfiguration.serverApiUrl + "attempts/clear";

    constructor(private http: HttpClient, private currentService: CurrentService) {
    }

    // getHeaders(): { password: string; login: string } {
    //     let user: User | null = this.currentService.getUser();
    //
    //     return {
    //         login: user?.login as string,
    //         password: user?.password as string
    //     };
    // }

    getHeaders(): HttpHeaders {
        let user: string | null = this.currentService.getUser();

        return new HttpHeaders().set('Authorization',  `Basic ${user}`)
    }

    addPoint(point: Point) {
        const body = {strX: point.paramX, strY: point.paramY, strR: point.paramR};


        // const myHeaders = new HttpHeaders({
        //     'Content-Type': 'application/json',
        //     'Authorization': '',
        //     'login': user?.login as string
        // });

        // const myHeaders =  {'Access-Control-Allow-Origin': 'http://localhost:4200'};
        // const myBody = new Http

        // const myHeaders = new HttpHeaders().set("Content-Type", "application/json").set('Access-Control-Allow-Origin', "*").set('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE').set('Access-Control-Allow-Credentials', 'true').set('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

        return this.http.post(this.add_url,
            body,
            {headers: this.getHeaders()}
        );
    }

    clearAttempts() {
        return this.http.post(this.clear_url,
            {},
            {headers: this.getHeaders()}
        );
    }

    allAttempts() {
        return this.http.get(this.all_url,
            {headers: this.getHeaders()}
        );
    }
}
