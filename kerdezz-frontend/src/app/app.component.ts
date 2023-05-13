import {Component} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'kerdezz';
  isMenuCollapsed = true;

  constructor(private router: Router) {
  }

  logout() {
    window.location.href = '/kerdezz/logout';
  }

  toAccount() {
    window.location.href = '/kerdezz/account';
  }
}
