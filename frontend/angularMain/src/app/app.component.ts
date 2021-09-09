import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from './models/user';
import { AccountService } from './services/account.service';
import { Component, NgModule } from '@angular/core';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'angularMain';
  sidenavOpen : boolean;
  user$ : Observable<User | null>;

  constructor(private accountService : AccountService, private router : Router) {
      this.sidenavOpen = true;
      this.user$ = accountService.user;
  }
}
