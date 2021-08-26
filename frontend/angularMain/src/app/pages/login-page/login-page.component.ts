import { Observable } from 'rxjs';
import { User } from './../../models/user';
import { AccountService } from './../../services/account.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  loginForm: FormGroup;
  loginLoading = false;
  loginSubmitted = false;
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
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.required]
    });
  }

  get loginF() {return this.loginForm.controls;}

  onLoginSubmit() {
    this.loginSubmitted = true;

    if(this.loginForm.invalid) {
      return;
    }

    this.loginLoading = true;
    this.accountService.login(this.loginF.email.value, this.loginF.password.value)
      .pipe(first())
      .subscribe({
        next: () => {
        },
        error: error => {
          this.loginLoading = false;
        },
        complete: () => {
          this.loginLoading = false
        }
      })
  }



}
