import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import {CurrentService} from "../../services/current.service";
import {inject} from "@angular/core";

export const mainGuard = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    const currentService = inject(CurrentService);    // получаем сервис

    return currentService.isAuthenticatedUser();
};
