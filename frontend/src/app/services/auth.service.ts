import {User} from "../models/interfaces";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MyConfiguration} from "../configuration/my-configuration";

@Injectable()
export class AuthService {
    url: string = MyConfiguration.serverApiUrl + "users/check";

    constructor(private http: HttpClient) {

    }

    auth(user: User) {
        const body = {login: user.login, password: user.password};
        return this.http.post(this.url, body);
    }
}
