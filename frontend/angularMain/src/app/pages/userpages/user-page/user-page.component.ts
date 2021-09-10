import { AccountService } from '../../../services/account.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { User } from '../../../models/user';
import { Observable } from 'rxjs';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {
  passwordForm: FormGroup;
  loading = false;
  submitted = false;
  matchingPass = true;

  user$ :Observable<User | null>;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService
  ) {
    this.user$ = accountService.user;
  }

  ngOnInit(): void {
    this.passwordForm = this.formBuilder.group({
      password: ['', Validators.required],
      newPass: ['newPass', Validators.required],
      confirmPass: ['confirmPass', Validators.required]
    });
  }

  get f() {return this.passwordForm.controls;}

  onSubmit() {
    this.submitted = true;

    if(this.passwordForm.invalid) {
      return;
    }
    if(this.passwordForm.get("newPass")?.value == null || this.passwordForm.get("newPass")?.value != this.passwordForm.get("confirmPass")?.value){
      this.matchingPass = false;
      return;
    }

    this.loading = true;
    if(!this.accountService.userValue){
      return
    }
    var user : User = this.accountService.userValue;
    user.oldPassword = this.f.password.value;
    user.password = this.f.newPass.value;
    this.accountService.changePass(user.id, user).subscribe(() =>
    () => {},
    () => {this.loading = false;},
    () => {this.loading = false;}
    );
  }

  logout() {
    this.accountService.logout();
    this.router.navigate(['', { outlets: { userSidebar: [ 'Login'] }}]);
  }

}
