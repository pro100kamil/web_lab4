import {User} from "../models/interfaces";
import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {MyConfiguration} from "../configuration/my-configuration";

@Injectable()
export class RegisterService {
    // url: string = "http://localhost:8080/lab4/api/users/new?login=logT&password=pasT";
    // url: string = "http://localhost:8080/lab4/api/users/new";
    url: string = MyConfiguration.serverApiUrl + "users/new";

    constructor(private http: HttpClient) {

    }

    register(user: User) {
        const body = {login: user.login, password: user.password};
        // const myHeaders =  {'Access-Control-Allow-Origin': 'http://localhost:4200'};
        // const myBody = new Http

        // const myHeaders = new HttpHeaders().set("Content-Type", "application/json").set('Access-Control-Allow-Origin', "*").set('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE').set('Access-Control-Allow-Credentials', 'true').set('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
        return this.http.post(this.url, body);
    }

}
