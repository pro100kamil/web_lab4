import {Routes} from '@angular/router';

// export const routes: Routes = [];

// компоненты, которые сопоставляются с маршрутами
import {IndexComponent} from "./components/index/index.component";
import {MainComponent} from "./components/main/main.component";
import {mainGuard} from "./components/main/main.guard";
import {NotFoundComponent} from "./components/not-found/not-found.component";

// определение маршрутов
export const routes: Routes = [
    {path: "", component: IndexComponent},
    {path: "index", component: IndexComponent},
    {path: "main", component: MainComponent, canActivate: [mainGuard]},
    {path: "**", component: NotFoundComponent}    // для оставшихся url'ов обработка 404
];
