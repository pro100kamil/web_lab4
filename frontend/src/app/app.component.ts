import { Component} from "@angular/core";
import { FormsModule } from "@angular/forms";
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {IndexComponent} from "./components/index/index.component";
import {MainComponent} from "./components/main/main.component";

@Component({
  selector: "my-app",
  standalone: true,
    imports: [FormsModule, IndexComponent, MainComponent, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: "./app.component.html",
  styleUrl: "./app.component.css"
})
export class AppComponent {

}
