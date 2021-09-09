import { User } from '../../../models/user';
import { AccountService } from '../../../services/account.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  constructor(private accountService : AccountService) { }

  ngOnInit(): void {
  }

  checkIfAdmin(): boolean {
    if(!this.accountService.userValue || !this.accountService.userValue.scopes){
      return false
    } else if(this.accountService.userValue.scopes.some(scope => scope == "admin")) {
      return true;
    } else {
      return false;
    }
  }

}
